package com.education.service;

import com.education.domain.Menu;
import com.education.domain.MenuAfter;
import com.education.domain.Resources;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {

    //查询所有菜单
    int getMenuAll();

    //查询根节点
    Menu getMenu2();

    //根据id获取根节点对象
    Menu getMenu1(String id);

    //查询id下的所有子节点
    List<Menu> getMenu(String id);

    //添加菜单
    void addMenu(MenuAfter menuAfter);

    //修改菜单
    void upDateMenu(MenuAfter menuAfter);

    //删除菜单
    void deleteMenu(String id);

    //给菜单赋予资源
    //先查询是否已经赋值过
    int selectMenuByIdResourcesById(String id,String resourcesId);
    //赋资源
    int addMenuResources(String id,String resourcesId);

    //根据菜单id查询对应的资源
    List<Resources> getMenuById(String id);

}
