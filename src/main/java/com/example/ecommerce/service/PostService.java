package com.example.ecommerce.service;

import com.example.ecommerce.entity.Post;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.InternalServerException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.PostRepository;
import com.example.ecommerce.request.PostRequest;
import com.example.ecommerce.util.PageUtil;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import static com.example.ecommerce.config.Constant.*;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public PageUtil<Post> getAll(int page, int size) {
        Page<Post> pageInfo = postRepository.findAll(PageRequest.of(page-1, size));
        if (page > (pageInfo.getTotalElements() / pageInfo.getPageable().getPageSize()) + 1) {
            throw new NotFoundException("Không tìm được trang");
        }
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

    public List<Post> getLatestPost() {
        List<Post> posts = postRepository.getLatestPosts(PUBLIC_POST, 5);
        return posts;
    }

    public Post createPost(PostRequest request, User user) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        Slugify slg = new Slugify();
        post.setSlug(slg.slugify(request.getTitle()));
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        post.setCreatedBy(user);

        if (request.getStatus() == PUBLIC_POST) {
            // Public post
            if (request.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai bài viết vui lòng nhập mô tả");
            }
            if (request.getImage().isEmpty()) {
                throw new BadRequestException("Vui lòng chọn ảnh cho bài viết trước khi công khai");
            }
            post.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        } else {
            if (request.getStatus() != DRAFT_POST) {
                throw new BadRequestException("Trạng thái bài viết không hợp lệ");
            }
        }
        post.setDescription(request.getDescription());
        post.setThumbnail(request.getImage());
        post.setStatus(request.getStatus());

        postRepository.save(post);

        return post;
    }

    public void updatePost(PostRequest request, User user, long id) {
        Optional<Post> rs = postRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Bài viết không tồn tại");
        }

        Post post = rs.get();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        Slugify slg = new Slugify();
        post.setSlug(slg.slugify(request.getTitle()));
        post.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        post.setModifiedBy(user);
        if (request.getStatus() == PUBLIC_POST) {
            // Public post
            if (request.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai bài viết vui lòng nhập mô tả");
            }
            if (request.getImage().isEmpty()) {
                throw new BadRequestException("Vui lòng chọn ảnh cho bài viết trước khi công khai");
            }
            if (post.getPublishedAt() == null) {
                post.setPublishedAt(new Timestamp(System.currentTimeMillis()));
            }
        } else {
            if (request.getStatus() != DRAFT_POST) {
                throw new BadRequestException("Trạng thái bài viết không hợp lệ");
            }
        }
        post.setDescription(request.getDescription());
        post.setThumbnail(request.getImage());
        post.setStatus(request.getStatus());

        try {
            postRepository.save(post);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật bài viết");
        }
    }

    public void deletePost(long id) {
        Optional<Post> rs = postRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Bài viết không tồn tại");
        }

        try {
            postRepository.delete(rs.get());
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa bài viết");
        }
    }

    public Post getPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new NotFoundException("Không tìm thấy tin tức");
        }

        return post.get();
    }
}
