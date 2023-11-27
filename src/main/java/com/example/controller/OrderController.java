package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.bestpay.api.BestpayClient;
//import com.bestpay.api.exception.BestpayApiException;
//import com.bestpay.api.model.response.BestpayResponse;
//import com.bestpay.api.util.SignatureUtil;
import com.example.common.R;
import com.example.dto.OrderRemarkDto;
import com.example.pojo.Meal;
import com.example.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.Signature;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

//点餐界面
@RestController
@Slf4j
@RequestMapping("/order")
@Api("点餐界面")
public class OrderController
{

    @Resource
    private OrderService orderService;

    //获取菜品信息
    @GetMapping("/page")
    @ApiOperation("获取菜品信息")
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
    // idNumMap [[0,1],[0,1]] 0号位为mealId 1号位为数量
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public R<String> submit(@RequestBody List<List<BigInteger>> idNumMap,
                            @RequestHeader("OrderRemarkDto")OrderRemarkDto orderRemarkDto,
                            @RequestHeader("price") float price)
    {
//        String remark1 = URLDecoder.decode(remark, "UTF-8");
        LocalDateTime time = LocalDateTime.now().withNano(0);
        String key = time + "_" + price;
        log.info("TEST===============下单");
        OrderRemarkDto orderRemarkDto1 = new OrderRemarkDto("aa1",
                "123456", "ab1");
        orderService.submit(idNumMap, key, orderRemarkDto1, time);
        return R.success("下单成功");
    }

//    @PostMapping("/notify")
//    public R<String> tradeNotify(@RequestParam Map<String, String> map){
//        log.info("翼支付通知参数=============" + map);
//        //验签
//        //
//        SignatureUtil.verify(Signature)
//
//
//        public static boolean verify(RsaCipher.SignHashAlgoMode algorithm, byte[] data, byte[] sign, PublicKey publicKey) {
//            if (data == null || sign == null || publicKey == null) {
//                return false;
//            }
//            try {
//                Signature signature = Signature.getInstance(algorithm.getCode(), getInstanceProvider());
//                signature.initVerify(publicKey);
//                signature.update(data);
//                return signature.verify(sign);
//            } catch (Exception e) {
//                return false;
//            }
//        }
//    }

}
