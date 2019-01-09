package com.education.service;

import com.education.domain.Menu;
import com.education.domain.MenuAfter;
import com.education.domain.Resources;
import com.education.mapper.MenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
public class MenuServiceimpl implements MenuService{

    @Resource
    private MenuMapper menuMapper;

    Menu menu = new Menu();

    //查询所有菜单
    @Override
    public int getMenuAll() {
        return menuMapper.getMenuAll();
    }

    //查询根节点
    @Override
    public Menu getMenu2() {
        return menuMapper.getMenu2();
    }

    //根据id获取根节点对象
    @Override
    public Menu getMenu1(String id) {
        return menuMapper.getMenu1(id);
    }

    //查询id下的所有子节点
    @Override
    public List<Menu> getMenu(String id) {
        return menuMapper.getMenu(id);
    }

    //添加菜单
    @Override
    public void addMenu(MenuAfter menuAfter) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());//获取当前时间
        menu.setCreatedon(calendar.getTime());//把当前时间给menu对象里面的setCreatedon
        BeanUtils.copyProperties(menuAfter,menu);//把menuAfter里的数据赋给menu对象
        menuMapper.addMenu(menu);//把menu对象传过去
    }

    //修改菜单
    @Override
    public void upDateMenu(MenuAfter menuAfter) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        System.out.println("获取的当前时间"+calendar);
        menu.setCreatedon(calendar.getTime());
        menu.setUpdatedon(calendar.getTime());
        BeanUtils.copyProperties(menuAfter,menu);
        menuMapper.upDateMenu(menu);
    }

    //删除菜单
    @Override
    public void deleteMenu(String id) {
        menuMapper.deleteMenu(id);
    }

    //给菜单赋予资源
    //先查询是否已经赋值过
    @Override
    public int selectMenuByIdResourcesById(String id, String resourcesId) {
        return menuMapper.selectMenuByIdResourcesById(id,resourcesId);
    }
    //赋资源
    @Override
    public int addMenuResources(String id, String resourcesId) {
        return menuMapper.addMenuResources(id,resourcesId);
    }

    //根据菜单id查询对应的资源
    @Override
    public List<Resources> getMenuById(String id) {
        return menuMapper.getMenuById(id);
    }


}
