package com.education.domain;

import java.util.Date;

/**
 * 资源表(应对后端)
 */
public class Resources {
    private String id;
    private String code;
    private String name;
    private String description;
    private String createdby;
    //@ApiModelProperty(value="创建时间",hidden=true)
    private Date createdon;//创建时间
    private String updatedby;
    private Date updatedon;//修改时间

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

    @Override
    public String toString() {
        return "Resources{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdby='" + createdby + '\'' +
                ", createdon=" + createdon +
                ", updatedby='" + updatedby + '\'' +
                ", updatedon=" + updatedon +
                '}';
    }
}
