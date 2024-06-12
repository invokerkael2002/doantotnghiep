package com.example.ecommerce.service;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.InternalServerException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.example.ecommerce.config.Constant.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(User user, Order order) {

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        long totalAmount = 0;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setStatus(ORDER_STATUS);
            order.addOrderItem(orderItem);
            orderItem.setOrder(order);
            totalAmount += cartItem.getProduct().getPrice() * cartItem.getQuantity();
            cartItemRepository.delete(cartItem);
        }

        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        order.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        order.setTotalAmount(totalAmount);
        order.setReceiverAddress(order.getReceiverAddress());
        order.setReceiverName(order.getReceiverName());
        order.setReceiverPhone(order.getReceiverPhone());

        order.setBuyer(user);
        order.setStatus(ORDER_STATUS);

        orderRepository.save(order);

        return order;

//        OrderInfoDto orderDTO = new OrderInfoDto();
//
//        orderDTO.setReceiverName(order.getReceiverName());
//        orderDTO.setReceiverPhone(order.getReceiverPhone());
//        orderDTO.setReceiverAddress(order.getReceiverAddress());
//        orderDTO.setPaymentMethod(order.getPaymentMethod());
//
//        return orderDTO;
    }

    public List<Order> getOrdersByBuyer(User user) {
        return orderRepository.getOrdersByBuyer(user);
    }

    public Order getOrderById(long id) {
        return orderRepository.getOrderById(id);
    }

    public void userCancelOrder(Order order) {
        if (order.getStatus() != ORDER_STATUS) {
            throw new BadRequestException("Chỉ có thể hủy đơn hàng ở trạng chờ lấy hàng");
        }
        order.setStatus(CANCELED_STATUS);
        order.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        order.setTotalAmount(0l);
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem oi : orderItems) {
            oi.setStatus(CANCELED_STATUS);
        }
        orderRepository.save(order);
    }

    public void updateStatusOrder(Order order, long id) {
        // Validate status of order
        Optional<Order> rs = orderRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Đơn hàng không tồn tại");
        }
        Order order1 = rs.get();

        // Validate status of order
        boolean statusIsValid = false;
        for (Integer status : LIST_ORDER_STATUS) {
            if (order1.getStatus() == CANCELED_STATUS) {
                throw new BadRequestException("Đơn hàng trạng thái ở hủy đơn không được phép thay đổi");
            }
            if (status == order1.getStatus()) {
                statusIsValid = true;
                break;
            }
        }
        if (!statusIsValid) {
            throw new BadRequestException("Trạng thái đơn hàng không hợp lệ");
        }

        order1.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        order1.setStatus(order.getStatus());
        if (order1.getStatus() == CANCELED_STATUS) {
            order1.setTotalAmount(0l);
        }
        List<OrderItem> orderItems = order1.getOrderItems();
        for (OrderItem oi : orderItems) {
            oi.setStatus(order1.getStatus());
            if (order1.getStatus() == COMPLETE_STATUS) {
                productRepository.plusOneProduct(oi.getProduct().getId(), oi.getQuantity());
                productRepository.minusOneProduct(oi.getProduct().getId(), oi.getQuantity());
            }
        }
        try {
            orderRepository.save(order1);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật đơn hàng");
        }
    }
}