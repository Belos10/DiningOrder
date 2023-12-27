package com.example;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.bestpay.api.BestpayClient;
import com.bestpay.api.util.BestpayLogger;
import com.example.config.BizContentConfig;
import com.example.config.CommonParamsConfig;
import com.example.config.PayClientConfig;
import com.example.controller.PayController;
import com.example.mapper.MealMapper;
import com.example.mapper.OrderMapper;
import com.example.service.MealService;
import com.example.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import org.thlws.payment.BestPayClient;
import org.thlws.payment.bestpay.entity.request.BarcodePayRequest;
import org.thlws.payment.bestpay.entity.request.OrderRefundRequest;
import org.thlws.payment.bestpay.entity.request.OrderReverseRequest;
import org.thlws.payment.bestpay.entity.request.QueryOrderRequest;
import org.thlws.payment.bestpay.entity.response.OrderRefundResponse;
import org.thlws.payment.bestpay.entity.response.OrderResultResponse;
import org.thlws.payment.bestpay.entity.response.OrderReverseResponse;

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

    private static final Log log = LogFactory.get();

    /**商户数据 KEY*/
    private final  String key = "E20356A94DA4E83810993FD14C10657F3120F1EDA1C43BF7";

    /**商户退款密码(又称 交易key)*/
    private final  String merchantPwd = "946476";

    /**商户号*/
    private final  String merchantId = "02450107030126669";

    @Resource
    private PayClientConfig payClientConfig;
    @Resource
    private BizContentConfig bizContentConfig;
    @Resource
    private CommonParamsConfig commonParamsConfig;

    @Resource
    private Environment environment;
    @Resource
    private PayController payController;

    @Test
    public void test(){

    }

    @Test
    public void test1(){
        System.out.println("========");
        System.out.println(commonParamsConfig.getComPath());
        System.out.println("========");
        System.out.println(commonParamsConfig.getVersion());
    }

    /**
     * 支付-测试.
     */
    @Test
    public void testPay() {

        try {
            String barcode = "510094008028763507";
            String orderAmt = "1";
            String orderNo = RandomUtil.randomNumbers(16);

            BarcodePayRequest request = new BarcodePayRequest();
            request.setMerchantId(merchantId);
            request.setBarcode("510094008028763507");
            request.setOrderNo( RandomUtil.randomNumbers(16));
            request.setOrderReqNo( RandomUtil.randomNumbers(16));
            request.setOrderDate("20231207090715");
            request.setOrderAmt("1");
            request.setProductAmt("1");
            request.setGoodsName("测试商品");
            request.setStoreId("00001025104487");
            OrderResultResponse response = BestPayClient.barcode(request, key);
            System.out.println("result========="+response.getResult());
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

            System.out.println(response.isPaySuccess());
//            assertTrue(response.isPaySuccess());
        } catch (Exception e) {
            log.error(e);
        }

    }


    /**
     * 查询交易-测试.
     */
    @Test
    public void testQuery() {
        try {
            QueryOrderRequest request = new QueryOrderRequest();
            request.setMerchantId(merchantId);
            request.setOrderNo("5241085967971143");
            request.setOrderReqNo("5241085967971143");
            request.setOrderDate("20171224121212");

            OrderResultResponse response = BestPayClient.query(request, key);
            System.out.println(response.getResult());
            assertNotNull(response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 撤销支付-测试.
     */
    @Test
    public void testReverse() {

        try {
            OrderReverseRequest request = new OrderReverseRequest();
            request.setMerchantId(merchantId);
            request.setMerchantPwd(merchantPwd);
            request.setOldOrderNo("5241085967971143");
            request.setOldOrderReqNo("5241085967971143");
            request.setRefundReqNo(RandomUtil.randomNumbers(16));
            request.setRefundReqDate("20171224");
            request.setTransAmt("1");

            OrderReverseResponse response = BestPayClient.reverse(request, key);
            assertNotNull(response.getResult());
        } catch (Exception e) {
            log.error(e);
        }
    }


    /**
     * 退款-测试.
     */
    @Test
    public void testRefund() {
        try {
            OrderRefundRequest request = new OrderRefundRequest();
            request.setMerchantId("02450107030126669");
            request.setOldOrderNo("5241085967971143");
            request.setOldOrderReqNo("5241085967971143");
            request.setMerchantPwd(merchantPwd);
            request.setRefundReqDate("20171224");
            request.setRefundReqNo(RandomUtil.randomNumbers(16));
            request.setTransAmt("1");

            OrderRefundResponse response = BestPayClient.refund(request, key);
            assertNotNull(response.getResult());
        } catch (Exception e) {
            log.error(e);
        }
    }


    @Test
    public void testOrder(){
        try{
            orderService.bestPayOrder("",
                    "", "", "", "");
        }catch (Exception e) {
            log.error(e);
        }
    }
}
