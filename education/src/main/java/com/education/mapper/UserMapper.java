package com.education.mapper;

import com.education.domain.Role;
import com.education.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户
 */

@Mapper
public interface UserMapper {

    //获取所有用户
    @Select("select * from user")
    List<User> getAllUser();

    //分页获取所有用户
    @Select("select * from user where name like CONCAT('%',#{name},'%')")
    List<User> getUser(@Param("name") String name);

    //添加用户
    /**
     *
     * keyProperty: 表示将select返回值设置到该类属性中
     * resultType: 返回类型
     * before: 是否在insert之前执行
     * statement: 自定义子查询 把生成的uuid去掉-和空
     */
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    //@Options(keyProperty = "user.id", useGeneratedKeys = true)
    @Insert("Insert into user(id,name,password)values(#{id},#{name},#{password})")
    int addUser(User user);

    //修改用户
    @Update("update user set name=#{name},password=#{password} where id=#{id}")
    void upDateUser(User user);

    //删除用户
    @Delete("delete from user where id=#{id}")
    void deleteUser(@Param("id") String id);

    //给用户赋予角色
    //先查询是否已经赋值过
    @Select("select count(*) from user_role where user_id=#{userId} and role_id=#{roleId}")
    int selectUserByIdRoleById(@Param("userId")String id,@Param("roleId") String roleId);
    //赋角色
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Insert("insert into user_role(id,role_id,user_id) values (#{id},#{roleId},#{userId}) ")
    int addUserRole(@Param("userId")String id,@Param("roleId") String roleId);

    //根据用户id查询对应的角色
    @Select("select * from Role r " +
            "left join user_role ur on ur.role_id = r.id " +
            "where ur.user_id = #{id}")
    List<Role> getUserById(String id);



}
