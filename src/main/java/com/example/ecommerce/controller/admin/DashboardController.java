package com.example.ecommerce.controller.admin;

import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/admin")
    public String getDashboardPage(Model model) {
        model.addAttribute("countBrands", brandRepository.count());
        model.addAttribute("countCategories", categoryRepository.count());
        model.addAttribute("countProducts", productRepository.count());
        model.addAttribute("countOrders", orderRepository.count());
        model.addAttribute("countUsers", userRepository.count());
        model.addAttribute("countPost", postRepository.count());
        model.addAttribute("countComment", commentRepository.count());
        return "admin/dashboard";
    }
}
