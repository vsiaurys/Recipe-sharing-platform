package lt.techin.recipesharingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    // @Password ////////////////////////
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    //   Must include at least 1 uppercase, at least 1 number, at least 1 special symbol. Not
    // least 6 characters, not
    // more than 20. Cannot have empty spaces.
    //

    @Column(unique = true)
    @NotEmpty(message = "Display name cannot be empty")
    @Size(min = 3, max = 15, message = "Display name must be in range between 3 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z]{3,15}$", message = "Display name may contain only lowercase or uppercase letters")
    private String displayName;

    //    //
    //    //                                        Cannot have offensive words.

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    //                                           Cannot have symbols, spaces. First letter must be uppercase. At least 2
    // characters. Same symbol cannot repeat
    // more than 5 times.
    //

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    //                                                    Cannot have symbols, spaces. First letter must be uppercase.
    // At
    // least 2 characters. Same symbol cannot repeat
    // more than 5 times.
    //
    @NotEmpty(message = "Gender cannot be empty")
    private String gender;
    //                                              Must be words Male, Female, or Other
    //
    @NotEmpty(message = "Role cannot be empty")
    @Pattern(
            regexp = "^ROLE_[A-Z]{1,25}$",
            message = "Role must start with ROLE_ and contain only uppercase characters (maximum 30)")
    private String role;

    public User(
            String email,
            String password,
            String displayName,
            String firstName,
            String lastName,
            String gender,
            String role) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.role = role;
    }

    public User() {}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }
}
