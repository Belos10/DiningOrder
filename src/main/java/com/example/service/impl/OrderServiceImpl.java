package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.MealDto;
import com.example.dto.OrderDto;
import com.example.mapper.MealMapper;
import com.example.mapper.OrderMapper;
import com.example.pojo.Meal;
import com.example.pojo.Order;
import com.example.service.OrderService;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MealMapper mealMapper;


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
    public float getPriceDto(MealDto mealDto)
    {
        Map<Meal, Integer> map = mealDto.getMealMap();
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
    public void submit(MealDto mealDto, String key, String remark, LocalDateTime time){
        for(Meal meal: mealDto.getMealMap().keySet()){
            Order order = new Order();
            order.setOrderTime(time);
            order.setKey(key);
            order.setMealId(meal.getMealId());
            order.setNum(mealDto.getMealMap().get(meal));
            order.setRemark(remark);
            orderMapper.insert(order);
        }

    }

//    @Override
//    public Page<MealDto> getOrderPage(int page, int pageSize)
//    {
//        Page<MealDto> pageInfo = new Page<>(page, pageSize);
//        List<Map> mapList = orderMapper.getKeyandmealList(page, pageSize);
//        for(var map: mapList){
//            String key = (String) map.get("keyList");
//            LocalDateTime orderTime = (LocalDateTime) map.get("order_time");
//            String remark = (String) map.get("remark");
//            List<Integer> mealIdList = (List<Integer>)map.get("mealIdList");
//            List<Integer> numList = (List<Integer>)map.get("numList");
//            Map<Meal, Integer> mealList = new HashMap<>();
//            for(int i = 0; i < mealIdList.size(); i++){
//                Meal meal = mealMapper.selectById(mealIdList.get(i));
//                mealList.put(meal, numList.get(i));
//            }
//            OrderDto orderDto = new OrderDto(key, orderTime, remark, mealList){
//                @Override
//                public void setKey(String key)
//                {
//                    super.setKey(key);
//                }
//                set
//            };
//
//        }
////        for(String key:map.keySet()){
////            List<Integer> list = map.get(key);
////            int mealId = list.get(0);
////            int num = list.get(1);
////
////        }
////        List<Meal> records =  orderMapper.getMealPage(page, pageSize);
////        pageInfo.setRecords(records);
////        pageInfo.setTotal(orderMapper.getMealPageCount());
//        return pageInfo;
//    }

    @Override
    public Page<OrderDto> getOrderPage(int page, int pageSize, LocalDate localDate)
    {
        Page<OrderDto> pageInfo = new Page<>(page, pageSize);
        List<Map> mapList = orderMapper.getKeyandmealList(page, pageSize, localDate);
        List<OrderDto> records = new ArrayList<>();
        for(var map: mapList){
            String key = (String) map.get("keyList");
            LocalDateTime orderTime = (LocalDateTime) map.get("order_time");
            String remark = (String) map.get("remark");
            List<Integer> mealIdList = (List<Integer>)map.get("mealIdList");
            List<Integer> numList = (List<Integer>)map.get("numList");
            Map<Meal, Integer> mealList = new HashMap<>();
            for(int i = 0; i < mealIdList.size(); i++){
                Meal meal = mealMapper.selectById(mealIdList.get(i));
                mealList.put(meal, numList.get(i));
            }
            OrderDto orderDto = new OrderDto(mealList);
            orderDto.setKey(key);
            orderDto.setRemark(remark);
            orderDto.setOrderTime(orderTime);
            records.add(orderDto);
        }
        pageInfo.setRecords(records);
        pageInfo.setTotal(mapList.size());
        return pageInfo;
    }
}