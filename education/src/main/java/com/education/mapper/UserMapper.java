package com.education.mapper;

import com.education.domain.*;
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




    /*//批量导入
    @Insert({ "<script>",
            "insert into event(id,summary,description,location,start_time,end_time,time_zone,event_type,action_type,notice_time,", "reminders,recurrence,notice_status,event_priority,event_status,share_status,creator_id,calendar_id,share_event_id,origin_event_id,", "finish_message,delete_reason,etag,expire_date,event_shared_date,split_id,create_time,update_time) ",
            "values",
            "<foreach item ='event' collection = 'list' separator = ','>",
            "(#{event.id},#{event.summary},#{event.description},#{event.location},#{event.startTime},#{event.endTime},#{event.timeZone},#{event.eventType},#{event.actionType},#{event.noticeTime},#{event.reminders},",
            "#{event.recurrence},#{event.noticeStatus},#{event.eventPriority},#{event.eventStatus},#{event.shareStatus},#{event.creatorId},#{event.calendarId},#{event.shareEventId},#{event.originEventId},#{event.finishMessage},#{event.deleteReason},", "#{event.etag},#{event.expireDate},#{event.eventSharedDate},#{event.splitId},#{event.createTime},#{event.updateTime})",
            "</foreach>",
            "</script>" })
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int saveAll(@Param("list") List<Event> list);*/


    //导入Excel表，解析，字段赋值，存储

    @Select(" SELECT count(*) FROM user WHERE name=#{name}")
    int selectByName(String name);

    @Insert("insert into util(name,phone,address,enrol_date,des)values(#{name},#{phone},#{address},#{enrolDate},#{des})")
    void addutil(Util userResord);

    @Update("update util set phone=#{phone},address=#{address},enrol_date=#{enrolDate},des=#{des} where name = #{name}")
    Util upDateutil(Util userResord);

    //从excel中获取的值添加到数据库
    @Insert("insert into text(name,phone)values(#{name},#{phone})")
    void addText(Text text);

    //获取Person数据
    //获取所有用户
    @Select("select * from person")
    List<Person> getAllPerson();




}
