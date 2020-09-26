package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.Category;

@Mapper
public interface CategoryMapper {

    Category findByName(@Param("name") String name);

    Category findByUsername(@Param("username") String username);

    Category findById(@Param("id") int id);

    List<Category> findAll();

    int insert(Category category);

    void update(Category category);

    void delete(int id);

}