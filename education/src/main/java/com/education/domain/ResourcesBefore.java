package com.education.domain;

/**
 * 资源表(应对前端)
 */
public class ResourcesBefore {
    private String id;
    private String code;
    private String name;
    private String description;
    private String createdby;
    //@ApiModelProperty(value="创建时间",hidden=true)
   // private Date createdon;//创建时间
    private String updatedby;
   // private Date updatedon;//修改时间


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

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    @Override
    public String toString() {
        return "ResourcesBefore{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdby='" + createdby + '\'' +
                ", updatedby='" + updatedby + '\'' +
                '}';
    }
}
