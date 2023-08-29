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
    Map<Integer, Integer> mealMap = new HashMap<>();
    float price = 0;
}
