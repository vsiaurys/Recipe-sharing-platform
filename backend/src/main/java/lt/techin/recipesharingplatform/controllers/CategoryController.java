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

    //    @PutMapping("/movies/{id}")
    //    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie, @PathVariable long id) {
    //        if (this.movieService.existsMovieById(id)) {
    //            Movie movieFromDb = this.movieService.findMovieById(id);
    //
    //            movieFromDb.setTitle(movie.getTitle());
    //            movieFromDb.setDateReleased(movie.getDateReleased());
    //            movieFromDb.setLengthMinutes(movie.getLengthMinutes());
    //
    //            return ResponseEntity.ok(this.movieService.saveMovie(movieFromDb));
    //        }
    //        Movie savedMovie = this.movieService.saveMovie(movie);
    //
    //        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
    //                .path("/{id}").buildAndExpand(savedMovie.getId()).toUri()).body(savedMovie);
    //    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {

        if (this.categoryService.existsCategoryById(id)) {
            this.categoryService.deleteCategoryById(id);

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}