package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestpay.api.BestpayClient;
import com.bestpay.api.common.EnvEnum;
import com.bestpay.api.exception.BestpayApiException;
import com.bestpay.api.model.request.BestpayRequest;
import com.bestpay.api.model.response.BestpayResponse;
import com.bestpay.api.util.BestpayLogger;
import com.example.config.BizContentConfig;
import com.example.config.CommonParamsConfig;
import com.example.config.PayClientConfig;
import com.example.dto.PayCreateDto;
import com.example.pojo.Order;
import com.example.service.OrderService;
import com.example.utils.payUtil.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

@Api(tags = "支付相关")
@RestController
@Slf4j
@RequestMapping("/pay")
@PropertySource("classpath:config/bestpay.yml")
public class PayController
{
    @Resource
    private PayClientConfig payClientConfig;
    @Resource
    private BizContentConfig bizContentConfig;
    @Resource
    private CommonParamsConfig commonParamsConfig;
    @Resource
    private BestpayClient bestpayClient;
    @Resource
    private OrderService orderService;

    //下单
    @ApiOperation(value = "调用支付接口下单")
    @PostMapping(value = "/create")
    // 传商品数量、商品信息、订单号、下单时间、订单信息、订单金额
    public String tradeCreate(@RequestBody PayCreateDto payCreateDto, HttpServletRequest httpServletRequest){
        //调用统一下单接口
        BestpayRequest request = new BestpayRequest();
        //准备H5网页需要的数据
        request.setEnvEnum(EnvEnum.TEST);

        JSONObject commonParams = new JSONObject();
        commonParams.put("institutionType", commonParamsConfig.getInstitutionType());
        commonParams.put("institutionCode", commonParamsConfig.getInstitutionCode());
        request.setCommonParams(commonParams.toJSONString());
        request.setPath(commonParamsConfig.getComPath());
        request.setVersion(commonParamsConfig.getVersion());

        JSONObject bizContent = new JSONObject();
        bizContent.put("accessCode", bizContentConfig.getAccessCode());
        bizContent.put("ccy", bizContentConfig.getCcy());
        bizContent.put("mediumType", bizContentConfig.getMediumType());
        bizContent.put("merchantNo", bizContentConfig.getMerchantNo());
        bizContent.put("notifyUrl", bizContentConfig.getNotifyUrl());
        bizContent.put("operator", bizContentConfig.getOperator());
        bizContent.put("tradeChannel", bizContentConfig.getTradeChannel());

        JSONObject riskControlInfo = new JSONObject();
        String mcCreateTradeIp = getMcCreateTradeIp(httpServletRequest);
        riskControlInfo.put("service_identify", "10000001");
        riskControlInfo.put("subject", payCreateDto.getSubject());
        riskControlInfo.put("product_type", "1");
        riskControlInfo.put("goods_count", payCreateDto.getGoodsCount());
        riskControlInfo.put("order_ip", mcCreateTradeIp);
        bizContent.put("riskControlInfo", riskControlInfo.toJSONString());
        // goodsInfo 商品信息
        bizContent.put("goodsInfo", payCreateDto.getGoodsInfo());
        // outTradeNo 商户订单号
        bizContent.put("outTradeNo", payCreateDto.getOutTradeNo());
        // requestDate 下单时间
        bizContent.put("requestDate", payCreateDto.getRequestDate());
        // subject 订单信息，在用户账单中展示
        bizContent.put("subject", payCreateDto.getSubject());
        // subject 订单金额，单位分
        bizContent.put("tradeAmt", payCreateDto.getTradeAmt());
        request.setBizContent(bizContent.toJSONString());

        try {
            BestpayResponse response = bestpayClient.execute(request);

            if (response.isSuccess()) {
                BestpayLogger.logBizError("成功处理......response:"+ response);
                BestpayLogger.logBizError("成功处理......getResult:"+ response.getResult());
                return "成功处理......response:"+ response + "\n" + "getResult:"+ response.getResult();
            } else {
                BestpayLogger.logBizError("失败处理......errorCode:"+ response.getErrorCode()+" errorMsg:"+ response.getErrorMsg());
                return "失败处理......errorCode:"+ response.getErrorCode() + "\n" + " errorMsg:"+ response.getErrorMsg();
            }
        } catch (BestpayApiException e) {
            BestpayLogger.logBizError("异常处理......errorCode:"+e.getErrCode()+" errorMsg:"+e.getErrMsg());
            throw new RuntimeException("创建支付交易失败");
        }


//        log.info("异步通知支付结果");
//        log.info("异步通知参数params" + params);
//        String result = "failure";
//        try{
//            //异步通知验签
//
//        }
//        //验签成功
//        log.info("验签成功");

    }

