package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.User;

/*
 * User Repository 接口
 *
 * */
public interface UserRepository {
    /*
     * 创建或修改
     * */
    User saveOrUpdataUser(User user);

    /*
     * 删除
     * */
    void deleteUser(Long id);

    /*
     * 查询单个
     * */
    User getUserById(Long id);

    /*
     * 获取用户列表
     * */
    List<User> listUser();
}
