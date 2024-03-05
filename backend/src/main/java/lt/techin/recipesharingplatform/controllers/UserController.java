package lt.techin.recipesharingplatform.controllers;

import jakarta.validation.Valid;
import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
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
