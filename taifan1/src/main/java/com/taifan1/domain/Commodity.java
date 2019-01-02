package com.taifan1.domain;

import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * 商品一
 */
public class Commodity {
    private Integer id;
    private String name;
    private Integer price;
    private String remark;
    private List<Commodity> commodity;//商品和购买者 一对多 在一的一方引入多的一方 引入集合；

    //商品和订单之间是多对多的关系，多对多就是多个一对多
    private List<Order> orders;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Commodity> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<Commodity> commodity) {
        this.commodity = commodity;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", remark='" + remark + '\'' +
                ", commodity=" + commodity +
                ", orders=" + orders +
                '}';
    }
}
