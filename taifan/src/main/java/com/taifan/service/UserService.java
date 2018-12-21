package com.taifan.service;
import com.taifan.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    //用户查询
    List<User> getAll();
    //根据id查询
    List<User> getAllid(int id);

    //用户删除
    public void delete(int id);

    //修改
    public void upDate();
    //修改第二种方法
    public void upDate1(User user);

    //用户添加
    int addUser(User user);

    //登录
    List<User> login();
    List<User> login2(User user);

    List<User> login5(String name,Integer password);

    //用户登录第三种方法
    User login3(User user);

    //用户更改
    //用户更改，先找到
    //public User toUpdate(int id);
    //修改
    //public void update(User user);


    //用户添加第2中方法
    int addUser1(User user);
    //int addUser(String[] dataArray)  ;
    //int addUser(String name,int password,String created_by);

}
