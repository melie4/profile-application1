package com.spring.springbootapplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Data;

import com.spring.springbootapplication.entity.User;

import java.time.LocalDateTime;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.spring.springbootapplication.entity.Categories;

@Data
@Entity
@Table(name = "learning_data")
public class LearningData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @Size(max = 255)
    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "learning_time")
    private Integer learningTime;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updateAt;

    @Column(name = "target_month")
    private LocalDate targetMonth;
}
