package com.spring.springbootapplication.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.springbootapplication.entity.Categories;
import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.entity.User;

@Repository
public interface SkillListRepository extends JpaRepository<LearningData,Integer>{
     List<LearningData> findByUserAndTargetMonthBetween(
        User user,
        LocalDate startDate,
        LocalDate endDate
    );

    boolean existsBySkillName(String SkillName);

    boolean existsByUserAndCategoryAndSkillNameAndTargetMonth(
            User user,
            Categories category,
            String skillName,
            LocalDate targetMonth
    );
}
