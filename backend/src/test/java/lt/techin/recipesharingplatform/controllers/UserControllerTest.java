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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
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
    @WithMockUser(roles = {"USER"})
    void createUser_whenCreateUser_thenReturnIt() throws Exception {
        //  given
        User user = new User(
                "abcdefghi.klmno49@efghijk.com",
                "Password123&=",
                "Smauglys87",
                "Vardas",
                "Pavarde",
                "Male",
                "ROLE_USER");
        given(this.userService.saveUser(any(User.class))).willReturn(user);

        // ObjectMapper om = new ObjectMapper();
        // om.findAndRegisterModules();

        //  when
        mockMvc.perform(
                        post("/register")
                        //       .contentType(MediaType.APPLICATION_JSON)
                        //       .accept(MediaType.APPLICATION_JSON)
                        // .content(om.writeValueAsString(user)))
                        )
                //  then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("abcdefghi.klmno49@efghijk.com"))
                // .andExpect(jsonPath("$.password").value("Password123&="))
                .andExpect(jsonPath("$.displayName").value("Smauglys87"))
                .andExpect(jsonPath("$.firstName").value("Vardas"))
                .andExpect(jsonPath("$.lastName").value("Pavarde"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));

        verify(this.userService).saveUser(any(User.class));
    }

    // public PasswordEncoder passwordEncoder() {
    //        return new BCryptPasswordEncoder();

    @Test
    @WithMockUser
    void createUser_whenNotAllowed_returnBadRequest() throws Exception {
        //  given
        User user = new User(
                "abcdefghi.klmno49@efghijk.com",
                "Password123&=",
                "Smauglys87",
                "Vardas",
                "Pavarde",
                "Male",
                "ROLE_USER");
        given(this.userService.saveUser(any(User.class))).willReturn(user);

        //        ObjectMapper om = new ObjectMapper();
        //        om.findAndRegisterModules();

        //  when
        mockMvc.perform(
                        post("/register")
                        //                        .contentType(MediaType.APPLICATION_JSON)
                        //                        .accept(MediaType.APPLICATION_JSON)
                        //                        .content(om.writeValueAsString(user)))
                        )
                //  then
                .andExpect(status().isBadRequest());

        verify(this.userService, times(0)).saveUser(any(User.class));
    }

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
                .andExpect(status().isOk());
    }
}
