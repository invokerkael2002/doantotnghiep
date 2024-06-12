package com.example.ecommerce.service;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.exception.InternalServerException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CommentRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class CommentService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment createComment(Comment comment, long productId, Long userId) {

        Product product = productRepository.findById(productId).get();

        User user = userRepository.getReferenceById(userId);

        comment.setProduct(product);

        comment.setUser(user);

        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        commentRepository.save(comment);

        return comment;
    };

    public PageUtil<Comment> getAll(int page, int size) {
        Page<Comment> pageInfo = commentRepository.findAll(PageRequest.of(page - 1, size));
        return new PageUtil<>(
                pageInfo.getContent(),
                pageInfo.getPageable().getPageNumber() + 1,
                pageInfo.getPageable().getPageSize(),
                IntStream.range(1, pageInfo.getTotalPages() + 1).boxed().toList(),
                pageInfo.getTotalElements(),
                pageInfo.isFirst(),
                pageInfo.isLast()
        );
    }

    public void deleteComment(long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new NotFoundException("Comment không tồn tại");
        }
        try {
            commentRepository.delete(commentOptional.get());
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa bình luận");
        }
    }
}
