package com.spring.springbootapplication.controller;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.dto.LearningDataAddDto;
import com.spring.springbootapplication.entity.Categories;
import com.spring.springbootapplication.security.LoginUserDetails;
import com.spring.springbootapplication.repository.CategoriesRepository;
import com.spring.springbootapplication.service.LearningDataAddService;
import com.spring.springbootapplication.entity.User;


import jakarta.servlet.http.HttpServletRequest;


@Controller
public class LearningDataAddController {
    @Autowired
    CategoriesRepository repository;

    @Autowired
    LearningDataAddService service;

    @RequestMapping("/learning-data-add")
    public ModelAndView index(
            @RequestParam String month,
            @RequestParam Integer categoryId,
            HttpServletRequest request,
            ModelAndView mav){
        
        LearningDataAddDto dto = new LearningDataAddDto();

        Categories category = repository.findById(categoryId).orElseThrow();

        dto.setTargetMonth(YearMonth.parse(month).atDay(1));
        dto.setCategory(category);

        mav.setViewName("learningDataAdd");
        mav.addObject("learningDataModel",dto);
        mav.addObject("selectedMonth",YearMonth.from(dto.getTargetMonth()));


        String userName = request.getRemoteUser();
        mav.addObject("userName", userName + "  portfolio site");

        return mav;
                
    }

    @RequestMapping(value = "/learning-data-add", method= RequestMethod.POST)
    public ModelAndView form(
            @ModelAttribute("learningDataModel") @Validated LearningDataAddDto dto,
            BindingResult result,
            @AuthenticationPrincipal LoginUserDetails loginUser,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            ModelAndView mav){

        //バリデーションチェック        
        if(result.hasErrors()){
            mav.setViewName("learningDataAdd");

            String userName = request.getRemoteUser();
            mav.addObject("userName", userName + "  portfolio site");

            Categories category = repository
                .findById(dto.getCategory().getId())
                .orElseThrow();
            dto.setCategory(category);

            return mav;
        }

        User user = loginUser.getUser();

        //項目追加中のエラー処理
        try {service.addLearningData(user,dto);}
        catch (IllegalArgumentException e) {
        mav.setViewName("learningDataAdd");
        mav.addObject("errorMessage", e.getMessage());

        Categories category = repository
            .findById(dto.getCategory().getId())
            .orElseThrow();
        dto.setCategory(category);

        String userName = request.getRemoteUser();
        mav.addObject("userName", userName + "  portfolio site");

        return mav;
        }

        //二重登録を防ぐための処理
        redirectAttributes.addFlashAttribute("isSuccess",true);
        redirectAttributes.addFlashAttribute("addSkillName",dto.getSkillName());
        redirectAttributes.addFlashAttribute("addLearningTime",dto.getLearningTime());

        Categories category = repository
            .findById(dto.getCategory().getId())
            .orElseThrow();
        redirectAttributes.addFlashAttribute("addCategoryName",category.getCategoryName());
        return new ModelAndView("redirect:/learning-data-add"
                               + "?month=" +  YearMonth.from(dto.getTargetMonth()) 
                               + "&categoryId=" + dto.getCategory().getId()
                        
        );
    }
        
    


}
