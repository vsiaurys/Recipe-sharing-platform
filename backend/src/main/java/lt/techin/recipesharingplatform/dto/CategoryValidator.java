package lt.techin.recipesharingplatform.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lt.techin.recipesharingplatform.models.Category;

public class CategoryValidator {
    private final Validator validator;

    public CategoryValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void validateCategory(Category category) {
        validator.validate(category, CategoryValidationSequence.class);
    }
}