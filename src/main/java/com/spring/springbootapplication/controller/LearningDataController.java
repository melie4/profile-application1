package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.security.LoginUserDetails;
import com.spring.springbootapplication.service.LearningDataService;

import jakarta.servlet.http.HttpServletRequest;

import com.spring.springbootapplication.entity.User;


@Controller
public class LearningDataController {
    private final LearningDataService learningDataService;

    public LearningDataController(LearningDataService learningDataService){
        this.learningDataService = learningDataService;
    }


    @RequestMapping("/learningData")
    public ModelAndView index(
            @RequestParam(required = false) String month,
            @AuthenticationPrincipal LoginUserDetails loginUser,
            HttpServletRequest request,
            ModelAndView mav){
        
        User user = loginUser.getUser();

        YearMonth currentMonth = YearMonth.now();

        //当月含め、過去3か月を表示
        List<YearMonth> months = List.of(
            currentMonth,
            currentMonth.minusMonths(1),
            currentMonth.minusMonths(2)
        );


        //最初にtopページを開いた際は当月のデータを表示
        YearMonth selectedMonth;
        if(month == null){
            selectedMonth = currentMonth;
        }
        else{
            selectedMonth = YearMonth.parse(month);
        }

        //該当の月の項目ごとの学習時間を取得
        List<LearningData> learningDataList = 
            learningDataService.getLearningData(user, selectedMonth);

        boolean hasBackendData = learningDataList.stream()
                    .anyMatch(data -> data.getCategory().getId() == 1);

        boolean hasFrontendData = learningDataList.stream()
                    .anyMatch(data -> data.getCategory().getId() == 2);            

        boolean hasInfraData = learningDataList.stream()
                    .anyMatch(data -> data.getCategory().getId() == 3); 

        mav.setViewName("learningData");
        mav.addObject("user",user);
        mav.addObject("months",months);
        mav.addObject("selectedMonth", selectedMonth);
        mav.addObject("learningDataList",learningDataList);
        mav.addObject("hasBackendData",hasBackendData);
        mav.addObject("hasFrontendData",hasFrontendData);
        mav.addObject("hasInfraData",hasInfraData);

        
        return mav;

        

    }

    
    
}
