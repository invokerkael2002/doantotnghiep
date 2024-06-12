package com.example.ecommerce.controller.web;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;

import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.security.CustomUserDetails;

import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class    ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/gio-hang")
    public String showShoppingCart(Model model) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<CartItem> cartItemList = shoppingCartService.listCartItems(user);

        model.addAttribute("cartItems", cartItemList);

        model.addAttribute("pageTitle", "Shopping Cart");
        return "shop/shopping_cart";
    }

    @PostMapping("/cart/add/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable("productId") long productId) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        Integer addQuantity = shoppingCartService.addProduct(productId, user);

        return new ResponseEntity<>(addQuantity, HttpStatus.CREATED);
    }

    @PutMapping("/cart/update/{pid}/{qty}")
    public ResponseEntity<?> updateQuantity(@PathVariable("pid") long productId,
                                            @PathVariable("qty") Integer quantity) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        Long subtotal = shoppingCartService.updateQuantity(productId, quantity, user);

        return ResponseEntity.ok(subtotal);
    }

    @DeleteMapping("/cart/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable("productId") long productId) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        shoppingCartService.removeProduct(productId, user);

        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/check-out")
    public String getCheckOut(@ModelAttribute("order") Order order, Model model) {
        try {
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

            model.addAttribute("cart", cartItemRepository.findByUser(user));

            model.addAttribute("totalAmount", shoppingCartService.getTotalAmount(user));

            return "shop/payment";

        } catch (NotFoundException ex) {
            return "error/404";
        }
    }

    @PostMapping("/api/order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        orderService.saveOrder(user, order);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
