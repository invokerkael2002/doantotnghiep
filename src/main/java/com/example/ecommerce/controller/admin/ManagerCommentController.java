package com.example.ecommerce.controller.admin;

import com.example.ecommerce.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagerCommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/admin/comments")
    public String getProductManagePage(Model model,
                                       @RequestParam(required = false, defaultValue = "1") int page,
                                       @RequestParam(required = false, defaultValue = "8") int size) {

        model.addAttribute("data", commentService.getAll(page, size));

        return "admin/comment/list";
    }

    @DeleteMapping("/api/admin/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
