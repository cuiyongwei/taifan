package com.taifan1.domain;

import java.util.List;

/**
 * 订单
 */
public class Orde {

    private Integer id;			// id
    private String code;	    //
    private Integer total;	// 密码

    //订单和商品之间是多对多的关系
    private List<Commodity> commodity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Commodity> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<Commodity> commodity) {
        this.commodity = commodity;
    }

    @Override
    public String toString() {
        return "Orde{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", total=" + total +
                ", commodity=" + commodity +
                '}';
    }
}
