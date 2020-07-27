package com.example.demo.pojo;

public class NavList {
    private int id;
    private String name;
    private String type;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {

        return id + "  Name: " + name + "  Type: " + type;
    }

}
