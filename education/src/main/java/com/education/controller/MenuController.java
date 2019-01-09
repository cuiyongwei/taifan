package com.education.controller;

import com.education.domain.Menu;
import com.education.domain.MenuAfter;
import com.education.domain.Resources;
import com.education.service.MenuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "菜单controller",tags = {"菜单操作接口"})
@RestController
@RequestMapping(value = "/Menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    //查询所有菜单显示树状
    @ApiOperation(value = "查询所有菜单",notes = "查询所有菜单显示树状")
    @GetMapping(value = "")
    public Menu getMenu(){
       int me = menuService.getMenuAll();
        if (me == 0) {
            return null;
        }else {
        Menu menu = menuService.getMenu2();
        return getMenuByIdAll(menu.getId());
        }
    }

    //根据id查询对应的下级菜单
    @ApiOperation(value = "根据id查询对应的下级菜单",notes = "根据id查询对应的下级菜单")
    @GetMapping(value = "/{parentid}")
    public Menu getMenuByIdAll(String id){

        //根据id获取根节点对象(SELECT * FROM tb_tree t WHERE t.cid=?)
        Menu menu = menuService.getMenu1(id);
        //查询id下的所有子节点(SELECT * FROM tb_tree t WHERE t.pid=?)
        List<Menu> list1 = menuService.getMenu(id);
        //遍历子节点
        for(Menu menu1 : list1){
            Menu n = getMenuByIdAll(menu1.getId()); //递归
            menu.getNodes().add(n);
        }
        return menu;
    }

    //添加菜单
    @ApiOperation(value = "添加菜单",notes = "添加菜单")
    @PostMapping(value = "")
    public void addMenu(@ApiParam(value = "菜单的各个属性") @RequestBody MenuAfter menuAfter){
        menuService.addMenu(menuAfter);
    }

    //修改菜单
    @ApiOperation(value = "修改菜单",notes = "修改菜单")
    @PutMapping(value = "")
    public void upDateMenu(@ApiParam(value = "菜单的各个属性")@RequestBody MenuAfter menuAfter){
        menuService.upDateMenu(menuAfter);
    }

    //删除菜单
    @ApiOperation(value = "删除菜单",notes = "根据id删除菜单")
    @DeleteMapping(value = "/{id}")
    @ApiImplicitParam(name = "id",value = "菜单id",required = true,dataType = "String",paramType = "path")
    public void deleteMenu(@PathVariable String id){
        menuService.deleteMenu(id);
    }

    //给菜单赋予资源
    @ApiOperation(value = "给菜单赋予资源",notes = "给菜单赋予资源")
    @PostMapping(value = "/MenuResources")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "菜单id",required = true,dataType = "String",paramType ="query"),
            @ApiImplicitParam(name = "resourcesId",value = "资源id",required = true,dataType = "String",allowMultiple = true,paramType ="query")
    })
    public void addMenuResources(@RequestParam String id,@RequestParam String[] resourcesId){
        for (String a : resourcesId){
            //先判断是否已经赋值过
          Integer int2 = menuService.selectMenuByIdResourcesById(id,a);
            if (int2 == 0) {
                //赋予资源
                menuService.addMenuResources(id,a);
            }else {
                System.out.println("重复");
            }
        }
    }

    //根据菜单id查询对应的资源
    @ApiOperation(value = "根据菜单id查询对应的资源",notes = "根据菜单id查询对应的资源")
    @GetMapping(value = "/Menu/{id}")
    @ApiImplicitParam(name = "id",value = "菜单id",required = true,dataType = "String",paramType = "path")
    public List<Resources> getMenuById(@PathVariable String id){
        return menuService.getMenuById(id);
    }

}
