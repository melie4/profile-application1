package com.spring.springbootapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.springbootapplication.entity.Categories;
import com.spring.springbootapplication.entity.LearningData;

@Repository
public interface  CategoriesRepository extends JpaRepository<Categories,Integer>{
    
}
