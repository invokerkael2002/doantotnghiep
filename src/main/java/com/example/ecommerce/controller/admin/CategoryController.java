package com.example.ecommerce.controller.admin;

import com.example.ecommerce.dto.CategoryInfo;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.request.CategoryRequest;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/categories")
    public String getCategoryManagePage(Model model) {
        List<CategoryInfo> categories = categoryService.getListCategoryAndProductCount();
        model.addAttribute("categories", categories);

        return "admin/category/list";
    }

    @PostMapping("/api/admin/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryService.createCategory(request);

        return ResponseEntity.ok(category);
    }

    @PutMapping("/api/admin/categories/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryRequest request, @PathVariable int id) {
        categoryService.updateCategory(id, request);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/categories/{id}")
    public ResponseEntity<?> deleteCategory( @PathVariable int id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
