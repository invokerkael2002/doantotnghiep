package com.example.ecommerce.controller.web;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.security.CustomUserDetails;
import com.example.ecommerce.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/api/add-comment/{productId}")
    public ResponseEntity<Comment> addComment(@Valid @RequestBody Comment comment, @PathVariable long productId) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        commentService.createComment(comment, productId, user.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
