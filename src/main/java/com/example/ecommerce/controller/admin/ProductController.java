package com.example.ecommerce.controller.admin;


import com.example.ecommerce.entity.Brand;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.request.ProductRequest;
import com.example.ecommerce.security.CustomUserDetails;
import com.example.ecommerce.service.BrandService;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ImageService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/admin/products")
    public String getProductManagePage(Model model,
                                       @RequestParam(required = false, defaultValue = "1") int page,
                                       @RequestParam(required = false, defaultValue = "10") int size) {

        model.addAttribute("categories", categoryService.getListCategory());

        model.addAttribute("brands", brandService.getListBrand());

        model.addAttribute("data", productService.getAll(page, size));

        return "admin/product/list";
    }

    @GetMapping("/admin/search-result")
    public String searchProduct(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int size,
                                @RequestParam("keyword") String keyword,
                                Model model) {
        model.addAttribute("categories", categoryService.getListCategory());

        model.addAttribute("brands", brandService.getListBrand());

        model.addAttribute("data", productService.searchProducts(page, size, keyword));

        return "admin/product/search-result";
    }

    @GetMapping("/admin/danh-muc/{id}")
    public String getProductsByCategory(@PathVariable int id, Model model,
                                        @RequestParam(required = false, defaultValue = "1") int page,
                                        @RequestParam(required = false, defaultValue = "12") int size) {

        model.addAttribute("brands", brandService.getListBrand());

        model.addAttribute("categories", categoryService.getListCategory());

        model.addAttribute("data", productService.getAllByCategories_Id(id, page, size));
        return "admin/product/list";
    }

    @GetMapping("/admin/thuong-hieu/{id}")
    public String getProductsByBrand(@PathVariable int id, Model model,
                                     @RequestParam(required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, defaultValue = "12") int size) {

        model.addAttribute("brands", brandService.getListBrand());

        model.addAttribute("categories", categoryService.getListCategory());

        model.addAttribute("data", productService.getAllByBrand_Id(id, page, size));
        return "admin/product/list";
    }

    @GetMapping("/admin/products/create")
    public String getProductCreatePage(Model model) {
        // Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());

        model.addAttribute("images", images);

        // Get list category
        List<Category> categories = categoryService.getListCategory();
        model.addAttribute("categories", categories);

        // Get list brand
        List<Brand> brands = brandService.getListBrand();
        model.addAttribute("brands", brands);

        return "admin/product/create";
    }

    @PostMapping("/api/admin/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request) {
        long productId = productService.createProduct(request);
        return ResponseEntity.ok(productId);
    }

    @GetMapping("/admin/products/{id}")
    public String getDetailProductPage(Model model, @PathVariable long id) {
        try {
            // Get info
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            System.out.println(product.getCategories());

            // Get list image of user
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            List<String> images = imageService.getListImageOfUser(user.getId());

            model.addAttribute("images", images);

            // Get list category
            List<Category> categories = categoryService.getListCategory();
            model.addAttribute("categories", categories);

            // Get list brand
            List<Brand> brands = brandService.getListBrand();
            model.addAttribute("brands", brands);

            return "admin/product/detail";
        } catch (NotFoundException ex) {
            return "error/admin";
        }
    }

    @PutMapping("/api/admin/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequest request) {
        productService.updateProduct(id, request);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
