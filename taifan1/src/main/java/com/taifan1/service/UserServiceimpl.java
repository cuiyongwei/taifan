package com.taifan1.service;

import com.taifan1.domain.*;
import com.taifan1.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserServiceimpl implements UserService {
    @Resource
    private UserMapper userMapper;
    //查询所有用户
    @Override
    public List<User> getall() {
        return userMapper.dou();
    }
    //添加用户
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
    //修改用户
    @Override
    public void upter(User user) {
        userMapper.upter(user);
    }
    //删除用户
    @Override
    public void dele(Integer id) {
        userMapper.dele(id);
    }
    //添加菜单
    @Override
    public int inadd(menu menu) {
        return userMapper.inadd(menu);
    }
    //查询所有的菜单
    @Override
    public List<menu> quan() {
        return userMapper.quan();
    }
    //修改菜单
    @Override
    public void upde(menu menu) {
        userMapper.upde(menu);
    }
    //删除菜单
    @Override
    public void dete(Integer id) {
        userMapper.dete(id);
    }
    //给用户赋予菜单
    @Override
    public int cha1(Integer id, Integer menu_id) {
        return userMapper.cha1(id,menu_id);
    }
    @Override
    public int setaUse(Integer id, Integer menu_id) {
        return userMapper.setaUse(id,menu_id);
    }
    //传用户id 查询出菜单
    @Override
    public List<menu> getall(int id) {
        return userMapper.find(id);
    }
    //添加功能
    @Override
    public int opadd(operation operation) {
        return userMapper.opadd(operation);
    }
    //功能查询
    @Override
    public List<operation> sect() {
        return userMapper.sect();
    }
    //功能删除
    @Override
    public void delte(Integer id) {
        userMapper.delte(id);
    }

    //赋予菜单功能
    @Override
    public int setss(Integer id, Integer menu_id) {
        return userMapper.setss(id,menu_id);
    }
    //根据菜单id查询出所对应的功能
    @Override
    public List<operation> getAllid(int id) {
        //return  userMapper.findCarByUserId(id);
        return  userMapper.findid(id);
    }
    //根据用户id直接查处功能
    @Override
    public List<operation> All(int id) {
        return userMapper.findaa(id);
    }



}
