package com.taifan1.mapper;

import com.taifan1.domain.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 多
 */
@Mapper
public interface UserMapper {

    //查询出所有用户
    @Select("select * from user")
    List<User> getUser();

    //添加用户
    @Insert("insert into user(id,name,password)values(#{id},#{name},#{password})")
    int addUser(User user);

    //修改用户
    @Update("update User set name=#{name},password=#{password} where id=#{id}")
    void updateUser(User user);

    //删除用户
    @Delete("delete from user where id=#{id}")
    void deleteUser(@Param("id") Integer id);

    //添加菜单
    @Insert("insert into menu(id,category)values(#{id},#{category})")
    int addmenu(menu menu);

    //查询出所有菜单
    @Select("select * from menu")
    List<menu> getmenu();

    //修改菜单
    @Update("update menu set category=#{category} where id=#{id}")
    void updatemenu(menu menu);

    //删除菜单
    @Delete("delete from menu where id=#{id}")
    void deletemenu(@Param("id") Integer id);

    //给用户赋予菜单，先查询
    @Select("select count(*) from us where menu_id=#{menu_id} and user_id = #{id} ")
    int selectUserByIdMenuId(@Param("id")Integer id,@Param("menu_id") Integer menu_id);

    //给用户赋予菜单
    @Insert("insert into us(menu_id,user_id) values (#{menu_id},#{uid}) ")
    @Results( value={
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="menu_id", property="menu_id", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.VARCHAR),
            //column是数据库表的字段，property是类表中的字段
    })
    int addUserMenu(@Param("uid")Integer id,@Param("menu_id") Integer menu_id);

    //传用户id 查询出菜单
    @Select("SELECT * FROM menu cc " +
            " left join us on us.menu_id = cc.id" +
            " where us.user_id = #{id} ")
    List<menu> getmenuById(Integer id);

    //添加功能
    @Insert("insert into operation(function)values(#{function})")
    int addoperation(operation operation);

    //功能查询
    @Select("select * from operation")
    List<operation> gitopeation();

    //删除功能
    @Delete("delete from operation where id=#{id}")
    void deleteoperation(@Param("id") Integer id);

    //赋予菜单功能
    @Update("update operation set mu_id=#{menu_id} where id=#{operation_id}")
    int addMenuOperation(@Param("operation_id") Integer id,@Param("menu_id") Integer menu_id);

    //根据菜单id查询出所对应的功能
    @Select("SELECT * FROM operation WHERE mu_id = #{id}")
    List<operation> getoperationById(Integer id);

    //整合sql语句 直接根据id查出用户功能

    @Select("select * from operation o left join menu on menu.id = o.id " +
            " left join us on us.menu_id = menu.id " +
            "left join user on us.user_id = user.id where us.user_id=#{id} ")
    List<operation> getOperationUserById(Integer id);



}


