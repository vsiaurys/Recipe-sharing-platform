package lt.techin.recipesharingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.recipesharingplatform.validation.NoOffensiveWords;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    private String password;

    @Column(unique = true)
    @NotEmpty(message = "Display name cannot be empty")
    @Size(min = 3, max = 15, message = "Display name must be in range between 3 and 15 characters")
    @Pattern(regexp = "^(?:[A-Z]|[a-z]|[0-9]){3,15}$", message = "Display name may contain only letters and digits")
    @NoOffensiveWords(message = "Display name cannot contain offensive words")
    private String displayName;

    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 2, message = "First name must be at least 2 characters")
    @Pattern(regexp = "[A-Z].*", message = "First name must start out of a letter in uppercase")
    @Pattern(regexp = "[A-Za-z]+", message = "First name may contain only letters")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 2, message = "Last name must be at least 2 characters")
    @Pattern(regexp = "[A-Z].*", message = "Last name must start out of a letter in uppercase")
    @Pattern(regexp = "[A-Za-z]+", message = "Last name may contain only letters")
    private String lastName;

    @NotEmpty(message = "Gender cannot be empty")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

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
        return email;
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
