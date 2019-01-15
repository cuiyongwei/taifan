package com.education.mapper;

import com.education.domain.Menu;
import com.education.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 角色
 */
@Mapper
public interface RoleMapper {

    //添加角色
    /**
     *主键由uuid函数生成
     * keyProperty: 表示将select返回值设置到该类属性中
     * resultType: 返回类型
     * before: 是否在insert之前执行
     * statement: 自定义子查询 把生成的uuid去掉-和空
     * useGeneratedKeys="true"把新增加的主键赋值到自己定义的keyProperty（id）
     */
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    //@Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("Insert into role(id,name,code)values(#{id},#{name},#{code})")
    void addRole(Role role);


    //查询角色
    @Select("select * from role")
    List<Role> getRole();

    //修改角色
    @Update("update role set name=#{name},code=#{code} where id=#{id}")
    void upDateRole(Role role);

    //删除角色
    @Delete("delete from role where id=#{id}")
    void deleteRole(@Param("id") String id);

    //给角色赋予菜单
    //先查询是否已经赋值过
    @Select("select count(*) from role_menu where role1_id=#{roleId} and menu_id=#{menuId}")
    int selectRoleByIdMenuById(@Param("roleId")String id,@Param("menuId") String menuId);

    //赋角色
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Insert("insert into role_menu(id,role1_id,menu_id) values (#{id},#{roleId},#{menuId})")
    int addRoleMenu(@Param("roleId")String id,@Param("menuId") String menuId);

    //根据角色id查询对应的菜单
    @Select("select * from menu m " +
            "left join role_menu rm on rm.menu_id = m.id " +
            "where rm.role1_id = #{id}")
    List<Menu> getRoleById(String id);


}
