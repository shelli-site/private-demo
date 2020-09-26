package com.example.demo.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.mapper.NavListMapper;
import com.example.demo.pojo.NavList;

@Controller
public class UserController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NavListMapper navlistMapper;

    @RequestMapping("/list")
    public String list(ModelMap map) {
        List<NavList> data = navlistMapper.findAll();
        map.addAttribute("list", data);
        return "user";
    }
}
