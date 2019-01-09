package com.education.service;

import com.education.domain.Menu;
import com.education.domain.Role;
import com.education.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    //获取所有用户
    List<User> getUser();

    //添加用户
    int addUser(User user);

    //修改用户
    void upDateUser(User user);

    //删除用户
    void deleteUser(String id);

    //给用户赋予角色
    //先查询是不是已经赋予过了
    int selectUserByIdRoleById(String id,String roleId);
    //赋角色
    int addUserRole(String id,String roleId);

    //根据用户id查询对应的角色
    List<Role> getUserById(String id);
    //根据角色id查询出所对应的菜单
    List<Role> getRoleById(List<Role> roleList1);

    //根据角色查询出菜单和资源
    List<Role> getRoleMenuResourcesUserById(List<Role> roleList1);




}
