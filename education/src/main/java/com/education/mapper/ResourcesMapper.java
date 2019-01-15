package com.education.mapper;

import com.education.domain.Resources;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 资源表
 */
@Mapper
public interface ResourcesMapper {

    //查询所有资源
    @Select("select * from resources")
    List<Resources> getResources();

    //添加资源
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
    @Insert("Insert into resources(id,code,name,description,created_by,created_on,updated_by)values" +
            "(#{id},#{code},#{name},#{description},#{createdby},#{createdon},#{updatedby})")
    void addResources(Resources resources);

    //修改资源
    @Update("update Resources set code=#{code},name=#{name},description=#{description},created_by=#{createdby},created_on=#{createdon},updated_by=#{updatedby},updated_on=#{updatedon} where id=#{id}")
    void upDateResources(Resources resources);

    //删除资源
    @Delete("delete from Resources where id=#{id}")
    void deleteResources(@Param("id") String id);

}
