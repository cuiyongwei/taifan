package com.education.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

/**
 * @Excel的属性
 * name 列名，
 * replace 值得替换 导出是{a_id,b_id} 导入反过来
 * orderNum列的排序,支持name_id
 * type导出类型 1 是文本 2 是图片,3 是函数,10 是数字 默认是文本
 * exportFormat	导出的时间格式,以这个是否为空来判断是否需要格式化日期
 * importFormat 导入的时间格式,以这个是否为空来判断是否需要格式化日期
 * format时间格式,相当于同时设置了exportFormat 和 importFormat
 *databaseFormat="yyyyMMddHHmmss"导出时间设置,如果字段是Date类型则不需要设置 数据库如果是string 类型,这个需要设置这个数据库格式,用以转换时间格式输出
 * isWrap 	是否换行 即支持\n
 */
public class Person {


    @Excel(name = "姓名", orderNum = "0")
    private String name;

    @Excel(name = "性别", replace = {"男_1", "女_2"}, orderNum = "1")
    private String sex;

    @Excel(name = "时间", format = "yyyy-MM-dd HH:mm:SS", orderNum = "2",isWrap=false)
    private Date birthday;

    @Excel(name ="年龄",orderNum = "3")
    private Integer age;

    public Person() {
        super();
    }

    public Person(String name, String sex, Date birthday, Integer age) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}
