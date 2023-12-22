package com.example.utils.payUtil.Demo;

import com.bestpay.api.DefaultBestpayClient;
import com.bestpay.api.common.EnvEnum;
import com.bestpay.api.exception.BestpayApiException;
import com.bestpay.api.model.request.BestpayRequest;
import com.bestpay.api.model.response.BestpayResponse;
import com.bestpay.api.util.BestpayLogger;

/**
 * @author zhuchengcheng
 * @version Id: DefaultBestpayClient.java, v 0.1 2021/9/10 14:42 zhuchengcheng Exp $$
 */
public class ProCreateOrderDemo {

    public static void main(String[] args){

        String userCertificateName = "bestpay.p12";
        String serviceCertificateName = "bestpay.cer";
        String passwd = "24054008";
        String alias = "conname";
        String keyStoreType = "PKCS12";
        DefaultBestpayClient bestpayClient = new DefaultBestpayClient(userCertificateName, serviceCertificateName, passwd, alias, keyStoreType);
        BestpayRequest request = new BestpayRequest();
        request.setEnvEnum(EnvEnum.PRODUCT);
        request.setCommonParams("{\n" +
                "\t\"institutionType\":\"MERCHANT\",\n" +
                "\t\"institutionCode\":\"3178033925245778\"\n" +
                "}");
        request.setPath("/uniformReceipt/proCreateOrder");
		request.setVersion("1.0.3");
        request.setBizContent("{\n" +
                "\t\"accessCode\": \"CASHIER\",\n" +
                "\t\"ccy\": \"156\",\n" +
                "\t\"goodsInfo\": \"Mi6\",\n" +
                "\t\"mediumType\": \"WIRELESS\",\n" +
                "\t\"merchantNo\": \"3178033925245778\",\n" +
                "\t\"notifyUrl\": \"https://f41c-117-40-199-249.ngrok-free.app/pay/getNo\",\n" +
                "\t\"operator\": \"3178033925245778\",\n" +
                "\t\"outTradeNo\": \"510519614129687\",\n" +
                "\t\"requestDate\": \"2023-12-21 17:11:15\",\n" +
                "\t\"storeCode\": \"xiaomiStore001\",\n" +
                "\t\"storeName\": \"xiaomiStore\",\n" +
                "\t\"subject\": \"subject\",\n" +
                "\t\"tradeAmt\": \"99\",\n" +
                "\t\"tradeChannel\": \"APP\"\n" +
                "}");


        try {
            BestpayResponse response = bestpayClient.execute(request);

            if (response.isSuccess()) {
                BestpayLogger.logBizError("成功处理......response:"+response);
                BestpayLogger.logBizError("成功处理......getResult:"+response.getResult());
            } else {
                BestpayLogger.logBizError("失败处理......errorCode:"+response.getErrorCode()+" errorMsg:"+ response.getErrorMsg());
            }
        } catch (BestpayApiException e) {
            BestpayLogger.logBizError("异常处理......errorCode:"+e.getErrCode()+" errorMsg:"+e.getErrMsg());
        }


    }

}

/*
响应报文示例-下单成功
{
	"errorCode": null,
	"errorMsg": null,
	"result": {
		"buyerContractNo": null,
		"memo": null,
		"merchantNo": "3178033925245778",
		"merchantOrderNo": "5105196112141111496872233",
		"outTradeNo": "5105196112141111496872233",
		"tradeNo": "20210910100000210002110248683601",
		"tradeResultCode": null,
		"tradeResultDesc": null,
		"tradeStatus": "WAITFORPAY",
		"tradeprodNo": "2021091017TPPIOP1110001459598843"
	},
	"sign": "O8qDRI8SFBip0EeknpO9qD73rZ7r8wqS1rohmpw1dx3rElopgQdAz4/JnKAarlNIAysrc2tiO0cu/n7nBB++7mdFj0uBYgXg/xNZKRJvLc5YGF6wUbm/qEftHYdRej/4tE00xAoFSiroqjK4NHM2Ml6k2kNm6QVZvy4rsxbwWFM=",
	"success": true
}

响应报文示例-下单失败
{
	"errorCode": "ORDER_FAIL",
	"errorMsg": "请求时间有效范围(T-1, T+1)",
	"result": null,
	"sign": "EkWdfKj3j5SW1+YZdSCqbtWcKKUad+AT+r2bGuJmvCV9Cod7ozSImRrdaCyCcIXzvh7FGx7wUhawK4ZLTNZl7xcPRaajZriEVyzdexkfGeljGqxaLoDCuk6QxF3gaQ+vf8GCZwGt4dlIdxoQSip5vteO0gmPegG2S9NXLdOtw/Q=",
	"success": false
}
*/

