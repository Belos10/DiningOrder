package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dto.MealDto;
import com.example.pojo.Meal;
import com.example.pojo.Order;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order>
{
    List<Meal> getMealPage(int page, int pageSize, LocalDate localDate);
    int getMealPageCount(LocalDate localDate);
    List<Map> getKeyandmealList(int page, int pageSize, LocalDate localDate);
    List<MealDto> getOrderMenuNum(int page, int pageSize, LocalDate localDate);
    List<Order> getOrderByKey(String key);
    int updateStatusByKey(String key, int status);
}