    @ApiOperation(value = "支付通知")
    @GetMapping(value = "/notify")
    public void tradeNotify(@RequestParam Map<String, Object> params) {
        log.info("异步通知支付结果");
        log.info("异步通知参数params----------" + params);
//        boolean res = false;
        String URL_PREFIX = "https://mapi.bestpay.com.cn/mapi/sdkRequest?BESTPAY_MAPI_VERSION="+ commonParamsConfig.getVersion();
        String CHARACTER_CODE = "UTF-8";
        String sign = sign(params);
        params.put("sign", sign);
        try{
            String result = HttpClientUtil.doPost(URL_PREFIX, params, CHARACTER_CODE);
            boolean ver = verify(result);
            if(!ver) {
                //验签失败则记录异常日志，并在response中返回failure.
                log.error("支付成功异步通知验签失败！");
                return;
            }
            // 验签成功，校验参数详情
            // 商户号
            String merchantNo = params.get("merchantNo").toString();
            String merchantNoProperty = bizContentConfig.getMerchantNo();
            if(!merchantNo.equals(merchantNoProperty)){
                log.error("商户号校验失败");
                return;
            }

            // 订单号
            String outTradeNo = params.get("outTradeNo").toString();
            Order order = orderService.getById(outTradeNo);
            if(order == null){
                log.error("订单不存在");
                return ;
            }

            log.info("支付成功异步通知验签成功！");
        }catch (BestpayApiException e) {
            log.error("exception message", e);
        }
    }

    private static final String SIGN_PARAM_NAME = "sign";
    public static final String CHARACTER_CODE = "UTF-8";


    private String sign(Map<String, Object> dataMap) {
        // 1、对报文排序组装
        Map<String, Object> mapContent = AssembleUtil.translateMapData(dataMap);
        String toBeSignedRequest = AssembleUtil.AssembleSignatureData(mapContent);
        System.out.println("toBeSignedRequest：" + toBeSignedRequest);
        // toBeSignedRequest: bizContent commonParams path

        String PRIVATE_KEY_PATH = payClientConfig.getUserCertificateName();
        String PRIVATE_KEY_PWD = payClientConfig.getPasswd();
        String KEY_STORE_TYPE = payClientConfig.getKeyStoreType();
        String ALIAS = payClientConfig.getAlias();
        // 2、获取私钥，私钥加签
        PrivateKey privateKey = KeyCertUtil.getPrivateKey(PRIVATE_KEY_PATH, PRIVATE_KEY_PWD, KEY_STORE_TYPE, ALIAS);
        String sign = SignEncryptUtil.sign(privateKey, toBeSignedRequest);
        System.out.println("sign：" + sign);

        return sign;
    }

    private boolean verify(String responseContent) throws BestpayApiException{
        // 3、返回明文排序
        Map resultMap = JSON.parseObject(responseContent);
        Map<String, Object> mapContent = AssembleUtil.translateMapData(resultMap);
        String responseSign = (String) mapContent.get(SIGN_PARAM_NAME);

        mapContent.remove(SIGN_PARAM_NAME);
        String serContent = AssembleUtil.AssembleSignatureData(mapContent);
        boolean verifyOK = false;
        String PUBLIC_KEY_PATH = payClientConfig.getServiceCertificateName();
        try {
            PublicKey publicKey = KeyCertUtil.getPublicKey(PUBLIC_KEY_PATH);
            verifyOK = SignEncryptUtil.verify(RsaCipher.SignHashAlgoMode.SHA1, serContent.getBytes(CHARACTER_CODE), Base64.decode(responseSign), publicKey);
            if (verifyOK) {
                System.out.println("verify true");
            } else {
                System.out.println("verify false");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("exception message", e);
        }
        return verifyOK;
    }

    // 获取用户IP
    public String getMcCreateTradeIp(HttpServletRequest request) {
        String mc_create_trade_ip = "/";
        if(!request.getRemoteAddr().equals("")){
            mc_create_trade_ip = request.getRemoteAddr();
        };
        return mc_create_trade_ip;
    }
}
