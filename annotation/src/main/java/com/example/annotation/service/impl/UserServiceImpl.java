package com.example.annotation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Override
    public List<User> getUsersAll() {
        UserVO userVO = new UserVO();
        userVO.setUseName("一");
        IPage<User> page = userMapper.getAll(userVO, new Page(1L, 10L));
        return page.getRecords();
    }
}
