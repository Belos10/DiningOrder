package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.MealDto;
import com.example.dto.OrderDto;
import com.example.pojo.Meal;
import com.example.pojo.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OrderService extends IService<Order>
{
    Page<Meal> getMealPage(int page, int pageSize);
    float getPriceDto(MealDto mealDto);
    void submit(MealDto mealDto, String key, String remark, LocalDateTime time);
    Page<OrderDto> getOrderPage(int page, int pageSize, LocalDate localDate);
}
