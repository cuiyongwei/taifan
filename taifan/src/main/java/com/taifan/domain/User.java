package com.taifan.domain;

public class User{

   // @ApiModelProperty(value="id数组",hidden=true) //隐藏id
    private Integer id;			// id
    private String name;	    // 登录英文名
    private Integer password;	// 密码
    private String createdby;   //登录中文名 需要驼峰转换
    private String type;//

    public User() {
        super();
    }

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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", createdby='" + createdby + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
