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

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDto extends Order {
    private List<Object> mealList;


    public OrderDto(List<Object> mealList)
    {
        this.mealList = mealList;
    }
}
