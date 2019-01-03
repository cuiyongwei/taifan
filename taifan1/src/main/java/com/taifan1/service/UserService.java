package com.taifan1.service;


import com.taifan1.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    //查询所有用户
    List<User> getUser();

    //添加用户
    int addUser(User user);

    //修改用户
    void updateUser(User user);

    //删除用户
    void deleteUser(Integer id);

    //添加菜单
    int addmenu(menu menu);

    //查询所有菜单
    List<menu> getmenu();

    //修改菜单
    void updatemenu(menu menu);

    //删除菜单
    void deletemenu(Integer id);

    //给用户赋予菜单 ,先查询
    int selectUserByIdMenuId(Integer id,Integer menu_id);
    int addUserMenu(Integer id,Integer menu_id);

    //传用户id 查询出菜单
    List<menu> getmenuById(int id);

    //添加功能
    int addoperation(operation operation);

    //功能查询
    List<operation> gitopeation();

    //删除功能
    void deleteoperation(Integer id);

    //赋予菜单功能
    int addMenuOperation(Integer id,Integer menu_id);

    //根据菜单id查询出所对应的功能
    List<operation> getoperationById(int id);
    List<menu> traverse(List<menu> booklist);

    //根据用户id直接查出功能
    List<operation> getOperationUserById(int id);

}
