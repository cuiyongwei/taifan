package com.education.service;

import com.education.domain.Menu;
import com.education.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    //添加角色
    void addRole(Role role);

    //获取所有角色
    List<Role> getRole();

    //修改角色
    void upDateRole(Role role);

    //删除角色
    void deleteRole(String id);

    //给角色赋予菜单
    //先查询是否已经赋值过
    int selectRoleByIdMenuById(String id,String menuId);

    //赋菜单
    int addRoleMenu(String id,String menuId);

    //根据角色id查询对应的菜单
    List<Menu> getRoleById(String id);

    //根据菜单id查询出所对应的资源
    List<Menu> getMenuById(List<Menu> menuList1);

}
