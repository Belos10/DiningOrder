package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.OrderDto;
import com.example.pojo.Meal;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
    public R<Page<Meal>> getMealPage(int page, int pageSize){
        return R.success(orderService.getMealPage((page-1)*pageSize, pageSize));
    }

    //查看购物车 可填写
    @PostMapping("/shopping")
    public R<OrderDto> Shopping(@RequestBody OrderDto orderDto) throws Exception{
        float price = orderService.getPriceDto(orderDto);
        orderDto.setPrice(price);
        return R.success(orderDto);
    }

    //用户下单 生成Order
    @PostMapping("/submit")
    public R<String> submit(@RequestBody OrderDto orderDto, String remark) throws Exception
    {
        float price = orderService.getPriceDto(orderDto);
        LocalDateTime time = LocalDateTime.now();
        String key = time + "_" + price;
//        log.info("订单数据：{}",orders);
        orderService.submit(orderDto, key, remark, time);
        return R.success("下单成功");
    }

}
