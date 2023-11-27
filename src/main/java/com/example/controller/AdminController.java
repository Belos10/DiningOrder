package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.MealDto;
import com.example.dto.OrderDto;
import com.example.pojo.Meal;
import com.example.pojo.Order;
import com.example.service.MealService;
import com.example.service.OrderService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@Slf4j
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8080", maxAge=3600)
@Api("管理员权限")
public class AdminController
{
    @Resource
    private MealService mealService;
    @Resource
    private OrderService orderService;

    @ApiOperation("获取菜品分页")
    @GetMapping("/mealMenu")
    public R<Page<Meal>> getMealPage(int page, int pageSize, String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        LocalDate date = dateTime.toLocalDate();
        return R.success(orderService.getMealPage((page-1)*pageSize, pageSize, date));
    }

    //菜品编辑
    @ApiOperation("增加菜品")
    @PostMapping("/addMeal")
    public R<String> addDish(@RequestBody Meal meal, HttpServletResponse response){
        mealService.addMeal(meal);
        return R.success("新增成功");
    }

    //删除菜品
    @ApiOperation("删除菜品")
    @DeleteMapping("/deleteMeal")
    public R<String> deleteDish(@RequestBody Meal meal){
        mealService.delMeal(meal);
        return R.success("修改成功");
    }

    //编辑菜品
    @ApiOperation("编辑菜品")
    @PutMapping
    public R<String> editDish(@RequestBody Meal meal){
        mealService.editMeal(meal);
        return R.success("修改成功");
    }

    //查看订单 可选时间
    @ApiOperation("查看订单 可选时间")
    @GetMapping("/orderMenu")
    public R<Page<OrderDto>> getOrderPage(int page, int pageSize, String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        LocalDate date = dateTime.toLocalDate();
        return R.success(orderService.getOrderPage((page-1)*pageSize, pageSize, date));
    }

    //返回某天订单的所有菜品与数量
    @ApiOperation("返回某天订单的所有菜品与数量")
    @GetMapping("/OrderMenuNum")
    public R<Page<MealDto>> getOrderMenuNum(int page, int pageSize, String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        LocalDate date = dateTime.toLocalDate();
        return R.success(orderService.getOrderMenuNum((page-1)*pageSize, pageSize, date));
    }

}
