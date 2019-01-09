package com.education.mapper;

import com.education.domain.Menu;
import com.education.domain.Resources;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 菜单
 */
@Mapper
public interface MenuMapper {

    //查询所有菜单
    @Select("select count(*) from menu")
    int getMenuAll();

    //查寻根节点
    @Select("select * from menu m where m.parent_id = 0 ")
    Menu getMenu2();

    //根据id获取根节点对象
    @Select("select * from menu where id =#{id}")
    Menu getMenu1(String id);

    //查询id下的所有子节点
    @Select("select * from menu m where m.parent_id = #{id}")
    List<Menu> getMenu(String id);

    //添加菜单
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
    @Insert("insert into menu(id,code,name,icon,url,parent_id,type,`order`,status,description,created_by,created_on,updated_by,is_leaf,default_menus)values" +
            "(#{id},#{code},#{name},#{icon},#{url},#{parentid},#{type},#{order},#{status},#{description},#{createdby},#{createdon},#{updatedby},#{isleaf},#{defaultmenus})")//当使用的字段名、表名与MySQL关键字冲突时，如果不加反引号``，无法执行成功。
    void addMenu(Menu menu);

    //修改菜单
    @Update("update menu set code=#{code},name=#{name},icon=#{icon},url=#{url},parent_id=#{parentid},type=#{type},`order`=#{order},status=#{status},description=#{description}," +
            "created_by=#{createdby},created_on=#{createdon},updated_by=#{updatedby},updated_on=#{updatedon},is_leaf=#{isleaf},default_menus=#{defaultmenus} where id=#{id}")
    void upDateMenu(Menu menu);

    //删除菜单
    @Delete("delete from menu where id=#{id}")
    void deleteMenu(@Param("id") String id);

    //给菜单赋予资源
    //先查询是否已经赋值过
    @Select("select count(*) from menu_resources where menu1_id=#{menuId} and resources_id=#{resourcesId}")
    int selectMenuByIdResourcesById(@Param("menuId")String id,@Param("resourcesId") String resourcesId);

    //赋资源
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Insert("insert into menu_resources(id,menu1_id,resources_id) values (#{id},#{menuId},#{resourcesId})")
    int addMenuResources(@Param("menuId")String id,@Param("resourcesId") String resourcesId);

    //根据菜单id查询对应的资源
    @Select("select * from resources r " +
            "left join menu_resources mr on mr.resources_id = r.id" +
            " where mr.menu1_id = #{id}")
    List<Resources> getMenuById(String id);

}
