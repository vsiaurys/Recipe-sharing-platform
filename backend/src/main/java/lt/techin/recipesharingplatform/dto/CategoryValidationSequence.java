package lt.techin.recipesharingplatform.dto;

import jakarta.validation.GroupSequence;

@GroupSequence({FirstOrder.class, SecondOrder.class, ThirdOrder.class})
public interface CategoryValidationSequence {}
