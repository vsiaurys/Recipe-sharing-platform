package lt.techin.recipesharingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.recipesharingplatform.dto.FirstOrder;
import lt.techin.recipesharingplatform.dto.SecondOrder;
import lt.techin.recipesharingplatform.dto.ThirdOrder;
import lt.techin.recipesharingplatform.validation.OffensiveWords;

@Entity
@Table(name = "Categories")
@GroupSequence({Category.class, FirstOrder.class, SecondOrder.class, ThirdOrder.class})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = OffensiveWords.OFFENSIVE_WORDS_PATTERN, message = "Category name cannot contain offensive words")
    @NotEmpty(message = "Name field cannot be empty.", groups = FirstOrder.class)
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]*( [a-zA-Z]+)*$",
            message = "Name must start with an uppercase letter and contain only letters and spaces",
            groups = SecondOrder.class)
    @Size(min = 4, max = 20, message = "Name must be from 4 to 20 characters", groups = ThirdOrder.class)
    @Column(unique = true)
    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
