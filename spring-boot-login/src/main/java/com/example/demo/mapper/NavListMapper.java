package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.NavList;

@Mapper
public interface NavListMapper {

    List<NavList> findAll();

    NavList findById(@Param("id") int id);

    int insert(@Param("name") String name, @Param("type") String type);

    void update(NavList navlist);

    void delete(int id);

}
