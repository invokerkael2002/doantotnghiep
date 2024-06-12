package com.example.ecommerce.request;

import lombok.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {
    @NotNull(message = "Nội dung rỗng")
    @NotEmpty(message = "Nội dung rỗng")
    private String content;

    private Timestamp createdAt;


}
