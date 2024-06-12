package com.example.ecommerce.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryInfo {
    private int id;

    private String name;

    private int productCount;
}
