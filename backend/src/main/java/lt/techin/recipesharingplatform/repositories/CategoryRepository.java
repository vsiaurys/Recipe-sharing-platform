package lt.techin.recipesharingplatform.repositories;

import lt.techin.recipesharingplatform.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    boolean existsById(long id);

    Category findCategoryById(long id);
}
