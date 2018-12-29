package com.taifan.service;
import com.taifan.domain.User;
import com.taifan.mapper.UserMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 自动注入UserMapper
     * */
    //用户查询
    @Override
    public List<User> getAll() {
        System.out.println("循环的数组");
        return userMapper.findAll();
    }
    //根据id查询
    @Override
    public List<User> getAllid(int id) {
        return userMapper.findid(id);
    }

    //用户删除
    @Override
    public void delete(int id) {
        userMapper.removeUser(id);
    }
    //修改第二种方法
    @Override
    public void upDate1(User user) {
        userMapper.upDate1(user);
    }
    //用户添加
    @Override
    public int addUser(User user) {
        return userMapper.addUser1(user);
    }
    //传用户名密码直接判断
    @Override
    public List<User> login5(String name, Integer password) {
        return userMapper.login5(name,password);
    }



}
