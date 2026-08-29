package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.dto.LearningDataAddDto;
import com.spring.springbootapplication.entity.Categories;
import com.spring.springbootapplication.repository.CategoriesRepository;
import com.spring.springbootapplication.repository.SkillListRepository;

import java.time.LocalDateTime;
import java.time.YearMonth;

import com.spring.springbootapplication.entity.LearningData;

@Service
@Transactional
public class LearningDataAddService {
    @Autowired
    CategoriesRepository categoryRepository;

    @Autowired
    SkillListRepository skillRepository;

    public void addLearningData(User user,LearningDataAddDto dto){
        Categories category = 
            categoryRepository
                .findById(dto.getCategory().getId())
                .orElseThrow(
                    () -> new IllegalArgumentException("カテゴリが見つかりません")
                );


        if(skillRepository.existsByUserAndCategoryAndSkillNameAndTargetMonth(
                    user,
                    dto.getCategory(),
                    dto.getSkillName(),
                    dto.getTargetMonth())){
            throw new IllegalArgumentException(dto.getSkillName() + "は既に登録されています");
        }
        
        LearningData learningData = new LearningData();

        learningData.setUser(user);
        learningData.setCategory(category);
        learningData.setSkillName(dto.getSkillName());
        learningData.setLearningTime(dto.getLearningTime());
        learningData.setTargetMonth(dto.getTargetMonth());
        LocalDateTime now = LocalDateTime.now();
        learningData.setCreatedAt(now);
        learningData.setUpdateAt(now);

        skillRepository.saveAndFlush(learningData);
    }
}
