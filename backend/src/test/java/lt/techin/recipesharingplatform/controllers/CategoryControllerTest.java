package lt.techin.recipesharingplatform.controllers;

import lt.techin.recipesharingplatform.models.Category;
import lt.techin.recipesharingplatform.security.SecurityConfig;
import lt.techin.recipesharingplatform.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSaveCategoryAsAdmin() throws Exception {
        Category category = new Category("Test Category");
        given(categoryService.saveCategory(any(Category.class))).willReturn(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TestCategory\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Category"))
                .andExpect(jsonPath("$.id").exists());

        verify(this.categoryService, times(1)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testSaveCategoryAsUser() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TestCategory\"}"))
                .andExpect(status().isForbidden());

        verify(this.categoryService, times(0)).saveCategory(any(Category.class));
    }

    @Test
    public void testSaveCategoryAsUnauthenticatedUser() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TestCategory\"}"))
                .andExpect(status().isUnauthorized());

        verify(this.categoryService, times(0)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSaveCategoryWithTooShortName() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Tes\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", equalTo("Name must be from 4 to 20 characters")));

        verify(this.categoryService, times(0)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSaveCategoryWithTooLongName() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Testttttttttttttttttt\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", equalTo("Name must be from 4 to 20 characters")));

        verify(this.categoryService, times(0)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSaveCategoryWithNumberInName() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                        "$.name",
                        equalTo("Name must start with an uppercase letter and contain only letters and spaces")));

        verify(this.categoryService, times(0)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSaveCategoryWithSymbolInName() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test~\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(
                        "$.name",
                        equalTo("Name must start with an uppercase letter and contain only letters and spaces")));

        verify(this.categoryService, times(0)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSaveCategoryWithEmptyName() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", equalTo("Name field cannot be empty.")));

        verify(this.categoryService, times(0)).saveCategory(any(Category.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testSaveCategoryWithExistingName() throws Exception {
        String categoryName = "TestCategory";

        given(categoryService.existsByName(categoryName)).willReturn(true);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"" + categoryName + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", equalTo("Category with this name already exists")));
    }
}
