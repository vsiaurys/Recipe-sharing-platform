package lt.techin.recipesharingplatform.services;

import lt.techin.recipesharingplatform.models.Category;
import lt.techin.recipesharingplatform.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public boolean existsCategoryById(long id) {
        return categoryRepository.existsById(id);
    }

    public void deleteCategoryById(long id) {
        categoryRepository.deleteById(id);
    }

    public Category findCategoryById(long id) {
        return categoryRepository.findCategoryById(id);
    }

    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }
}
