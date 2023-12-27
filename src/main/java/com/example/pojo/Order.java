package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;



@TableName(value ="orderlist")
@Data
public class Order implements Serializable {

    @TableId(type = IdType.AUTO)
    private BigInteger orderId;
    private BigInteger mealId;

    //订单标识
    private String orderKey;
    //点单时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    private String remark;
    private Integer num;
    private String orderName;
    private String orderPhone;
    /* 0：新建未付款
    * 1：已付款
    * 2：已撤销
    * 3：已退款     */
    private Integer orderStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
