package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.pojo.Meal;
import com.example.pojo.Order;
import com.example.service.MealService;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private MealService mealService;
    @Resource
    private OrderService orderService;

    @GetMapping("/mealMenu")
    public R<Page<Meal>> getMealPage(int page, int pageSize){
        return R.success(orderService.getMealPage((page-1)*pageSize, pageSize));
    }


    //菜品编辑
    @PostMapping
    public R<String> addDish(@RequestBody Meal meal){
        mealService.addMeal(meal);
        return R.success("新增成功");
    }

    @DeleteMapping
    public R<String> deleteDish(@RequestBody Meal meal){
        mealService.delMeal(meal);
        return R.success("修改成功");
    }

    @PutMapping
    public R<String> editDish(@RequestBody Meal meal){
        mealService.editMeal(meal);
        return R.success("修改成功");
    }

//    @GetMapping("/orderMenu")
//    public R<Page<Order>> getOrderPage(int page, int pageSize){
//        return R.success(orderService.getMealPage((page-1)*pageSize, pageSize));
//    }
}
