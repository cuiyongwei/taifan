package com.education.domain;

/**
 * 菜单表 前端
 */
public class MenuAfter {

    private int id;
    private String code;
    private String name;
    private String icon;
    private String url;
    private String parentid;
    private String type;
    private int order;
    private String status;
    private String description;
    private String createdby;
    private String updatedby;
    private String isleaf;
    private String defaultmenus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public String getDefaultmenus() {
        return defaultmenus;
    }

    public void setDefaultmenus(String defaultmenus) {
        this.defaultmenus = defaultmenus;
    }

    @Override
    public String toString() {
        return "MenuAfter{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", parentid='" + parentid + '\'' +
                ", type='" + type + '\'' +
                ", order=" + order +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", createdby='" + createdby + '\'' +
                ", updatedby='" + updatedby + '\'' +
                ", isleaf='" + isleaf + '\'' +
                ", defaultmenus='" + defaultmenus + '\'' +
                '}';
    }
}
