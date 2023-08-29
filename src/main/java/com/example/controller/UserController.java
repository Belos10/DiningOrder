package com.example.controller;

import com.example.common.R;
import com.example.common.ThreadLocalUtil;
import com.example.pojo.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

//登录界面
@RequestMapping("/user")
@Slf4j
@RestController
//@CrossOrigin(maxAge=3600)
public class UserController
{
    @Resource
    private UserService userService;

    //登录管理员账户
    @PostMapping("/login")
    public R<User> loginApi(@RequestBody User user,
                            HttpSession session) {
        R<User> r = userService.loginService(user);
        if (r.getCode() == 0)
            return r;
        session.setAttribute("loginUser", r.getData().getId());
        return r;
    }




    //登出
    @PostMapping("/loginout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("loginUser");
        ThreadLocalUtil.remove();
        return R.success("退出成功");
    }

    //修改密码
    @PostMapping("/changepwd")
    public R<String> changePassword(@RequestBody User user,
                                    @RequestHeader("newPwd") String newPwd){
        return userService.editUser(user, newPwd);
    }
}
