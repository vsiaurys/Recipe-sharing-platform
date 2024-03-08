package lt.techin.recipesharingplatform.controllers;

import jakarta.validation.Valid;
import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        Map<String, String> errors = new HashMap<>();

        boolean existsByDisplayName = userService.existsUserByDisplayName(user.getDisplayName());
        boolean existsByEmail = userService.existsUserByEmail(user.getEmail());

        if (existsByDisplayName) {
            errors.put("displayName", "User with display name " + user.getDisplayName() + " already exists");
        }
        if (existsByEmail) {
            errors.put("email", "User with email " + user.getEmail() + " already exists");
        }
        if (existsByDisplayName || existsByEmail) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
}