package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.OrderRemarkDto;
import com.example.pojo.Meal;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//点餐界面
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController
{

    @Resource
    private OrderService orderService;

    //获取菜品信息
    @GetMapping("/page")
    public R<Page<Meal>> getMealPage(int page, int pageSize, String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        LocalDate date = dateTime.toLocalDate();
        return R.success(orderService.getMealPage((page-1)*pageSize, pageSize, date));
    }

//    //查看购物车 可填写
//    @PostMapping("/shopping")
//    public R<MealDto> Shopping(@RequestBody MealDto mealDto) throws Exception{
//        float price = orderService.getPriceDto(mealDto);
//        mealDto.setPrice(price);
//        return R.success(mealDto);
//    }

    //用户下单 生成Order
    @PostMapping("/submit")
    public R<String> submit(@RequestBody List<List<BigInteger>> idNumMap,
                            @RequestHeader("OrderRemarkDto")OrderRemarkDto orderRemarkDto,
                            @RequestHeader("price") float price) throws Exception
    {
//        String remark1 = URLDecoder.decode(remark, "UTF-8");
        LocalDateTime time = LocalDateTime.now().withNano(0);
        String key = time + "_" + price;
        orderService.submit(idNumMap, key, orderRemarkDto, time);
        return R.success("下单成功");
    }

}
