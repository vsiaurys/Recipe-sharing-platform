package lt.techin.recipesharingplatform.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.services.UserService;
import lt.techin.recipesharingplatform.validation.PasswordValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

        PasswordValidator password = new PasswordValidator(user);
        String message = password.validatePassword();
        if (!message.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        User savedUser = this.userService.saveUser(user);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri())
                .body(savedUser);
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
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", 401);
        errorMap.put("message", "Unauthorized");
        errorMap.put("details", "The email or password provided is incorrect.");

        String messageJson = new ObjectMapper().writeValueAsString(errorMap);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageJson);
    }
}
