package com.example.dto;

import lombok.Data;

@Data
public class OrderRemarkDto
{
    private String orderName;
    private String orderPhone;
    private String remark;

    public OrderRemarkDto(String orderName, String orderPhone, String remark)
    {
        this.orderName = orderName;
        this.orderPhone = orderPhone;
        this.remark = remark;
    }
}
