package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.R;
import com.example.dto.OrderDto;
import com.example.mapper.OrderMapper;
import com.example.pojo.Meal;
import com.example.pojo.Order;
import com.example.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Resource
    private OrderMapper orderMapper;


    @Override
    public Page<Meal> getMealPage(int page, int pageSize)
    {
        Page<Meal> pageInfo = new Page<>(page, pageSize);
        List<Meal> records =  orderMapper.getMealPage(page, pageSize);
        pageInfo.setRecords(records);
        pageInfo.setTotal(orderMapper.getMealPageCount());
        return pageInfo;
    }

    @Override
    public float getPriceDto(OrderDto orderDto)
    {
        Map<Meal, Integer> map = orderDto.getOrderMap();
        float sum = (float) 0;
        if(map.isEmpty())
            return sum;
        for(Meal meal:map.keySet()){
            int num = map.get(meal);
            float cost = meal.getCost();
            sum += num * cost;
        }
        return sum;
    }

    @Override
    public void submit(OrderDto orderDto, String key, String remark, LocalDateTime time){
        for(Meal meal:orderDto.getOrderMap().keySet()){
            Order order = new Order();
            order.setOrderTime(time);
            order.setKey(key);
            order.setMealId(meal.getMealId());
            order.setRemark(remark);
            orderMapper.insert(order);
        }

    }
}
