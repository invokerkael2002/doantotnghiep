package com.example.ecommerce.repository;

import com.example.ecommerce.dto.BrandInfo;
import com.example.ecommerce.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(nativeQuery = true, name = "getListBrandAndProductCount")
    List<BrandInfo> getListBrandAndProductCount();
}