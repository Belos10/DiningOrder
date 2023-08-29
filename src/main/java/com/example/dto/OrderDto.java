package com.example.dto;

import com.example.pojo.Meal;
import com.example.pojo.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OrderDto extends Order {
    private List<MealDto> mealList;
    public OrderDto(List<MealDto> mealList)
    {
        this.mealList = mealList;
    }
}
