package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM post WHERE status = ?1 ORDER BY created_at DESC LIMIT ?2")
    List<Post> getLatestPosts(int publicStatus, int limit); 
}