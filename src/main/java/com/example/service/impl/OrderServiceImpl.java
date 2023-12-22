package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bestpay.api.DefaultBestpayClient;
import com.bestpay.api.common.EnvEnum;
import com.bestpay.api.exception.BestpayApiException;
import com.bestpay.api.model.request.BestpayRequest;
import com.bestpay.api.model.response.BestpayResponse;
import com.bestpay.api.util.BestpayLogger;
import com.example.dto.MealDto;
import com.example.dto.OrderDto;
import com.example.dto.OrderRemarkDto;
import com.example.mapper.MealMapper;
import com.example.mapper.OrderMapper;
import com.example.pojo.Meal;
import com.example.pojo.Order;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MealMapper mealMapper;
//    @Resource
//    private BestpayClient bestpayClient;


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

    @Transactional
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
//            save(order);
            try {
                log.info("===============订单===============");
                bestPayOrder("",
                        "", "", "", "");
//                save(order);
            }catch (Exception e){
                System.out.println(e);
            }

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

    @Override
    public Page<MealDto> getOrderMenuNum(int page, int pageSize, LocalDate localDate)
    {
        Page<MealDto> pageInfo = new Page<>(page, pageSize);
        List<MealDto> orderMenuNum = orderMapper.getOrderMenuNum(page, pageSize, localDate);
        pageInfo.setRecords(orderMenuNum);
        return pageInfo;
    }


    @Transactional
    @Override
    public void bestPayOrder(String userCertificateName, String serviceCertificateName, String passwd, String alias, String keyStoreType)
    {
        userCertificateName = "bestpay.p12";
        serviceCertificateName = "bestpay.cer";
        passwd = "24054008";
        alias = "conname";
        keyStoreType = "PKCS12";
        String institutionCode = "3178033925245778";
        String institutionType = "MERCHANT";

        log.info("调用翼支付接口");

        DefaultBestpayClient defaultBestpayClient = new DefaultBestpayClient
                (userCertificateName, serviceCertificateName, passwd, alias, keyStoreType);
        BestpayRequest request = new BestpayRequest();
        request.setEnvEnum(EnvEnum.TEST);
        request.setCommonParams("{\n" +
                "\t\"institutionType\":\"MERCHANT\",\n" +
                "\t\"institutionCode\":\"3178033925245778\"\n" +
                "}");
        request.setPath("/order/submit");
        request.setVersion("1.0.3");
        request.setBizContent("{\n" +
                "\t\"accessCode\": \"CASHIER\",\n" +
                "\t\"ccy\": \"156\",\n" +
                "\t\"goodsInfo\": \"Mi6\",\n" +
                "\t\"mediumType\": \"WIRELESS\",\n" +
                "\t\"merchantNo\": \"3178033925245778\",\n" +
                "\t\"notifyUrl\": \"/order/submit\",\n" +
                "\t\"operator\": \"3178033925245778\",\n" +
                "\t\"outTradeNo\": \"51028217115687\",\n" +
                "\t\"requestDate\": \"2023-12-07 09:10:15\",\n" +
                "\t\"subject\": \"subject\",\n" +
                "\t\"tradeAmt\": \"99\",\n" +
                "\t\"tradeChannel\": \"H5\"\n" +
                "\t\"timeOut\": \"1200\"\n" +
                "}");

//        request.setCommonParams("{\n" +
//                "\t\"institutionType\":\"MERCHANT\",\n" +
//                "\t\"institutionCode\":\"商户号\"\n" +
//                "}");
//        request.setPath("/order/submit");
//        request.setVersion("1.0.3");
//        request.setBizContent("{\n" +
//                "\t\"accessCode\": \"CASHIER\",\n" +
//                "\t\"ccy\": \"156\",\n" +
//                "\t\"goodsInfo\": \"交易包含的商品信息\",\n" +
//                "\t\"mediumType\": \"WIRELESS\",\n" +
//                "\t\"merchantNo\": \"商户号\",\n" +
//                "\t\"notifyUrl\": \"异步通知地址\",\n" +
//                "\t\"operator\": \"同商户号\",\n" +
//                "\t\"outTradeNo\": \"订单号\",\n" +
//                "\t\"requestDate\": \"订单时间\",\n" +
//                "\t\"storeCode\": \"商户配置渠道号/门店号\",\n" +
//                "\t\"storeName\": \"？？？\",\n" +
//                "\t\"subject\": \"订单信息，在用户账单中展示\",\n" +
//                "\t\"tradeAmt\": \"订单金额，单位分\",\n" +
//                "\t\"tradeChannel\": \"H5\"\n" +
//                "}");

        try {
            BestpayResponse response = defaultBestpayClient.execute(request);

            if (response.isSuccess()) {
                System.out.println("成功处理......response:"+response);
                System.out.println("成功处理......getResult:"+response.getResult());
                BestpayLogger.logBizError("成功处理......response:"+response);
                BestpayLogger.logBizError("成功处理......getResult:"+response.getResult());
            } else {
                System.out.println("失败处理......errorCode:"+response.getErrorCode()+
                        " errorMsg:"+ response.getErrorMsg());
                BestpayLogger.logBizError("失败处理......errorCode:"+response.getErrorCode()+
                        " errorMsg:"+ response.getErrorMsg());
            }
        } catch (BestpayApiException e) {
            BestpayLogger.logBizError("异常处理......errorCode:"+e.getErrCode()+" errorMsg:"+e.getErrMsg());
        }
    }
}
