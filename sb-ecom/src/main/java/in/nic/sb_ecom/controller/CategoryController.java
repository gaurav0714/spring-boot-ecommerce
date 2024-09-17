package in.nic.sb_ecom.controller;

import in.nic.sb_ecom.model.Category;
import in.nic.sb_ecom.payload.CategoryDTO;
import in.nic.sb_ecom.payload.CategoryResponse;
import in.nic.sb_ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(@RequestParam(name = "pageNumber" ) Integer pageNumber,
                                                          @RequestParam(name = "pageSize") Integer pageSize) {
        CategoryResponse categoryResponse = categoryService.getCategories(pageNumber, pageSize);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategoryId(@PathVariable Long categoryId) {

            CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);

    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId) {

            CategoryDTO savedCategory = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(savedCategory,HttpStatus.OK);


    }
}
