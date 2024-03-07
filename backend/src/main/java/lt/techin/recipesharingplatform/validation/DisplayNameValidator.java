package lt.techin.recipesharingplatform.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DisplayNameValidator implements ConstraintValidator<NoOffensiveWords, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Allow null or empty values
        }

        String[] offensiveWords = {"offensive1", "offensive2", "offensive3"};

        for (String word : offensiveWords) {
            if (value.toLowerCase().contains(word.toLowerCase())) {
                return false; // Contains offensive word
            }
        }
        return true; // No offensive words found
    }
}
