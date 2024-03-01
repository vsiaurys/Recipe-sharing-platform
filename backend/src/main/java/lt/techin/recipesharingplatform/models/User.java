package lt.techin.recipesharingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
public class User implements UserDetails {
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

    private static final String NAME_REGEX = "^(?!\\w*(\\w)\\1{5})"
            + // Negative lookahead to ensure no character is repeated more than 5 times consecutively
            "(?=\\p{Lu}\\p{L}+$)"
            + // Positive lookahead to ensure the string starts with an uppercase letter followed by one or more
            // letters
            "[A-Za-z]+$"; // Character class to match any uppercase or lowercase letter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Pattern(
            regexp = PASSWORD_REGEX,
            message =
                    "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace.")
    @Size(min = 6, max = 20, message = "Password must be in range between 6 and 20 characters")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @Column(unique = true)
    @NotEmpty(message = "Display name cannot be empty")
    @Size(min = 3, max = 15, message = "Display name must be in range between 3 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z]{3,15}$", message = "Display name may contain only lowercase or uppercase letters")
    private String displayName;
    // Cannot have offensive words.

    @NotEmpty(message = "First name cannot be empty")
    @Pattern(
            regexp = NAME_REGEX,
            message =
                    "First name must start out of a letter in uppercase and may contain only lowercase or uppercase letters (minimum 2 characters)")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Pattern(
            regexp = NAME_REGEX,
            message =
                    "Last name must start out of a letter in uppercase and may contain only lowercase or uppercase letters (minimum 2 characters)")
    private String lastName;

    @NotEmpty(message = "Gender cannot be empty")
    private String gender;
    // Must be words Male, Female, or Other

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

    @Override
    public String getUsername() {
        return null;
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.getRole());
    }
}
