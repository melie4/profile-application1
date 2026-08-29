package com.spring.springbootapplication.dto;

import java.time.LocalDate;

import com.spring.springbootapplication.entity.Categories;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LearningDataAddDto {
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @Size(max = 50)
    @NotBlank
    @Column(name = "skill_name")
    private String skillName;

    @NotNull
    @Min(0)
    @Column(name = "learning_time")
    private Integer learningTime;

    @Column(name = "target_month")
    private LocalDate targetMonth;
}
