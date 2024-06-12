package com.example.ecommerce.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    @Pattern(regexp="(09|01[2|6|8|9])+([0-9]{8})\\b", message = "Điện thoại không hợp lệ")
    private String phone;

    @NotNull(message = "Họ tên trống")
    @NotEmpty(message = "Họ tên trống")
    @JsonProperty("full_name")
    private String fullName;

    private String address;
}