package com.example.ecommerce.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotNull(message = "Mật khẩu cũ trống")
    @NotEmpty(message = "Mật khẩu cũ trống")
    @Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4 - 20 ký tự")
    @JsonProperty("old_password")
    private String oldPassword;

    @NotNull(message = "Mật khẩu mới trống")
    @NotEmpty(message = "Mật khẩu mới trống")
    @Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4 - 20 ký tự")
    @JsonProperty("new_password")
    private String newPassword;
}
