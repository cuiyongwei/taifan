package com.taifan1.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 菜单 一
 */
public class menu {
    private Integer id;
    private String category;//菜单
    //private List<User> users;//菜单和用户是多对多的关系
    @ApiModelProperty(value="menus数组",hidden=true)
    private List<operation> operation;//菜单和操作表 一对多 在一的一方引入多的一方 引入集合；

    //菜单和用户之间是多对多的关系
   // private List<User> users;

    //菜单和用户之间是多对多的关系
   // private List<User> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<com.taifan1.domain.operation> getOperation() {
        return operation;
    }

    public void setOperation(List<com.taifan1.domain.operation> operation) {
        this.operation = operation;
    }


    @Override
    public String toString() {
        return "menu{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", operation=" + operation +
                '}';
    }
}
