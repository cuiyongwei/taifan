package com.taifan1.domain;

public class Us {
    private Integer id;
    private Integer menu_id;
    private Integer user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Us{" +
                "id=" + id +
                ", menu_id=" + menu_id +
                ", user_id=" + user_id +
                '}';
    }
}
