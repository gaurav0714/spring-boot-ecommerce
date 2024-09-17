package in.nic.sb_ecom.service;

import in.nic.sb_ecom.model.Category;
import in.nic.sb_ecom.payload.CategoryDTO;
import in.nic.sb_ecom.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getCategories(Integer pageNumber, Integer pageSize);
    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId);
}
