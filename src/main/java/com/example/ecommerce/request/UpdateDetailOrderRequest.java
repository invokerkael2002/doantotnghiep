package com.example.ecommerce.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDetailOrderRequest {
    @NotNull(message = "Sản phẩm trống")
    @NotEmpty(message = "Sản phẩm trống")
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("coupon_code")
    private String couponCode;

    @JsonProperty("total_price")
    private long totalPrice;

    @JsonProperty("product_price")
    private long productPrice;
}
