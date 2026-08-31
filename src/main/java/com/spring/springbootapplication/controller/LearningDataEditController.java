package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.YearMonth;

import com.spring.springbootapplication.security.LoginUserDetails;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.LearningDataService;
import com.spring.springbootapplication.entity.LearningData;

@Controller
public class LearningDataEditController {
    @Autowired
    LearningDataService learningDataService;

    @RequestMapping(value = "/learningData-update", method = RequestMethod.POST)
    public ModelAndView form(
        @RequestParam Integer learningDataId,
        @RequestParam Integer learningTime,
        @RequestParam YearMonth targetMonth,
        @AuthenticationPrincipal LoginUserDetails loginUser,
        RedirectAttributes redirectAttributes)
    {
        if(learningTime < 0)
        {
            redirectAttributes.addFlashAttribute(
                "learningTimeError",
                "学習時間は0以上で入力してください"
            );

            redirectAttributes.addFlashAttribute(
                "errorLearningDataId",
                learningDataId
            );

            return new ModelAndView("redirect:/learningData?month=" + targetMonth);
        }


       User user = loginUser.getUser();

        LearningData learningData = 
            learningDataService.updateLearningData(user,learningDataId,learningTime);

        redirectAttributes.addFlashAttribute("isUpdateSuccess",true);
        redirectAttributes.addFlashAttribute("updateSkillName",learningData.getSkillName());

        return new ModelAndView("redirect:/learningData?month=" + targetMonth);


    }
}
