package com.example.utils.payUtil.Demo;

import com.alibaba.fastjson.JSON;
import com.example.utils.payUtil.*;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuchengcheng
 * @date 2021/9/7
 */
public class CallMapiSDKInterface {
    // 接口版本号
    private static final String INF_VERSION="1.0.3";
    private static final String URL = "/uniformReceipt/proCreateOrder";
    private static final String URL_PREFIX = "https://mapi.bestpay.com.cn/mapi/sdkRequest?BESTPAY_MAPI_VERSION="+INF_VERSION;
    private static final String SIGN_PARAM_NAME = "sign";
    public static final String CHARACTER_CODE = "UTF-8";
    public static final String JSON_REQUEST = "{\n" +
            "\t\"accessCode\": \"CASHIER\",\n" +
            "\t\"ccy\": \"156\",\n" +
            "\t\"goodsInfo\": \"Mi6\",\n" +
            "\t\"mediumType\": \"WIRELESS\",\n" +
            "\t\"merchantNo\": \"3178033925245778\",\n" +
            "\t\"notifyUrl\": \"https://f41c-117-40-199-249.ngrok-free.app/pay/getNo\",\n" +
            "\t\"operator\": \"3178002069171199\",\n" +
            "\t\"outTradeNo\": \"51051961121411114968722\",\n" +
            "\t\"requestDate\": \"2023-12-18 15:59:15\",\n" +
            "\t\"storeCode\": \"xiaomiStore001\",\n" +
            "\t\"storeName\": \"xiaomiStore\",\n" +
            "\t\"subject\": \"subject\",\n" +
            "\t\"tradeAmt\": \"99\",\n" +
            "\t\"tradeChannel\": \"APP\"\n" +
            "}";

    public static final String JSON_RESPONSE = "{\n" +
            "\t\"errorCode\": null,\n" +
            "\t\"errorMsg\": null,\n" +
            "\t\"result\": {\n" +
            "\t\t\"buyerContractNo\": null,\n" +
            "\t\t\"memo\": null,\n" +
            "\t\t\"merchantNo\": \"3178033925245778\",\n" +
            "\t\t\"merchantOrderNo\": \"51051961121411114968722\",\n" +
            "\t\t\"outTradeNo\": \"51051961121411114968722\",\n" +
            "\t\t\"tradeNo\": \"20210910100000210002110248557045\",\n" +
            "\t\t\"tradeResultCode\": null,\n" +
            "\t\t\"tradeResultDesc\": null,\n" +
            "\t\t\"tradeStatus\": \"WAITFORPAY\",\n" +
            "\t\t\"tradeprodNo\": \"2021091017TPPIOP1110001459527428\"\n" +
            "\t},\n" +
            "\t\"sign\": \"eO/gZym6aH1BFLbHNcMj04bt22U358l0rGk9+eO8EVCaR+HovuOJ1gpoyOAgs72Rwfpu4/fNUZ/teIdha25je6FedoDWZRYw4kXgcLUmKMSWbPAuT/CZJ/caX2vAPYWBV0TE2jTpBVVvXJMBkA71b55vRgEi86x1ZwRXq+vwyOo=\",\n" +
            "\t\"success\": true\n" +
            "}";

    // 商户私钥证书
    private static final String PRIVATE_KEY_PATH = "bestpay.p12";
    // 私钥密码
    private static final String PRIVATE_KEY_PWD = "24054008";
    private static final String KEY_STORE_TYPE = "PKCS12";
    private static final String ALIAS = "conname";
    // 公钥路径
    private static final String PUBLIC_KEY_PATH = "bestpay.cer";

    public static void main(String[] args) {
        CallMapiSDKInterface callInterface = new CallMapiSDKInterface();
        callInterface.call();
    }

    /**
     * 一、模拟请求报文
     * 二、调用接口
     * 三、验签
     */
    private void call() {
        Map dataMap = getCallRequestParam();
        String result = HttpClientUtil.doPost(URL_PREFIX, dataMap, CHARACTER_CODE);
        verify(result);
    }

    // URL commonParams bizContent
    private Map getCallRequestParam() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("path", URL);
        dataMap.put("commonParams",
                "{\n\t\"institutionType\":\"MERCHANT\",\n\t\"institutionCode\":\"3178033925245778\"\n}");
        dataMap.put("bizContent", JSON_REQUEST);

        //签名的参数包含path、commonParams、bizContent
        String sign = sign(dataMap);

        dataMap.put("sign", sign);
        return dataMap;
    }

    private String sign(Map<String, Object> dataMap) {
        // 1、对报文排序组装
        Map<String, Object> mapContent = AssembleUtil.translateMapData(dataMap);
        String toBeSignedRequest = AssembleUtil.AssembleSignatureData(mapContent);
        System.out.println("toBeSignedRequest：" + toBeSignedRequest);
        // toBeSignedRequest: bizContent commonParams path

        // 2、获取私钥，私钥加签
        PrivateKey privateKey = KeyCertUtil.getPrivateKey(PRIVATE_KEY_PATH, PRIVATE_KEY_PWD, KEY_STORE_TYPE, ALIAS);
        String sign = SignEncryptUtil.sign(privateKey, toBeSignedRequest);
        System.out.println("sign：" + sign);

        return sign;
    }

    private boolean verify(String responseContent) {
        // 3、返回明文排序
        Map resultMap = JSON.parseObject(responseContent);
        Map<String, Object> mapContent = AssembleUtil.translateMapData(resultMap);
        String responseSign = (String) mapContent.get(SIGN_PARAM_NAME);

        mapContent.remove(SIGN_PARAM_NAME);
        String serContent = AssembleUtil.AssembleSignatureData(mapContent);
        boolean verifyOK = false;
        try {
            PublicKey publicKey = KeyCertUtil.getPublicKey(PUBLIC_KEY_PATH);
            verifyOK = SignEncryptUtil.verify(RsaCipher.SignHashAlgoMode.SHA1, serContent.getBytes(CHARACTER_CODE), Base64.decode(responseSign), publicKey);
            if (verifyOK) {
                System.out.println("verify true");
            } else {
                System.out.println("verify false");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return verifyOK;
    }

}
