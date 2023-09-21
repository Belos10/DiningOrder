package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 管理员账户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    //id
    @TableId(type = IdType.AUTO)
    private BigInteger id;
    //账户
    private String account;
    //密码
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}