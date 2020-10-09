package com.example.annotation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.annotation.domain.User;
import com.example.annotation.domain.VO.UserVO;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> getUsers(UserVO userVO);

    List<User> getUsersAll();
}
