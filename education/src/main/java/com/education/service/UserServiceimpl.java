package com.education.service;

import com.education.domain.Menu;
import com.education.domain.Resources;
import com.education.domain.Role;
import com.education.domain.User;
import com.education.mapper.MenuMapper;
import com.education.mapper.RoleMapper;
import com.education.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceimpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;

    //查询所有用户
    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    //分页获取所有用户
    @Override
    public List<User> getPaging(int pageNum, int pageSize) throws Exception {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        List<User> paginglist = userMapper.getAllUser();
        return paginglist;
    }

    //查询所有用户
    @Override
    public List<User> getUser(String name, int pageNum, int pageSize) throws Exception {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        List<User> typeList = userMapper.getUser(name);
        return typeList;
    }

    //添加用户
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    //修改用户
    @Override
    public void upDateUser(User user) {
        userMapper.upDateUser(user);
    }

    //删除用户
    @Override
    public void deleteUser(String id) {
        userMapper.deleteUser(id);
    }

    //给用户赋予角色
    //先查询是否已经赋值过
    @Override
    public int selectUserByIdRoleById(String id, String roleId) {
        return userMapper.selectUserByIdRoleById(id, roleId);
    }

    //赋角色
    @Override
    public int addUserRole(String id, String roleId) {
        return userMapper.addUserRole(id, roleId);
    }

    //根据用户id查询对应的角色
    @Override
    public List<Role> getUserById(String id) {
        return userMapper.getUserById(id);
    }

    //根据获取到的角色查询出菜单
    @Override
    public List<Role> getRoleById(List<Role> roleList1) {
        List<Role> maplist = new ArrayList<>();
        for (int i = 0; i < roleList1.size(); i++) {
            //把遍历出的菜单数据放角色对像中
            Role role1 = roleList1.get(i);
            String aa = role1.getId();
            //传角色id查询出菜单
            List<Menu> menulist1 = roleMapper.getRoleById(aa);
            role1.setMenus(menulist1); //Role 里面得有 Menu集合,把获取到的菜单集合set到角色对象里面
            maplist.add(role1);
        }
        return maplist;
    }


    //根据用户id查询出对应的角色和菜单和资源
    //根据获取到的角色查询出菜单和资源
    public List<Role> getRoleMenuResourcesUserById(List<Role> roleList1) {
        List<Role> maplist = new ArrayList<>();
        for (int i = 0; i < roleList1.size(); i++) {
            //把遍历出的菜单数据放角色对像中
            Role role1 = roleList1.get(i);
            String aa = role1.getId();
            //传角色id查询出菜单
            List<Menu> menulist1 = roleMapper.getRoleById(aa);
            //根据获取到的菜单查询出资源
            List<Menu> mlist1 = new ArrayList<>();
            for (int s = 0; s < menulist1.size(); s++) {
                //把遍历出的菜单数据放资源对像中
                Menu menu1 = menulist1.get(s);
                String bb = menu1.getId();
                //传菜单id查询出资源
                List<Resources> resources1 = menuMapper.getMenuById(bb);
                menu1.setResources(resources1); //Role 里面得有 Menu集合,把获取到的菜单集合set到角色对象里面
                mlist1.add(menu1);
                role1.setMenus(mlist1); //Role 里面得有 Menu集合,把获取到的菜单集合set到角色对象里面
                maplist.add(role1);
            }

        }
        return maplist;
    }
}
