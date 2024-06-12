package com.example.ecommerce.repository;

import com.example.ecommerce.dto.CategoryInfo;
import com.example.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(nativeQuery = true, name = "getListCategoryAndProductCount")
    List<CategoryInfo> getListCategoryAndProductCount();

}