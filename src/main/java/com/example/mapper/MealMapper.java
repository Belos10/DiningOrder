package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.Meal;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MealMapper extends BaseMapper<Meal> {
    void addMeal(Meal meal);
}
