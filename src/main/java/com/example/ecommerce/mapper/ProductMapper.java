package com.example.ecommerce.mapper;

import com.example.ecommerce.entity.Brand;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.request.ProductRequest;
import com.github.slugify.Slugify;

import java.util.ArrayList;

public class ProductMapper {
    public static Product toProduct(ProductRequest req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setAvailable(req.isAvailable());
        product.setTotalSold(req.getTotalSold());
        product.setQuantity(req.getQuantity());
        product.setProductImages(req.getProductImages());
        // Gen slug
        Slugify slg = new Slugify();
        product.setSlug(slg.slugify(req.getName()));
        // Set brand
        Brand brand = new Brand();
        brand.setId(req.getBrandId());
        product.setBrand(brand);
        // Set category
        ArrayList<Category> categories = new ArrayList<Category>();
        for (Integer id : req.getCategoryIds()) {
            Category category = new Category();
            category.setId(id);
            categories.add(category);
        }
        product.setCategories(categories);

        return product;
    }
}
