package com.example.dto;


import com.example.pojo.Meal;
import com.example.pojo.Order;
import lombok.Data;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


//public class MealDto extends Order{
//    Map<Meal, Integer> orderMap = new HashMap<>();
//}
@Data
public class MealDto {
    private Meal meal;
    private int num;

    public MealDto(Meal meal, int num)
    {
        this.meal = meal;
        this.num = num;
    }
}
