package com.example.ecommerce.service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.DuplicateException;
import com.example.ecommerce.exception.InternalServerException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.request.ChangePasswordRequest;
import com.example.ecommerce.request.CreateUserRequest;
import com.example.ecommerce.request.UpdateProfileRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public User createUser(CreateUserRequest request) {
        // Check email exist
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            throw new DuplicateException("Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác.");
        }

        user = modelMapper.map(request, User.class);
        // Hash password using BCrypt
        String hash = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hash);

        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        user.setStatus(true);

        user.setRoles(new ArrayList<String>(Arrays.asList("USER")));

        userRepository.save(user);

        return user;
    }

    public User createAdmin(CreateUserRequest request) {
        // Check email exist
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            throw new DuplicateException("Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác.");
        }

        user = modelMapper.map(request, User.class);
        // Hash password using BCrypt
        String hash = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hash);

        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        user.setStatus(true);

        user.setRoles(new ArrayList<String>(Arrays.asList("USER", "ADMIN")));

        userRepository.save(user);

        return user;
    }

    public void changePassword(User user, ChangePasswordRequest request) {
        // Validate password
        if (!BCrypt.checkpw(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }

        String hash = BCrypt.hashpw(request.getNewPassword(), BCrypt.gensalt(10));
        user.setPassword(hash);
        userRepository.save(user);
    }

    public User updateProfile(User user, UpdateProfileRequest request) {
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setFullName(request.getFullName());

        return userRepository.save(user);
    }

    public void deleteUser(long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            throw new NotFoundException("Tài khoản không tồn tại");
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa tai khoan");
        }
    }
}
