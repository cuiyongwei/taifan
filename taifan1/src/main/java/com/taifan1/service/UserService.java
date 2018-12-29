package com.taifan1.service;


import com.taifan1.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface UserService {
    //查询所有用户
    List<User> getall();
    //添加用户
    int addUser(User user);
    //修改用户
    void upter(User user);
    //删除用户
    void dele(Integer id);
    //添加菜单
    int inadd(menu menu);
    //查询所有菜单
    List<menu> quan();
    //修改菜单
    void upde(menu menu);
    //删除菜单
    void dete(Integer id);
    //给用户赋予菜单 ,先查询
    int cha1(Integer id,Integer menu_id);
    int setaUse(Integer id,Integer menu_id);
    //传用户id 查询出菜单
    List<menu> getall(int id);
    //添加功能
    int opadd(operation operation);
    //功能查询
    List<operation> sect();
    //删除功能
    void delte(Integer id);
    //赋予菜单功能
    int setss(Integer id,Integer menu_id);
    //根据菜单id查询出所对应的功能
    List<operation> getAllid(int id);
    //根据用户id直接查出功能
    List<operation> All(int id);




}
