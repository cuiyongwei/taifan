package com.taifan.mapper;

import com.taifan.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserMapper {
//@ResultMap的用法。当这段@Results代码需要在多个方法用到时，为了提高代码复用性，
// 我们可以为这个@Results注解设置id，然后使用@ResultMap注解来复用这段代码。
    //查询所有
    @Select({"select * from user"})
    @Results(id="studentMap", value={
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.INTEGER),
            @Result(column="created_by", property="createdby", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_type", property="type",jdbcType=JdbcType.VARCHAR)//column是数据库表的字段，property是类表中的字段
    })
    List<User> initial();

    @Select({"select * from user"})
          @ResultMap(value="studentMap")
    List<User> findAll();
    //第根据id查询
        @Select({"select *  from user where id = #{id}"})
        @ResultMap(value="studentMap")
        List<User> findid(@Param("id") Integer id);

    //用户删除delete from <表名> [where <删除条件>]例：delete from a where name='开心朋朋'（删除表a中列值为开心朋朋的行）
    @Delete("Delete FROM USER WHERE id = #{id}")
    int removeUser(@Param("id") Integer id);//@Param("id")注解表示给该注解后面的变量去一个参数名称，对应@Delete注解中的#{id}
    //修改
    @Update("UPDATE USER SET name=#{name},password=#{password},created_by=#{createdby},user_type=#{type} WHERE id=#{id}")
    void upDate1(User user);
    //用户添加第2中方法
    @Insert("insert into user(id,name,password,created_by,user_type)values(#{id},#{name},#{password},#{createdby},#{type})")
    int addUser1(User user);


    //传用户名密码直接判断
    @Select("select * from user where name=#{name} and password = #{password}")
    @ResultMap(value="studentMap")
    List<User> login5(@Param("name")String name,@Param("password") Integer password);


}

