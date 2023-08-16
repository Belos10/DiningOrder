package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.R;
import com.example.dto.OrderDto;
import com.example.pojo.Meal;
import com.example.pojo.Order;

import java.time.LocalDateTime;

public interface OrderService extends IService<Order>
{
    Page<Meal> getMealPage(int page, int pageSize);
    float getPriceDto(OrderDto orderDto);
    void submit(OrderDto orderDto, String key, String remark, LocalDateTime time);
}
