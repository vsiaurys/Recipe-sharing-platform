package lt.techin.recipesharingplatform.controllers;

import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.security.SecurityConfig;
import lt.techin.recipesharingplatform.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void login_withIncorrectEmail_returnUnauthorized() throws Exception {
        // Given
        User user =
                new User("test@example.com", "wrongpassword", "Smauglys87", "Vardas", "Pavarde", "Male", "ROLE_USER");
        given(userService.findUserByEmail(user.getEmail())).willReturn(Optional.empty());

        // When
        mockMvc.perform(
                        post("/login")
                                .contentType(APPLICATION_JSON)
                                .content(
                                        """
                        {
                            "email": "test@example.com",
                            "password": "wrongpassword",
                            "username": "Smauglys87",
                            "firstName": "Vardas",
                            "lastName": "Pavarde",
                            "gender": "Male",
                            "role": "ROLE_USER"
                        }
                        """))

                // Then
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("The email or password provided is incorrect."));

        // Verify
        verify(userService).findUserByEmail("test@example.com");
    }

    @Test
    public void login_withIncorrectPassword_returnUnauthorized() throws Exception {
        // Given
        User user =
                new User("test@example.com", "correctpassword", "Smauglys87", "Vardas", "Pavarde", "Male", "ROLE_USER");
        given(userService.findUserByEmail(user.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches("wrongpassword", user.getPassword())).willReturn(false);

        // When & Then
        mockMvc.perform(
                        post("/login")
                                .contentType(APPLICATION_JSON)
                                .content(
                                        """
                        {
                            "email": "test@example.com",
                            "password": "wrongpassword"
                        }
                        """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("The email or password provided is incorrect."));

        // Verify
        verify(userService).findUserByEmail("test@example.com");
    }

    @Test
    public void login_withCorrectCredentials_returnOk() throws Exception {
        // Given
        User user =
                new User("test@example.com", "correctpassword", "Smauglys87", "Vardas", "Pavarde", "Male", "ROLE_USER");
        given(userService.findUserByEmail(user.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches("correctpassword", user.getPassword())).willReturn(true);

        mockMvc.perform(
                        post("/login")
                                .contentType(APPLICATION_JSON)
                                .content(
                                        """
                                {
                                    "email": "test@example.com",
                                    "password": "correctpassword"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.role").value(user.getRole()));

        // Verify
        verify(userService).findUserByEmail("test@example.com");
    }
}
