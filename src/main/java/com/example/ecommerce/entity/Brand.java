package com.example.ecommerce.entity;

import com.example.ecommerce.dto.BrandInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "brandInfo",
                        classes = @ConstructorResult(
                                targetClass = BrandInfo.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Integer.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "thumbnail", type = String.class),
                                        @ColumnResult(name = "product_count", type = Integer.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListBrandAndProductCount",
        resultSetMapping = "brandInfo",
        query = "SELECT brand.id, brand.name, brand.thumbnail,\n" +
                "(\n" +
                "    SELECT count(product.id)\n" +
                "    FROM product\n" +
                "    WHERE product.brand_id = brand.id\n" +
                ") product_count \n" +
                "FROM brand "
)

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

}