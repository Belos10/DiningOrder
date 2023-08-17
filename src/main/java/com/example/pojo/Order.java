package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;



@TableName(value ="order_detail")
@Data
public class Order implements Serializable {

    @TableId
    private BigInteger orderId;

    private BigInteger mealId;

    //订单标识
    private String key;
    //点单时间
    private LocalDateTime orderTime;

    private String remark;

    private Integer num;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
