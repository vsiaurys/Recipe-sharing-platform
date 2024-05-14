package lt.techin.recipesharingplatform.controllers;

import jakarta.validation.Valid;
import lt.techin.recipesharingplatform.models.Category;
import lt.techin.recipesharingplatform.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody Category category) {
        if (categoryService.existsByName(category.getName())) {
            Map<String, Object> response = new HashMap<>();
            response.put("name", "Category with this name already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        category.setEnabled(true);
        Category savedCategory = categoryService.saveCategory(category);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedCategory.getId())
                        .toUri())
                .body(savedCategory);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category, @PathVariable long id) {
        if (this.categoryService.existsCategoryById(id)) {
            Category categoryFromDb = this.categoryService.findCategoryById(id);
            Optional<Category> checkIfCategoryInDatabase = categoryService.findCategoryByName(category.getName());

            if (checkIfCategoryInDatabase.isPresent()
                    && checkIfCategoryInDatabase.get().getId() != id) {
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("error", "This category already exists.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
            }

            categoryFromDb.setName(category.getName());
            categoryFromDb.setEnabled(category.getEnabled());

            return ResponseEntity.ok(this.categoryService.saveCategory(categoryFromDb));
        }

        Optional<Category> checkIfCategoryInDatabase = categoryService.findCategoryByName(category.getName());

        if (checkIfCategoryInDatabase.isPresent()) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "This category already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }

        Category savedCategory = this.categoryService.saveCategory(category);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedCategory.getId())
                        .toUri())
                .body(savedCategory);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {

        if (this.categoryService.existsCategoryById(id)) {
            this.categoryService.deleteCategoryById(id);

            return ResponseEntity.noContent().build();
        }

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Nothing to delete");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }
}