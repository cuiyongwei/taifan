package com.education.controller;

import com.education.domain.Menu;
import com.education.domain.Role;
import com.education.service.RoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 角色控制层
 */

@Api(value = "角色controller",tags = {"角色操作接口"})
@RestController
@RequestMapping("/Role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //添加角色
    @ApiOperation(value = "添加角色",notes = "添加角色")
    @PostMapping(value = "")
    public void addRole(@ApiParam(value = "角色的各个属性") @RequestBody Role role){
        roleService.addRole(role);
    }

    //获取所有角色
    @ApiOperation(value = "获取所有角色",notes = "获取所有角色")
    @GetMapping("")
    public List<Role> getRole(){
        return roleService.getRole();
    }

    //修改角色
    @ApiOperation(value = "修改角色",notes = "修改角色")
    @PutMapping(value = "")
    public void upDate(@ApiParam(value = "角色的各个属性") @RequestBody Role role){
        roleService.upDateRole(role);
    }

    //删除角色
    @ApiOperation(value = "删除角色",notes = "根据id删除用户")
    @DeleteMapping(value = "/{id}")
    @ApiImplicitParam(name = "id",value = "角色id",required = true,dataType = "String",paramType = "Path")
    public void deleteRole(@PathVariable("id") String id){
        roleService.deleteRole(id);
    }

    //给角色赋予菜单
    @ApiOperation(value = "给角色赋予菜单",notes = "给角色赋予菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "角色id",required = true,dataType ="String",paramType ="query"),
            @ApiImplicitParam(name = "menuId",value = "菜单id",required = true,dataType ="String",paramType ="query",allowMultiple = true)
    })
    @PostMapping(value = "/RoleMenu")
    public void addRoleMenu(@RequestParam String id,@RequestParam String[] menuId){
        for (String a : menuId){
            //先判断是否已经赋值过
            Integer int1 = roleService.selectRoleByIdMenuById(id,a);
            if (int1 == 0) {
                //赋予菜单
                roleService.addRoleMenu(id,a);
            }else {
                System.out.println("重复");
            }

        }

    }

    //根据角色id查询对应的菜单
    @ApiOperation(value = "根据角色id查询对应的菜单",notes = "根据角色id查询对应的菜单")
    @GetMapping(value = "/Role/{id}")
    public List<Menu> getRoleById(@PathVariable String id){
       return roleService.getRoleById(id);
    }

    //根据角色id查询出对应的菜单和资源
    @ApiOperation(value = "根据角色id查询出对应的菜单和资源",notes = "根据角色id查询出对应的菜单和资源")
    @GetMapping(value = "/MenuResourcesRoleById/{id}")
    public List<Menu> getMenuResourcesRoleById(@PathVariable String id){
        //传角色id查询出菜单
        List<Menu> menuList1 = roleService.getRoleById(id);
        return roleService.getMenuById(menuList1);
    }

}
