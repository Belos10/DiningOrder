package com.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("支付传输dto 全部传String")
public class PayCreateDto {
    @ApiModelProperty("商品数量")
    private String goodsCount;
    @ApiModelProperty("商品信息")
    private String goodsInfo;
    @ApiModelProperty("商户订单号")
    private String outTradeNo;
    @ApiModelProperty("下单时间")
    private String requestDate;
    @ApiModelProperty("订单信息")
    private String subject;
    @ApiModelProperty("订单金额")
    private String tradeAmt;
}
