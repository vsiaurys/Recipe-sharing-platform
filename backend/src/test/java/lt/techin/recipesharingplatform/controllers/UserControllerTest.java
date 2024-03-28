package lt.techin.recipesharingplatform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.recipesharingplatform.dto.UserDto;
import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.security.SecurityConfig;
import lt.techin.recipesharingplatform.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void login_withIncorrectEmail_returnUnauthorized() throws Exception {
        // Given
        User user = new User("test@example.com", "wrongpassword", "Smauglys87", "Vardas", "Pavarde", "Male");
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
                                                    "profileImage": "https://www.example.com"
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
        User user = new User("test@example.com", "correctpassword", "Smauglys87", "Vardas", "Pavarde", "Male");
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
        User user = new User("test@example.com", "correctpassword", "Smauglys87", "Vardas", "Pavarde", "Male");
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

    @Test
    void createUser_whenCreateUser_thenReturnIt() throws Exception {
        //  given
        User user = new User("email@email.com", "Password=123", "Smauglys87", "Vardas", "Pavarde", "Male");
        given(this.userService.saveUser(any(User.class))).willReturn(user);

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "email": "email@email.com",
                                                    "password": "Password=123",
                                                    "displayName": "Smauglys87",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Male"
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.displayName").value("Smauglys87"))
                .andExpect(jsonPath("$.firstName").value("Vardas"))
                .andExpect(jsonPath("$.lastName").value("Pavarde"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.profileImage").value("https://www.example.com"));

        verify(this.userService).saveUser(any(User.class));
    }

    @Test
    void createUser_whenDisplayNameExists_thenReturnBadRequest() throws Exception {
        //  given
        given(this.userService.existsUserByDisplayName("Display1")).willReturn(true);

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "abcdefghi.klmno49@efghijk.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.displayName").value("User with display name Display1 already exists"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenDisplayNameNotExists_thenReturnIsCreated() throws Exception {
        //  given
        given(this.userService.existsUserByDisplayName("Display1")).willReturn(false);
        User user = new User("email@email.com", "Password=1", "Display1", "Vardas", "Pavarde", "Female");
        given(this.userService.saveUser(any(User.class))).willReturn(user);

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"

                                                }
                                                """))

                //  then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.displayName").value("Display1"))
                .andExpect(jsonPath("$.firstName").value("Vardas"))
                .andExpect(jsonPath("$.lastName").value("Pavarde"))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.profileImage").value("https://www.example.com"));

        verify(this.userService).saveUser(any(User.class));
        verify(this.userService).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenEmailExists_thenReturnBadRequest() throws Exception {
        //  given
        given(this.userService.existsUserByEmail("email@email.com")).willReturn(true);

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female"
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("User with email email@email.com already exists"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService).existsUserByEmail("email@email.com");
    }

    @Test
    void createUser_whenEmailNotExists_thenReturnIsCreated() throws Exception {
        //  given
        given(this.userService.existsUserByEmail("email@email.com")).willReturn(false);
        User user = new User("email@email.com", "Password=1", "Display1", "Vardas", "Pavarde", "Female");
        given(this.userService.saveUser(any(User.class))).willReturn(user);

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.displayName").value("Display1"))
                .andExpect(jsonPath("$.firstName").value("Vardas"))
                .andExpect(jsonPath("$.lastName").value("Pavarde"))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.profileImage").value("https://www.example.com"));

        verify(this.userService).saveUser(any(User.class));
        verify(this.userService).existsUserByEmail("email@email.com");
    }

    @Test
    void createUser_whenEmptyEmail_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email cannot be empty"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenBadEmail_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "BadEmail",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Please provide a valid email address"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("BadEmail");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenEmptyPassword_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password cannot be empty"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenShortPassword_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Bad=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must be at least 6 characters long"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    ///////////
    @Test
    void createUser_whenPasswordHasNoDigit_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "BadPassword=",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must contain at least one digit"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenPasswordHasNoLowercaseLetter_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "BADPASSWORD=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"

                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must contain at least one lowercase letter"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenPasswordHasNoUppercaseLetter_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "badpassword=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must contain at least one uppercase letter"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenPasswordHasNoSpecialCharacter_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "BadPassword1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must contain at least one special character"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenPasswordHasAWhiteSpace_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Bad password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("No whitespace allowed"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenEmptyDisplayName_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.displayName").value("Display name cannot be empty"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("");
    }

    @Test
    void createUser_whenDisplayNameNotInRange_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "DN",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.displayName").value("Display name must be in range between 3 and 15 characters"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("DN");
    }

    @Test
    void createUser_whenDisplayNameNotStartsFromLetter_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "1DisplayName",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.displayName").value("Display name must start out of a letter"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("1DisplayName");
    }

    @Test
    void createUser_whenDisplayNameContainsBadCharacters_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1@",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.displayName").value("Display name may contain only letters and digits"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1@");
    }

    @Test
    void createUser_whenDisplayNameContainsOffensiveWord_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "bastard",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.displayName").value("Display name cannot contain offensive words"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("offensive1");
    }

    @Test
    void createUser_whenEmptyFirstName_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name cannot be empty"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenFirstNameTooShort_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "N",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name must be at least 2 characters"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenFirstNameNotStartsWithAnUppercaseLetter_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "name",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name must start out of a letter in uppercase"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenFirstNameContainsDigits_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Name123",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name may contain only letters"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenFirstNameHas5SameCharacters_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Nammmmme",
                                                    "lastName": "Pavarde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("Same character cannot repeat 5 times or more"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenEmptyLastName_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName").value("Last name cannot be empty"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenLastNameTooShort_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "L",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName").value("Last name must be at least 2 characters"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenLastNameNotStartsWithAnUppercaseLetter_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "lastname",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName").value("Last name must start out of a letter in uppercase"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenLastNameContainsDigits_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Lastname13",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName").value("Last name may contain only letters"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenLastNameHas5SameCharacters_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarrrrrrde",
                                                    "gender": "Female",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName").value("Same character cannot repeat 5 times or more"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenEmptyGender_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.gender").value("Gender cannot be empty"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    void createUser_whenWrongGender_thenReturnBadRequest() throws Exception {
        //  given

        //  when
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "displayName": "Display1",
                                                    "email": "email@email.com",
                                                    "password": "Password=1",
                                                    "firstName": "Vardas",
                                                    "lastName": "Pavarde",
                                                    "gender": "Fem",
                                                    "profileImage": "https://www.example.com"
                                                }
                                                """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.gender").value("Gender must be Male, Female, or Other"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }

    @Test
    @WithMockUser(roles = "USER", username = "test1@example.com")
    void updateUser_whenUpdateSuccessful_thenReturnOkAndBody() throws Exception {
        // given
        byte[] imageBytes = new byte[] {1, 2, 3, 4, 5};
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, imageBytes);

        ObjectMapper objectMapper = new ObjectMapper();
        UserDto data = new UserDto("test2@example.com", "123456aA!", "Galva", "Jebus", "Mangaramas", "Male");
        MockMultipartFile userDto = new MockMultipartFile(
                "userDto", "userDto", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(data));
        User oldUser = new User("test1@example.com", "123456aA!", "Smauglyss", "Tomasas", "Pavarde1", "Female");
        User updatedUser = new User("test2@example.com", "123456aA!", "Galva", "Jebus", "Mangaramas", "Male");

        given(userService.findUserById(1)).willReturn(Optional.of(oldUser));
        given(userService.saveUser(any(User.class))).willReturn(updatedUser);

        // when
        mockMvc.perform(multipart("/update-user/{id}", 1)
                        .file(file)
                        .file(userDto)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test2@example.com"))
                .andExpect(jsonPath("$.displayName").value("Galva"))
                .andExpect(jsonPath("$.firstName").value("Jebus"))
                .andExpect(jsonPath("$.lastName").value("Mangaramas"))
                .andExpect(jsonPath("$.gender").value("Male"));
    }
}
