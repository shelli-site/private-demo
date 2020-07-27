package com.example.annotation.controller;

import com.example.annotation.domain.VO.UserVO;
import com.example.annotation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created By shelli On 2020/7/24 11:35
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public List getList(UserVO userVO) {
        return userService.getUsers(userVO);
    }
}
