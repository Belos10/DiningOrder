package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.MealDto;
import com.example.dto.OrderDto;
import com.example.dto.OrderRemarkDto;
import com.example.mapper.MealMapper;
import com.example.mapper.OrderMapper;
import com.example.pojo.Meal;
import com.example.pojo.Order;
import com.example.service.OrderService;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MealMapper mealMapper;


    @Override
    public Page<Meal> getMealPage(int page, int pageSize, LocalDate localDate)
    {
        Page<Meal> pageInfo = new Page<>(page, pageSize);
        List<Meal> records =  orderMapper.getMealPage(page, pageSize, localDate);
        pageInfo.setRecords(records);
        pageInfo.setTotal(orderMapper.getMealPageCount(localDate));
        return pageInfo;
    }

//    @Override
//    public float getPriceDto(MealDto mealDto)
//    {
//        Map<Meal, Integer> map = mealDto.getMealMap();
//        float sum = (float) 0;
//        if(map.isEmpty())
//            return sum;
//        for(Meal meal:map.keySet()){
//            int num = map.get(meal);
//            float cost = meal.getCost();
//            sum += num * cost;
//        }
//        return sum;
//    }

    @Override
    public void submit(List<List<BigInteger>> idNumMap, String key, OrderRemarkDto orderRemarkDto, LocalDateTime time){
        String orderName = orderRemarkDto.getOrderName();
        String orderPhone = orderRemarkDto.getOrderPhone();
        String remark = orderRemarkDto.getRemark();
        for(List<BigInteger> list:idNumMap){
            Order order = new Order();
            order.setOrderTime(time);
            order.setOrderKey(key);
            order.setMealId(list.get(0));
            order.setNum(list.get(1).intValue());
            order.setRemark(remark);
            order.setOrderName(orderName);
            order.setOrderPhone(orderPhone);
            save(order);
        }
    }


    @Override
    public Page<OrderDto> getOrderPage(int page, int pageSize, LocalDate localDate)
    {
        Page<OrderDto> pageInfo = new Page<>(page, pageSize);
        List<Map> mapList = orderMapper.getKeyandmealList(page, pageSize, localDate);
        List<OrderDto> records = new ArrayList<>();
        for(var map: mapList){
            String key = (String) map.get("keyList");
            LocalDateTime orderTime =LocalDateTime.ofInstant(((Timestamp)map.get("order_time")).toInstant(), ZoneId.systemDefault()).withNano(0);
            String remark = (String) map.get("remark");
            List<String> mealIdList = Arrays.asList(((String)map.get("mealIdList")).split(","));
            List<String> numList = Arrays.asList(((String)map.get("numList")).split(","));
            List<MealDto> mealList = new ArrayList<>();
            for(int i = 0; i < mealIdList.size(); i++){
                MealDto mealDto = new MealDto(mealMapper.selectById(mealIdList.get(i)), Integer.parseInt(numList.get(i)));
//                Meal meal = mealMapper.selectById(mealIdList.get(i));
//                mealList.set(0, meal);
//                mealList.set(1, Integer.valueOf(numList.get(i)));
                mealList.add(mealDto);
            }
            OrderDto orderDto = new OrderDto(mealList);
            orderDto.setOrderKey(key);
            orderDto.setRemark(remark);
            orderDto.setOrderTime(orderTime);
            records.add(orderDto);
        }
        pageInfo.setRecords(records);
        pageInfo.setTotal(mapList.size());
        return pageInfo;
    }
}
