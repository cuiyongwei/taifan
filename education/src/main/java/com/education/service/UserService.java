package com.education.service;

import com.education.domain.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface UserService {

    //获取所有用户
    List<User> getAllUser();

    //分页获取用户
    List<User> getPaging(int pageNum,int pageSize) throws Exception;

    //分页获取所有用户
    List<User> getUser(String name,int pageNum,int pageSize) throws Exception;

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

    //导入Excel表，解析，字段赋值，存储
    boolean batchImport(String fileName, MultipartFile file) throws Exception;

    //从excel中获取的值添加到数据库
    void addText(List<Text> utils);

    //获取Person数据
    List<Person> getAllPerson();


}
