package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Meal;


public interface MealService extends IService<Meal> {
    void addMeal(Meal meal);
    void editMeal(Meal meal);
    void delMeal(Meal meal);
}
