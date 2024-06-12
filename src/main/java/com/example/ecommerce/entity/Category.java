package com.example.ecommerce.entity;

import com.example.ecommerce.dto.CategoryInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "categoryInfo",
                        classes = @ConstructorResult(
                                targetClass = CategoryInfo.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Integer.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "product_count", type = Integer.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListCategoryAndProductCount",
        resultSetMapping = "categoryInfo",
        query = "SELECT category.id, category.name, \n" +
                "(\n" +
                "    SELECT count(product_id)\n" +
                "    FROM product_category\n" +
                "    WHERE product_category.category_id = category.id\n" +
                ") product_count \n" +
                "FROM category "
)

@Table(name = "category")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

}