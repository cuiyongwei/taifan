package com.taifan.mapper;

import com.taifan.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserMapper {

    //用户查询select <列名> from <表名> [where <查询条件表达试>] [order by <排序的列名>[asc或desc]]
/*    @Select(" select * from user")
    List<User> findAll();*/



//@ResultMap的用法。当这段@Results代码需要在多个方法用到时，为了提高代码复用性，
// 我们可以为这个@Results注解设置id，然后使用@ResultMap注解来复用这段代码。
    //查询所有
    @Select({"select * from user"})
    @Results(id="studentMap", value={
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.INTEGER),
            @Result(column="created_by", property="createdby", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_type", property="type",jdbcType=JdbcType.VARCHAR)
    })
    List<User> initial();

    @Select({"select * from user"})
          @ResultMap(value="studentMap")
    List<User> findAll();




    //用户查询select <列名> from <表名> [where <查询条件表达试>] [order by <排序的列名>[asc或desc]]
/*    @Select(" select * from user Where id =#{id}")
    List<User> findid(@Param("id") Integer id);*/
//根据id查询
    /*    第一种 让字段的别名与实体类的属性名相同，优点：操作简单,容易理解。缺点：当这样的语句出现的次数过多的时候,到时冗余代码增多,这些别名不能重用。
        @Select(" select id,name,password,created_by,user_type type from user Where id =#{id}")
        List<User> findid(@Param("id") Integer id);*/
    //第二中使用注解@Results和@Result这两个注解与XML文件中的标签相对应： @Results对应resultMap @Result对应result
       /* @Select("select * from user where id = #{id}")
        @Results({@Result(property = "type", column = "user_type"),@Result(property = "id", column = "id")})
        List<User> findid(@Param("id") Integer id);*/
    //第三种根据id查询
        @Select({"select *  from user where id = #{id}"})
        @ResultMap(value="studentMap")
        List<User> findid(@Param("id") Integer id);

    //查询所有用户
/*    @Select("select * from user")
    @Results({@Result(property = "type", column = "user_type")})
    List<User> findAll();*/


    //用户删除delete from <表名> [where <删除条件>]例：delete from a where name='开心朋朋'（删除表a中列值为开心朋朋的行）
    @Delete("Delete FROM USER WHERE id = #{id}")
    int removeUser(@Param("id") Integer id);//@Param("id")注解表示给该注解后面的变量去一个参数名称，对应@Delete注解中的#{id}


    //用户添加insert [into] <表名> (列名) values (列值)例：insert into Strdents (姓名,性别,出生日期) values ('开心朋朋','男','1980/6/15')
    @Insert("insert into user(name,password,created_by)values('zhang',456,'liu')")
    int addUser();

    //修改update <表名> set <列名=更新值> [where <更新条件>]例：update tongxunlu set 年龄=18 where 姓名='蓝色小名'
    @Update("UPDATE USER SET name='wasai',password=999,created_by='xi' WHERE id=3")
    void upDate();

    //登录
    @Select("select * from user where name='awei' and password=123")
    List<User> login();
    //登录2
    @Select("select * from user where name=#{name} and password = #{password}")
    List<User> login2(User user);
    //登录2
    @Select("select * from user where name=#{name} and password = #{password}")
    @ResultMap(value="studentMap")
    List<User> login5(@Param("name")String name,@Param("password") Integer password);
    //登录第三种方法
   @Select("select * from user where name = #{name} and password = #{password}")
    User login3(User user);




    /*
     //修改 先找到要修改的数据(根据id查询)
    @Select("SELECT * FROM USER WHERE id = #{id}")
    User toupdate(Integer id);*/

    //修改
    @Update("UPDATE USER SET name=#{name},password=#{password},created_by=#{createdby},user_type=#{type} WHERE id=#{id}")
    void upDate1(User user);


/*
    //用户添加
    @Insert("INSET INTO USER(name,password,created_by) values(#{name},#{password},#{created_by})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int addUser(String name, int password, String created_by);*/

   //用户添加第2中方法
    @Insert("insert into user(id,name,password,created_by,user_type)values(#{id},#{name},#{password},#{createdby},#{type})")
    int addUser1(User user);



}

