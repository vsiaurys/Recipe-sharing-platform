package lt.techin.recipesharingplatform.controllers;

import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        String password = user.getPassword();

        if (!password.matches(".*\\d.*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must contain at least one digit");
        }
        if (!password.matches(".*[a-z].*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[A-Z].*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[@#$%^&+=].*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must contain at least one special character");
        }
        if (!password.matches("[^\\s]+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No whitespace allowed");
        }
        if (!password.matches(".{6,20}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must be in range between 6 and 20 characters");
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
