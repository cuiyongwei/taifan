package com.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.education.domain.Role;
import com.education.domain.User;
import com.education.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户conctroller",tags = {"用户操作接口"})
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    //查询所有用户
    @ApiOperation(value = "查询所有用户",notes = "查询所有用户")
    @GetMapping(value = "",produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="pageSize", value="每页显示的条数", dataType = "Integer",paramType="query")
    })
    public String getAllUser(@RequestParam int pageNum,@RequestParam int pageSize ) throws Exception {
        List<User> userList1 = userService.getPaging(pageNum,pageSize);
        List<User> counts = userService.getAllUser();
       int number = counts.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum",pageNum);
        jsonObject.put("pageSize",pageSize);
        jsonObject.put("count",number);
        jsonObject.put("data",userList1);
        return jsonObject.toJSONString();
    }

    //根据用户名查询用户
    @ApiOperation(value = "根据用户名查询用户",notes = "查询所有用户")
    @GetMapping(value = "/Paging",produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="name",value ="用户名",required = true,dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="pageSize", value="每页显示的条数", dataType = "Integer",paramType="query")
    })
    public String getUser(@RequestParam String name,@RequestParam int pageNum,@RequestParam int pageSize) throws Exception {
        List<User> booklist=userService.getUser(name,pageNum,pageSize);
        List<User> userList1 = userService.getAllUser();
        int count = userList1.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum",pageNum);
        jsonObject.put("pageSize",pageSize);
        jsonObject.put("count",count);
        jsonObject.put("data",booklist);
      return jsonObject.toJSONString();
    }

    //添加用户
    @ApiOperation(value = "添加用户",notes = "添加用户")
    @PostMapping(value = "")
    public void addUser(@ApiParam(value = "用户的各个属性") @RequestBody User user){
        userService.addUser(user);
    }

    //修改用户
    @ApiOperation(value = "修改用户",notes = "修改用户")
    @PutMapping(value = "")
    public void upDateUser(@ApiParam(value = "用户的各个属性") @RequestBody User user){
        userService.upDateUser(user);
    }

    //删除用户
    @ApiOperation(value = "删除用户",notes = "根据id删除用户")
    @ApiImplicitParam(name = "id",value = "用户的id",required = true,dataType = "String",paramType = "path")
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }

    //给用户赋予角色
    @ApiOperation(value = "给用户赋予角色",notes = "传用户id,和多个角色id")
    @PostMapping(value = "/UserRole")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "roleId",value = "角色id",required = true,dataType = "String",allowMultiple = true,paramType = "query")
    })
    public void addUserRole(@RequestParam String id,@RequestParam String[] roleId){
        for (String a : roleId){
            //先判断是否已经赋值过
            int int1 = userService.selectUserByIdRoleById(id, a);
            if (int1 == 0) {
                //赋予角色
                userService.addUserRole(id,a);
            }else {
                System.out.println("重复");
            }

        }

    }

    //根据用户id查询对应的角色
    @ApiOperation(value = "传用户id查询对应的角色",notes = "根据用户id查询对应的角色")
    @GetMapping(value = "/User/{id}")
    public List<Role> getUserById(@PathVariable String id){
      List<Role> roleList = userService.getUserById(id);
      return roleList;
    }

    //根据用户id查询出对应的角色和菜单
    @ApiOperation(value = "根据用户id查询出对应的角色和菜单",notes = "根据用户id查询出对应的角色和菜单")
    @GetMapping(value = "/RoleMenuUserById/{id}")
    public List<Role> getRoleMenuUserById(@PathVariable String id){
        //传用户id查询出角色
        List<Role> roleList1 = userService.getUserById(id);
        return userService.getRoleById(roleList1);
    }

    //根据用户id查询出对应的角色和菜单和资源
    @ApiOperation(value = "根据用户id查询出对应的角色和菜单和资源",notes = "根据用户id查询出对应的角色和菜单和资源")
    @GetMapping(value = "/RoleMenuResourcesUserById/{id}")
    public List<Role> getRoleMenuResourcesUserById(@PathVariable String id){
        //传用户id查询出角色
        List<Role> roleList1 = userService.getUserById(id);
        //根据角色查询出菜单和资源
        return userService.getRoleMenuResourcesUserById(roleList1);
    }



}
