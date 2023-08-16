package com.example.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> SQLExceptionHandler(SQLIntegrityConstraintViolationException e){
        log.info("发生错误:"+ e.getMessage());
        if(e.getMessage().contains("Duplicate entry")){
            String[] strs = e.getMessage().split(" ");
            return R.error(strs[2]+"已存在");
        }
        return R.error("未知错误");
    }
}
