package com.example.ecommerce.service;

import com.example.ecommerce.dto.CategoryInfo;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.exception.InternalServerException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    };

    public List<CategoryInfo> getListCategoryAndProductCount() {
        return categoryRepository.getListCategoryAndProductCount();
    };

    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());

        categoryRepository.save(category);

        return category;
    };

    public void updateCategory(int id, CategoryRequest request) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category không tồn tại");
        }

        Category category = categoryOptional.get();
        category.setName(request.getName());

        try {
            categoryRepository.save(category);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi chỉnh sửa category");
        }
    }

    public void deleteCategory(int id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category không tồn tại");
        }

        Category category = categoryOptional.get();

        try {
            categoryRepository.delete(category);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa category");
        }
    }
}
