package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.R;
import com.example.pojo.User;


public interface UserService extends IService<User> {
    R<User> loginService(User user);
    R<String> editUser(User user, String newPwd);

}
