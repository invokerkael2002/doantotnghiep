package com.example.ecommerce.service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> listCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public Integer addProduct(Long productId, User user) {

        Integer addQuantity = 1;

        Product product = productRepository.findById(productId).get();

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);

        Product productCheckAvailable = productRepository.checkProductAvailable(product.getId());
        if (productCheckAvailable == null) {
            throw new BadRequestException("Sản phẩm này tạm hết. Vui lòng chọn sản phẩm khác");
        }

        if (cartItem != null) {
            addQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(addQuantity);
        }
        else {
            cartItem = new CartItem();
            cartItem.setQuantity(1);
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }
        cartItemRepository.save(cartItem);

        return addQuantity;
    }

    public void removeProduct(Long productId, User user) {
        cartItemRepository.deleteByUserAndProduct(user.getId(), productId);
    }

    public Long updateQuantity(Long productId, Integer quantity, User user) {
        cartItemRepository.updateQuantity(quantity, productId, user.getId());

        Product product = productRepository.findById(productId).get();

        Long subTotal = product.getPrice() * quantity;

        return subTotal;
    }

    public long getTotalAmount(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        long totalAmount = 0;
        for (CartItem ci : cartItems) {
            totalAmount += ci.getProduct().getPrice() * ci.getQuantity();
        }
        if(totalAmount == 0 ) {
            throw new NotFoundException("không tìm thấy đơn hàng");
        }
        return totalAmount;
    }
}
