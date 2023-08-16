package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.R;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public R<User> loginService(User user){
        // 将页面提交密码password进行md5加密
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据页面提交账户account查询数据库
        String account = user.getAccount();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, account).eq(User::getPassword, password);
        User emp = getOne(lambdaQueryWrapper);

        if(emp==null)
            return R.error("登录失败，用户名或密码错误");
        return R.success(emp);
    }

    @Override
    public R<String> editUser(User user, String newPwd){
        if(newPwd == null||newPwd.isEmpty())
            return R.error("未输入新密码");
        //密码错误
        String pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        String account = user.getAccount();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, account).eq(User::getPassword, pwd);
        User emp = getOne(lambdaQueryWrapper);
        if(emp==null)
            return R.error("账户或密码错误");
        newPwd = DigestUtils.md5DigestAsHex(newPwd.getBytes());
        //密码重复
        if(emp.getPassword().equals(newPwd))
            return R.error("新密码不可与原密码重复");
        //成功
        emp.setPassword(newPwd);
        saveOrUpdate(emp);
        return R.success("修改成功");
    }

}




