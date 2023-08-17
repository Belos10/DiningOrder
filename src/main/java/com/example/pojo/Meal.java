package com.example.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;


@TableName(value = "meal")
@Data
public class Meal implements Serializable {
    //菜品id
    @TableId(type = IdType.AUTO)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate mealTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
