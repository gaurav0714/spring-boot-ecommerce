package in.nic.sb_ecom.service.impl;

import in.nic.sb_ecom.exceptions.ApiException;
import in.nic.sb_ecom.exceptions.ResourceNotFoundException;
import in.nic.sb_ecom.model.Category;
import in.nic.sb_ecom.payload.CategoryDTO;
import in.nic.sb_ecom.payload.CategoryResponse;
import in.nic.sb_ecom.repositories.CategoryRepository;
import in.nic.sb_ecom.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getCategories(Integer pageNumber, Integer pageSize) {
        Sort sortByAndOrder = Sort.by("categoryId").ascending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> page = categoryRepository.findAll(pageDetails);
        List<Category> categories = page.getContent();
        if(categories.isEmpty())
            throw new ApiException("No categories created till now.");
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(page.getNumber());
        categoryResponse.setPageSize(page.getSize());
        categoryResponse.setTotalPages(page.getTotalPages());
        categoryResponse.setTotalElements(page.getTotalElements());
        categoryResponse.setLastPage(page.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(savedCategory != null)
            throw new ApiException("Category with name "+categoryDTO.getCategoryName()+" already exist");
        Category resultCategory = categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
        return modelMapper.map(resultCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryDTO.setCategoryId(categoryId);
        Category category = categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
        return modelMapper.map(category, CategoryDTO.class);


    }
}
