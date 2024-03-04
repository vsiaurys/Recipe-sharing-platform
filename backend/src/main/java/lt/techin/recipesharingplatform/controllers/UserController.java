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
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])" + // At least one digit
            "(?=.*[a-z])"
            + // At least one lowercase letter
            "(?=.*[A-Z])"
            + // At least one uppercase letter
            "(?=.*[@#$%^&+=])"
            + // At least one special character
            "(?=\\S+$)"
            + // No whitespace allowed
            ".{6,20}$"; // From 6 to 20 characters long

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if (user.getPassword().matches(PASSWORD_REGEX)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            User savedUser = this.userService.saveUser(user);

            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
                    .body(savedUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(
                        "Password must contain at least one digit, one lowercase letter, one uppercase letter,"
                                + " one special character, and no whitespace. Password must be in range between 6 and 20 characters");
    }
}
