package com.spring.springbootapplication.controller;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.security.LoginUserDetails;
import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.service.LearningDataService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TopPageController {
    @Autowired
    LearningDataService learningDataService;

    @RequestMapping("/top")
    public ModelAndView index(
            ModelAndView mav, 
            HttpServletRequest request,
            @AuthenticationPrincipal LoginUserDetails loginUser) {
                
        mav.setViewName("topLoggedIn");
        String userName = request.getRemoteUser();
        mav.addObject("userName", userName + "  portfolio site");

        //ログイン中のユーザーのLoginUserDetailsを渡す
        User user = loginUser.getUser();
        mav.addObject("user",user);

        //スキルチャート部分
        YearMonth currentMonth = YearMonth.now();
        YearMonth lastMonth = currentMonth.minusMonths(1);
        YearMonth twoMonthsAgo = currentMonth.minusMonths(2);

        //月ごとにデータをまとめる
        List<LearningData> currentMonthData = 
                learningDataService.getLearningData(user,currentMonth);

        List<LearningData> lastMonthData = 
                learningDataService.getLearningData(user,lastMonth);

        List<LearningData> twoMonthsAgoData = 
                learningDataService.getLearningData(user,twoMonthsAgo);

        //カテゴリーごとに学習時間を合計
        Integer twoMonthsAgoBackend = learningDataService.getTotalLearningTime(twoMonthsAgoData,1);
        Integer twoMonthsAgoFrontend = learningDataService.getTotalLearningTime(twoMonthsAgoData,2);
        Integer twoMonthsAgoInfra = learningDataService.getTotalLearningTime(twoMonthsAgoData,3);

        Integer lastMonthBackend = learningDataService.getTotalLearningTime(lastMonthData,1);
        Integer lastMonthFrontend = learningDataService.getTotalLearningTime(lastMonthData,2);
        Integer lastMonthInfra = learningDataService.getTotalLearningTime(lastMonthData,3);

        Integer currentMonthBackend = learningDataService.getTotalLearningTime(currentMonthData,1);
        Integer currentMonthFrontend = learningDataService.getTotalLearningTime(currentMonthData,2);
        Integer currentMonthInfra = learningDataService.getTotalLearningTime(currentMonthData,3);
        
        mav.addObject("twoMonthsAgoBackend", twoMonthsAgoBackend);
        mav.addObject("twoMonthsAgoFrontend", twoMonthsAgoFrontend);
        mav.addObject("twoMonthsAgoInfra", twoMonthsAgoInfra);

        mav.addObject("lastMonthBackend", lastMonthBackend);
        mav.addObject("lastMonthFrontend", lastMonthFrontend);
        mav.addObject("lastMonthInfra", lastMonthInfra);

        mav.addObject("currentMonthBackend", currentMonthBackend);
        mav.addObject("currentMonthFrontend", currentMonthFrontend);
        mav.addObject("currentMonthInfra", currentMonthInfra);
        
        return mav;
    }
}
