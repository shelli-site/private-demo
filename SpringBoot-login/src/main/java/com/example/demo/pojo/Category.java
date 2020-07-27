package com.example.demo.pojo;

public class Category {

    private int id;
    private String username;
    private String password;
    private String name;
    private int age;

    public Category(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Category(Integer id, String username, String password, String name, Integer age) {
        this.id = id.intValue();
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age.intValue();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {

        return id + " User " + username + " is " + name + " password: " + password;
    }

}