package com.example.demo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.YamlJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.NavListMapper;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.NavList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    NavListMapper navlistMapper;

    @Test
    public void ToList() {

        List<Category> cs = categoryMapper.findAll();
        for (Category cat : cs) {
            System.out.println(cat.toString());
        }
    }

    @Test
    public void NavToList() {
        List<NavList> c = navlistMapper.findAll();
        for (NavList cat : c) {
            System.out.println(cat.toString());
        }

    }

    @Test
    public void Add() throws InvocationTargetException {
        Category ann = new Category("ann", "12345623");

        if (categoryMapper.insert(ann) == 1)
            ToList();
        else
            System.out.println("error!");
    }

    @Test
    public void Updata() {

        Category category = categoryMapper.findByUsername("ann");
        category.setPassword("654123");
        category.setName("anni");
        category.setAge(12);
        categoryMapper.update(category);
        ToList();
    }

    @Test
    public void Delete() {

        Category category = categoryMapper.findByUsername("qw");
        categoryMapper.delete(category.getId());
        ToList();
    }

    @Test
    public void Te() {
        //NavList na=navlistMapper.findById(2);
        //System.out.println(navlistMapper.insert("test1", "type1"));
		/*NavList na=navlistMapper.findById(13);
		na.setType("TestType1");
		navlistMapper.update(na);*/
        navlistMapper.delete(13);
        NavToList();
    }

    @Test
    public void Json() {
        String data = "{\"username\":\"admin\",\"password\":\"123456\"}";
        System.out.println(data);
        YamlJsonParser json = new YamlJsonParser();
        Map<String, Object> parser = json.parseMap(data);

        System.out.println(parser.get("username"));

    }

}
