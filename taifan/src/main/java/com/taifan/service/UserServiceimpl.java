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
    //用户修改
    @Override
    public void upDate() {
        userMapper.upDate();

    }
    //修改第二种方法
    @Override
    public void upDate1(User user) {
        userMapper.upDate1(user);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser1(user);
    }


    //用户添加


    @Override
    public List<User> login() {
        return userMapper.login();
    }

    @Override
    public List<User> login2(User user) {
        return userMapper.login2(user);
    }

    @Override
    public List<User> login5(String name, Integer password) {
        return userMapper.login5(name,password);
    }


    //判断

    //用户登录3
    @Override
    public User login3(User user) {
        return userMapper.login3(user);
    }

    //用户添加第2中方法
    @Override
    public int addUser1(User user) {
        return userMapper.addUser1(user);
    }
    //登录



/*    //修改用户，先找到要改的数据
    @Override
    public User toUpdate(int id) {
        return userMapper.toupdate(id);
    }*/


    //修改
/*    @Override
    public void update(User user) {
        userMapper.update(user);
    }*/




/*    @Override
    public int addUser(String name, int password, String created_by) {
        System.out.println("----------");
        return userMapper.addUser(name,password,created_by);
    }*/

  /*  @Override
    public int addUser(String[] dataArray) {
        return userMapper.addUser(dataArray);
    }*/

    //添加用户





}
