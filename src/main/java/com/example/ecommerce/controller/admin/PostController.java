package com.example.ecommerce.controller.admin;

import com.example.ecommerce.entity.Post;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.request.PostRequest;
import com.example.ecommerce.security.CustomUserDetails;
import com.example.ecommerce.service.ImageService;
import com.example.ecommerce.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private ImageService imageService;


    @GetMapping("/admin/posts")
    public String getPostManagePage(Model model) {
        model.addAttribute("posts", postService.getAllPost());

        return "admin/blog/list";
    }

    @GetMapping("/admin/posts/create")
    public String getPostCreatePage(Model model) {
        // Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());

        model.addAttribute("images", images);

        return "admin/blog/create";
    }

    @PostMapping("/api/admin/posts")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest request) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        Post post = postService.createPost(request, user);

        return ResponseEntity.ok(post.getId());
    }

    @GetMapping("/admin/posts/{id}")
    public String getPostDetailPage(Model model, @PathVariable long id) {
        // Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());

        model.addAttribute("images", images);

        Post post = postService.getPostById(id);
        model.addAttribute("post", post);

        return "admin/blog/detail";
    }

    @PutMapping("/api/admin/posts/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody PostRequest request, @PathVariable long id) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        postService.updatePost(request, user, id);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/posts/{id}")
    public ResponseEntity<?> deletePost( @PathVariable long id) {
        postService.deletePost(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
