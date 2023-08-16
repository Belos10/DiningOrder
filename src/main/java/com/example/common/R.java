package com.example.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> implements Serializable
{
    // 表示成功or失败 1or0
    private int code;
    // 失败时返回错误信息
    private String msg;
    // 成功时返回数据
    private T data;

    private Map<String, Object> map = new HashMap<>();

    public static <T> R<T> success(T data){
        R<T> r = new R<>();
        r.data = data;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg){
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value){
        this.map.put(key,value);
        return this;
    }
}
