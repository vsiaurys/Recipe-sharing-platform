package lt.techin.recipesharingplatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 4, max = 20, message = "Name must be from 4 to 20 characters")
    //    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "Name must contain only letters and spaces")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]*( [a-zA-Z]+)*$",
            message = "Name must start with an uppercase letter and contain only letters and spaces")
    @Column(unique = true)
    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
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
