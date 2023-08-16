package com.example.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * 菜品管理
 * @TableName Meal
 */
@TableName(value = "meal")
@Data
public class Meal implements Serializable {
    //菜品id
    @TableId
    private BigInteger mealId;
    //菜品名称
    private String name;
    //菜品价格
    private Float cost;
     //介绍
    private String intro;
    //图片
    private String pic;
    //上架时间
    private LocalDateTime mealTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
