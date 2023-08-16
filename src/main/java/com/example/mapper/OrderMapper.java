package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.Meal;
import com.example.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order>
{
    List<Meal> getMealPage(int page, int pageSize);
    int getMealPageCount();
//    void addOrder(String key, BigInteger mealId, String remark, LocalDateTime time);
}
