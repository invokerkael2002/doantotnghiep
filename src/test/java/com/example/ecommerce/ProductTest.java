package com.example.ecommerce;

import com.example.ecommerce.dto.ProductInfoDto;
import com.example.ecommerce.entity.Comment;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CommentRepository;
import com.example.ecommerce.service.CommentService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.util.PageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Test
    void getAll() {
        PageUtil<Product> products = productService.getAll(1, 10);
        System.out.println(products);
    }

    @Test
    void searchProducts() {
        PageUtil<Product> products = productService.searchProducts(1, 10, "apple");
        System.out.println(products);
    }

    @Test
    void getListProductLimit() {
        List<Product> products = productService.getListProductLimit();
        products.forEach(System.out::println);
    }
}
