package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.MealMapper;
import com.example.pojo.Meal;
import com.example.service.MealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MealServiceImpl extends ServiceImpl<MealMapper, Meal>
        implements MealService {
    @Resource
    private MealMapper mealMapper;

    @Override
    public void addMeal(Meal meal){
        mealMapper.insert(meal);
    }

    @Override
    public void editMeal(Meal meal){
        saveOrUpdate(meal);
    }

    @Override
    public void delMeal(Meal meal) {
        mealMapper.deleteById(meal.getMealId());
    }
}
