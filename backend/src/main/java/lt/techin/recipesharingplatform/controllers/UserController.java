package lt.techin.recipesharingplatform.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lt.techin.recipesharingplatform.dto.UserDto;
import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto) {
        Map<String, String> errors = new HashMap<>();

        boolean existsByDisplayName = userService.existsUserByDisplayName(userDto.getDisplayName());
        boolean existsByEmail = userService.existsUserByEmail(userDto.getEmail());

        if (existsByDisplayName) {
            errors.put("displayName", "User with display name " + userDto.getDisplayName() + " already exists");
        }
        if (existsByEmail) {
            errors.put("email", "User with email " + userDto.getEmail() + " already exists");
        }
        if (existsByDisplayName || existsByEmail) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setDisplayName(userDto.getDisplayName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setRole("ROLE_USER");
        user.setProfileImage(userDto.getProfileImage());

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(user.getId())
                        .toUri())
                .body(this.userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws JsonProcessingException {
        Optional<User> userOptional = userService.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            User userDb = userOptional.get();

            if (passwordEncoder.matches(user.getPassword(), userDb.getPassword())) {
                // Authentication successful
                return ResponseEntity.ok().body(userDb);
            }
        }

        // Authentication failed
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "The email or password provided is incorrect.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUserWithFile(
            @PathVariable("id") Long id,
            @RequestPart(required = false) MultipartFile file,
            @Valid @RequestPart("userDto") UserDto userDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authenticatedEmail = authentication.getName();

        if (!this.userService.findUserById(id).isPresent()) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "User you are trying to update does not exist in database.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }

        if (!Objects.equals(
                authenticatedEmail, this.userService.findUserById(id).get().getEmail())) {

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "User is not authorized to update this user.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
        }

        Optional<User> userOptional = userService.findUserById(id);
        Optional<User> checkIfEmailInDatabase = userService.findUserByEmail(userDto.getEmail());
        boolean checkIfDisplayNameInDatabase = userService.existsUserByDisplayName(userDto.getDisplayName());

        if (checkIfDisplayNameInDatabase
                && !Objects.equals(
                        userDto.getDisplayName(),
                        userService.findUserById(id).get().getDisplayName())) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "User with this display name already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }

        if (checkIfEmailInDatabase.isPresent() && !Objects.equals(authenticatedEmail, userDto.getEmail())) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "User with this email already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();

            userToUpdate.setDisplayName(userDto.getDisplayName());
            userToUpdate.setFirstName(userDto.getFirstName());
            userToUpdate.setLastName(userDto.getLastName());
            userToUpdate.setGender(userDto.getGender());

            userToUpdate.setEmail(userDto.getEmail());

            userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));

            if (file != null && !file.isEmpty()) {

                String contentType = file.getContentType();
                if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("error", "Only JPEG and PNG file types are accepted");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
                }

                String fileName = file.getOriginalFilename();
                String currentDir = System.getProperty("user.dir");
                String uploadDir = currentDir + File.separator + "uploads";
                String filePath = uploadDir + File.separator + fileName;

                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File destFile = new File(filePath);
                try {
                    file.transferTo(destFile);
                    userToUpdate.setProfileImage("/uploads/" + fileName);
                } catch (IOException e) {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("error", "Failed to upload file: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(errorMap);
                }
            }

            User updatedUser = userService.saveUser(userToUpdate);
            return ResponseEntity.ok().body(updatedUser);
        } else {

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "User not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
        }
    }
}
