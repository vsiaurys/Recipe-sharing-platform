package lt.techin.recipesharingplatform.validation;

import lt.techin.recipesharingplatform.models.User;

public class PasswordValidator {

    User user;

    public PasswordValidator(User user) {
        this.user = user;
    }

    public String validatePassword() {
        String password = user.getPassword();

        if (!password.matches(".*\\d.*")) {
            return "Password must contain at least one digit";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter";
        }
        if (!password.matches(".*[@#$%^&+=].*")) {
            return "Password must contain at least one special character";
        }
        if (!password.matches("[^\\s]+")) {
            return "No whitespace allowed in password";
        }
        if (!password.matches(".{6,20}")) {
            return "Password must be in range between 6 and 20 characters";
        }
        return "";
    }
}
