package com.taifan1.service;

import com.taifan1.domain.*;
import com.taifan1.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceimpl implements UserService {
    @Resource
    private UserMapper userMapper;

    //查询所有用户
    @Override
    public List<User> getUser() {
        return userMapper.getUser();
    }

    //添加用户
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    //修改用户
    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    //删除用户
    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    //添加菜单
    @Override
    public int addmenu(menu menu) {
        return userMapper.addmenu(menu);
    }

    //查询所有的菜单
    @Override
    public List<menu> getmenu() {
        return userMapper.getmenu();
    }

    //修改菜单
    @Override
    public void updatemenu(menu menu) {
        userMapper.updatemenu(menu);
    }

    //删除菜单
    @Override
    public void deletemenu(Integer id) {
        userMapper.deletemenu(id);
    }

    //给用户赋予菜单
    @Override
    public int selectUserByIdMenuId(Integer id, Integer menu_id) {
        return userMapper.selectUserByIdMenuId(id,menu_id);
    }

    @Override
    public int addUserMenu(Integer id, Integer menu_id) {
        return userMapper.addUserMenu(id,menu_id);
    }

    //传用户id 查询出菜单
    @Override
    public List<menu> getmenuById(int id) {
        return userMapper.getmenuById(id);
    }

    //添加功能
    @Override
    public int addoperation(operation operation) {
        return userMapper.addoperation(operation);
    }

    //功能查询
    @Override
    public List<operation> gitopeation() {
        return userMapper.gitopeation();
    }

    //功能删除
    @Override
    public void deleteoperation(Integer id) {
        userMapper.deleteoperation(id);
    }

    //赋予菜单功能
    @Override
    public int addMenuOperation(Integer id, Integer menu_id) {
        return userMapper.addMenuOperation(id,menu_id);
    }

    //根据菜单id查询出所对应的功能
    @Override
    public List<operation> getoperationById(int id) {
        //return  userMapper.findCarByUserId(id);
        return  userMapper.getoperationById(id);
    }

    @Override
    public List<menu> traverse(List<menu> booklist) {
        List<menu> maplist = new ArrayList<>();
        for(int i=0;i<booklist.size();i++){
            //把遍历出的菜单数据放对像中
            menu menu1 = booklist.get(i);
            Integer aa = menu1.getId();
            //传菜单id查询出功能
            List<operation> book_list1= userMapper.getoperationById(aa);
            menu1.setOperation(book_list1);
            maplist.add(menu1);}
        return maplist;
    }

    //根据用户id直接查处功能
    @Override
    public List<operation> getOperationUserById(int id) {
        return userMapper.getOperationUserById(id);
    }



}
