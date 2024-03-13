package lt.techin.recipesharingplatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.recipesharingplatform.validation.OffensiveWords;

public class UserDto {
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern.List({
        @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit"),
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter"),
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter"),
        @Pattern(
                regexp = ".*[@#$%^&+=~`!*()\\-_{}\\[\\]|:;\"'<,>.?/].*",
                message = "Password must contain at least one special character"),
        @Pattern(regexp = "[^\\s]+", message = "No whitespace allowed")
    })
    private String password;

    @NotEmpty(message = "Display name cannot be empty")
    @Size(min = 3, max = 15, message = "Display name must be in range between 3 and 15 characters")
    @Pattern.List({
        @Pattern(regexp = "[A-Za-z].*", message = "Display name must start out of a letter"),
        @Pattern(regexp = "[a-zA-Z0-9]+", message = "Display name may contain only letters and digits"),
        @Pattern(
                regexp = OffensiveWords.OFFENSIVE_WORDS_PATTERN,
                message = "Display name cannot contain offensive words")
    })
    private String displayName;

    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 2, message = "First name must be at least 2 characters")
    @Pattern.List({
        @Pattern(regexp = "[A-Z].*", message = "First name must start out of a letter in uppercase"),
        @Pattern(regexp = "[A-Za-z]+", message = "First name may contain only letters"),
        @Pattern(regexp = "^(?!.*(.)\\1{4}).*$", message = "Same character cannot repeat 5 times or more")
    })
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 2, message = "Last name must be at least 2 characters")
    @Pattern.List({
        @Pattern(regexp = "[A-Z].*", message = "Last name must start out of a letter in uppercase"),
        @Pattern(regexp = "[A-Za-z]+", message = "Last name may contain only letters")
    })
    private String lastName;

    @NotEmpty(message = "Gender cannot be empty")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    public UserDto(
            String email, String password, String displayName, String firstName, String lastName, String gender) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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
}
