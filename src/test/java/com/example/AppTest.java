package com.example;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.example.mapper.MealMapper;
import com.example.mapper.OrderMapper;
import com.example.pojo.Meal;
import com.example.service.MealService;
import com.example.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Autowired
    private OrderMapper orderMapper;
    @Resource
    private MealService mealService;
    @Resource
    private MealMapper mealMapper;
    @Resource
    private OrderService orderService;




    @Test
    public void test1(){
        orderService.bestPayOrder("", "","",
                "","");
    }
}
