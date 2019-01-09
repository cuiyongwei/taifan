package com.education.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 角色表
 */
public class Role {
    private String id;
    private String name;
    private String code;

    //角色和菜单是一对多的关系
    @ApiModelProperty(value="menua菜单集合",hidden=true)
    private List<Menu> menus;//在一的一方引入多的一方的集合
    @ApiModelProperty(value="Resources资源集合",hidden=true)
    private List<Resources> resources;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Resources> getResources() {
        return resources;
    }

    public void setResources(List<Resources> resources) {
        this.resources = resources;
    }
}
