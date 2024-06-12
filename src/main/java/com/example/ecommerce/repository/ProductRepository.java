package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    int countByBrandId(int id);

    Page<Product> findProductByNameContaining(String keyword, Pageable pageable);

    Page<Product> getAllByCategories_Id(int id, Pageable pageable);

    Page<Product> getAllByBrand_Id(int id, Pageable pageable);

    @Query(nativeQuery = true, name = "getListProductLimit")
    List<Product> getListProductLimit(int limit);

    @Query("SELECT p from Product p WHERE p.price < 2000000")
    Page<Product> getProductsList1(Pageable pageable);

    @Query("SELECT p from Product p WHERE p.price > 2000000 and p.price < 6000000")
    Page<Product> getProductsList2(Pageable pageable);

    @Query("SELECT p from Product p WHERE p.price > 6000000 and p.price < 10000000")
    Page<Product> getProductsList3(Pageable pageable);

    @Query("SELECT p from Product p WHERE p.price > 10000000")
    Page<Product> getProductsList4(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM product WHERE id = ?1 AND quantity > 0")
    Product checkProductAvailable(Long id);

    @Query(nativeQuery = true, value = "SELECT quantity FROM product WHERE id = ?1 AND quantity > 0")
    List<Integer> findAllQuantityOfProduct(Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Update product set total_Sold = total_Sold + ?2 where id = ?1")
    void plusOneProduct(long id, Integer amount);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Update product set quantity = quantity - ?2 where id = ?1")
    void minusOneProduct(long id, Integer amount);

    @Query(nativeQuery = true, value = "SELECT * FROM product WHERE quantity > 0")
    List<Product> getAvailableProducts();
}
