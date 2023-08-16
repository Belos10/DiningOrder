package com.example.dto;


import com.example.pojo.Meal;
import com.example.pojo.Order;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


//public class OrderDto extends Order{
//    Map<Meal, Integer> orderMap = new HashMap<>();
//}
@Data
public class OrderDto{
    Map<Meal, Integer> orderMap = new HashMap<>();
    float price = 0;
}
