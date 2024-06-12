package com.example.ecommerce.dto;

import com.example.ecommerce.entity.Brand;
import com.example.ecommerce.entity.Comment;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductInfoDto {
    private String id;

    private String name;

    private String slug;

    private long price;

    private int totalSold;

    private int quantity;

    private ArrayList<String> productImages;

    private List<Comment> comments;

    private String description;

    private Brand brand;
}
