package com.taifan1.domain;


import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 购买者多
 */

public class User {
    private Integer id;			// id
    private String name;	    //用户名
    private Integer password;	// 密码
    //用户和菜单是多对多的关系
    @ApiModelProperty(value="menus数组",hidden=true)
    private List<menu> menus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public List<menu> getMenus() {
        return menus;
    }

    public void setMenus(List<menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", menus=" + menus +
                '}';
    }
}
