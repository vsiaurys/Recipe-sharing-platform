package lt.techin.recipesharingplatform.controllers;

import jakarta.validation.Valid;
import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.services.UserService;
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
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

        if (userService.existsUserByDisplayName(user) || userService.existsUserByEmail(user)) {
            // MultiValueMap<String, String> json = new LinkedMultiValueMap<>();
            String json = "{\n";

            if (userService.existsUserByDisplayName(user)) {
                json = json + "\"displayName\": " + "\"User with display name " + user.getDisplayName()
                        + " already exists\",\n";
            }
            if (userService.existsUserByEmail(user)) {
                json = json + "\"email\": " + "\"User with email " + user.getEmail() + " already exists\"\n";
            }
            json = json + "}";
            return ResponseEntity.badRequest().body(json);
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
