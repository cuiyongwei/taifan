package com.education.domain;

import java.io.Serializable;
import java.util.Date;

public class Util implements Serializable {
    private Integer id;

    private String name;

    private Integer phone;

    private String address;

    private Date enrolDate;

    private String des;

    private static final long serialVersionUID = 1L;

    public Util() {
        super();
    }

    public Util(Integer id, String name, Integer phone, String address, Date enrolDate, String des) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.enrolDate = enrolDate;
        this.des = des;
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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getEnrolDate() {
        return enrolDate;
    }

    public void setEnrolDate(Date enrolDate) {
        this.enrolDate = enrolDate;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Util other = (Util) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getEnrolDate() == null ? other.getEnrolDate() == null : this.getEnrolDate().equals(other.getEnrolDate()))
                && (this.getDes() == null ? other.getDes() == null : this.getDes().equals(other.getDes()));

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getEnrolDate() == null) ? 0 : getEnrolDate().hashCode());
        result = prime * result + ((getDes() == null) ? 0 : getDes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Util{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", enrolDate=" + enrolDate +
                ", des='" + des + '\'' +
                '}';
    }
}
