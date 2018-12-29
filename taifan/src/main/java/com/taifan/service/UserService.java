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
    //修改第二种方法
    public void upDate1(User user);
    //用户添加
    int addUser(User user);
    //传用户名密码直接判断
    List<User> login5(String name,Integer password);
}
