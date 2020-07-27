package com.example.annotation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.annotation.annotation.utils.QueryHelp;
import com.example.annotation.domain.User;
import com.example.annotation.domain.VO.UserVO;
import com.example.annotation.mapper.UserMapper;
import com.example.annotation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUsers(UserVO userVO) {
        return userMapper.selectList(QueryHelp.createQueryWra(userVO, User.class));
    }
}
