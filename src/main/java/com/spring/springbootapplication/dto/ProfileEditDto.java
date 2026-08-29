package com.spring.springbootapplication.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileEditDto {
    @Size(min = 50, max = 200, message="自己紹介は50文字以上200文字以下で入力してください")
    @NotBlank(message = "自己紹介文を入力してください")
    private String selfIntroduction;

}
