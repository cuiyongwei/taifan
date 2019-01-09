package com.education.service;

import com.education.domain.Menu;
import com.education.domain.Resources;
import com.education.domain.Role;
import com.education.mapper.MenuMapper;
import com.education.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceimpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuMapper menuMapper;

    //添加角色
    @Override
    public void addRole(Role role) {
        roleMapper.addRole(role);
    }

    //获取所有角色
    @Override
    public List<Role> getRole() {
        return roleMapper.getRole();
    }

    //修改角色
    @Override
    public void upDateRole(Role role) {
        roleMapper.upDateRole(role);
    }

    //删除角色
    @Override
    public void deleteRole(String id) {
        roleMapper.deleteRole(id);
    }

    //给角色赋予菜单
    //先查询是否已经赋值过
    @Override
    public int selectRoleByIdMenuById(String id, String menuId) {
        return roleMapper.selectRoleByIdMenuById(id,menuId);
    }

    //赋菜单
    @Override
    public int addRoleMenu(String id, String menuId) {
        return roleMapper.addRoleMenu(id,menuId);
    }

    //根据角色id查询对应的菜单
    @Override
    public List<Menu> getRoleById(String id) {
        return roleMapper.getRoleById(id);
    }

    //根据获取到的菜单查询出资源
    @Override
    public List<Menu> getMenuById(List<Menu> menuList1) {
            List<Menu> maplist = new ArrayList<>();
            for(int i=0;i<menuList1.size();i++){
                //把遍历出的菜单数据放资源对像中
                Menu menu1 = menuList1.get(i);
                String aa = menu1.getId();
                //传菜单id查询出资源
                List<Resources> resources1 = menuMapper.getMenuById(aa);
                menu1.setResources(resources1); //Role 里面得有 Menu集合,把获取到的菜单集合set到角色对象里面
                maplist.add(menu1);}
            return maplist;
    }

}
