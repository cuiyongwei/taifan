package com.taifan1.domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * 操作表 多
 */
public class operation {
    @ApiModelProperty(value="id",hidden=true)
    private Integer id;
    private String function;//功能
    @ApiModelProperty(value="menus数组",hidden=true)
    private menu menus;

    public menu getMenus() {
        return menus;
    }

    public void setMenus(menu menus) {
        this.menus = menus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "operation{" +
                "id=" + id +
                ", function='" + function + '\'' +
                ", menus=" + menus +
                '}';
    }
}
