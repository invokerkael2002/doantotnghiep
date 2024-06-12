package com.example.ecommerce.controller.admin;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.request.CreateUserRequest;
import com.example.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
public class ManagerUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/admin/users")
    public String getUserManagePage(Model model) {

        model.addAttribute("users", userRepository.findAll());

        return "admin/user/list";
    }

    @PostMapping("/api/admin/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest request) {
        User result = userService.createAdmin(request);

        return ResponseEntity.ok(modelMapper.map(result, UserDto.class));
    }

    @DeleteMapping("/api/admin/users/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
