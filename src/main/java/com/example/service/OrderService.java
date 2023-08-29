package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.MealDto;
import com.example.dto.OrderDto;
import com.example.dto.OrderRemarkDto;
import com.example.pojo.Meal;
import com.example.pojo.Order;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends IService<Order>
{
    Page<Meal> getMealPage(int page, int pageSize, LocalDate localDate);
//    float getPriceDto(MealDto mealDto);
    void submit(List<List<BigInteger>> idNumMap, String key, OrderRemarkDto orderRemarkDto, LocalDateTime time);
    Page<OrderDto> getOrderPage(int page, int pageSize, LocalDate localDate);
    Page<MealDto> getOrderMenuNum(int page, int pageSize, LocalDate localDate);
}
