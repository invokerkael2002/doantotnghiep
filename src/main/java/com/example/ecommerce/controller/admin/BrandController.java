package com.example.ecommerce.controller.admin;

import com.example.ecommerce.entity.Brand;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.request.BrandRequest;
import com.example.ecommerce.security.CustomUserDetails;
import com.example.ecommerce.service.BrandService;
import com.example.ecommerce.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/admin/brands")
    public String getPostManagePage(Model model) {

        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        model.addAttribute("images", imageService.getListImageOfUser(user.getId()));

        model.addAttribute("brands", brandService.getListBrandAndProductCount());

        return "admin/brand/list";
    }

    @PostMapping("/api/admin/brands")
    public ResponseEntity<?> createCategory(@Valid @RequestBody BrandRequest request) {
        Brand brand = brandService.createBrand(request);

        return ResponseEntity.ok(brand);
    }

    @PutMapping("/api/admin/brands/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody BrandRequest request, @PathVariable int id) {
        brandService.updateBrand(id, request);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/brands/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        brandService.deleteBrand(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
