package com.education.domain;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单表 后端 菜单和资源是多对多的关系、
 * 菜单是1对应多个资源
 */
public class Menu implements Serializable {
    private String id;
    private String code;
    private String name;
    private String icon;
    private String url;
    private String parentid;//节点
    private String type;
    private int order;
    private String status;
    private String description;
    private String createdby;
    private Date  createdon;//时间
    private String updatedby;
    private Date updatedon;
    private String isleaf;
    private String defaultmenus;

    private List nodes = new ArrayList();

    @ApiModelProperty(value="Resources资源集合",hidden=true)
    private List<Resources> resources;//在一的一方引入多的一方的集合

    public Menu() {

    }

    public List getNodes() {
        return nodes;
    }

    public void setNodes(List nodes) {
        this.nodes = nodes;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public List<Resources> getResources() {
        return resources;
    }

    public void setResources(List<Resources> resources) {
        this.resources = resources;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(Date updatedon) {
        this.updatedon = updatedon;
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
        return "Menu{" +
                "id='" + id + '\'' +
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
                ", createdon=" + createdon +
                ", updatedby='" + updatedby + '\'' +
                ", updatedon=" + updatedon +
                ", isleaf='" + isleaf + '\'' +
                ", defaultmenus='" + defaultmenus + '\'' +
                ", nodes=" + nodes +
                ", resources=" + resources +
                '}';
    }
}
