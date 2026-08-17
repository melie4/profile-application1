package com.spring.springbootapplication.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Pattern(
        regexp = "^$|^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",
        message = "メールアドレスが正しい形式ではありません"
    )
    private String email;

    
    @NotBlank
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "英数字8文字以上で入力してください"
    )
    private String password;
}