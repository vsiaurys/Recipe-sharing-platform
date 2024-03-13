package lt.techin.recipesharingplatform.controllers;

import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.security.SecurityConfig;
import lt.techin.recipesharingplatform.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    //    @MockBean
    //    private UserValidator userValidator;

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
                                                         }
                                                         """))

                //  then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.displayName").value("Smauglys87"))
                .andExpect(jsonPath("$.firstName").value("Vardas"))
                .andExpect(jsonPath("$.lastName").value("Pavarde"))
                .andExpect(jsonPath("$.gender").value("Male"));

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
                                                                             "gender": "Female"
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
                                                                             "gender": "Female"
                                                                         }
                                                                         """))

                //  then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.displayName").value("Display1"))
                .andExpect(jsonPath("$.firstName").value("Vardas"))
                .andExpect(jsonPath("$.lastName").value("Pavarde"))
                .andExpect(jsonPath("$.gender").value("Female"));

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
                                                                             "gender": "Female"
                                                                         }
                                                                         """))

                //  then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.displayName").value("Display1"))
                .andExpect(jsonPath("$.firstName").value("Vardas"))
                .andExpect(jsonPath("$.lastName").value("Pavarde"))
                .andExpect(jsonPath("$.gender").value("Female"));

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
                                                                             "gender": "Female"
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
                                                                             "gender": "Female"
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
                                                                             "gender": "Female"
                                                                         }
                                                                         """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password cannot be empty"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }
    //////////////
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
                                                                         "gender": "Female"
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
                                                                             "gender": "Female"
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
                                                                             "gender": "Female"
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
                                                                             "gender": "Female"
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
                                                                             "gender": "Female"
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
                                                                             "gender": "Female"
                                                                         }
                                                                         """))

                //  then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("No whitespace allowed"));

        verify(this.userService, times(0)).saveUser(any(User.class));
        verify(this.userService, times(0)).existsUserByEmail("email@email.com");
        verify(this.userService, times(0)).existsUserByDisplayName("Display1");
    }
}
