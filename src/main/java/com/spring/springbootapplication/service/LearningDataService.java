package com.spring.springbootapplication.service;

import java.time.YearMonth;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.repository.SkillListRepository;
import com.spring.springbootapplication.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LearningDataService {
    @Autowired
    SkillListRepository repository;

    public List<LearningData> getLearningData(
            User user,
            YearMonth selectedMonth){
            
        //選択された月から取得するデータの範囲を指定
        LocalDate startDate = selectedMonth.atDay(1);
        LocalDate endDate = selectedMonth.atEndOfMonth();

        
        return repository.findByUserAndTargetMonthBetween(user, startDate, endDate);


    }


    //学習データ編集
    public LearningData updateLearningData(
            User user,
            Integer learningDataId,
            Integer learningTime)
    {
        LearningData learningData = 
            repository.findByIdAndUser(learningDataId,user)
                      .orElseThrow();

        learningData.setLearningTime(learningTime);
        LocalDateTime now = LocalDateTime.now();
        learningData.setUpdateAt(now);

        return repository.saveAndFlush(learningData);

    }

    //学習データ削除
    public LearningData deleteLearningData(
            User user,
            Integer learningDataId)
    {
        LearningData learningData = 
            repository.findByIdAndUser(learningDataId,user)
                      .orElseThrow();

        repository.delete(learningData);
        return learningData;
    }

}