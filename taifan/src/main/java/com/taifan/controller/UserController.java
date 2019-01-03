package com.taifan.controller;

import com.taifan.domain.User;
import com.taifan.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * 用户功能模块
 */

@Api(value = "用户controller",tags={"用户操作接口"})
@RestController
@RequestMapping(value ="/user")
@EnableSwagger2
public class UserController {
    /* *
     * 自动注入UserService
     * */
    @Autowired
    private UserService userService;
    private static final Logger logger= LoggerFactory.getLogger(UserController.class);
    //用户查询
    @ApiOperation(value = "查询", notes = "查询数据库中所有的学生信息")
    @GetMapping(value = "")//替代@RequestMapping(value="", method = RequestMethod.GET)
    public List<User> chaxun(){
        List<User> book_list = userService.getAll();
        return book_list;
    }
    //根据id查询
    @ApiOperation(value = "查询用户",notes = "根据url的id来查询用户")
    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "Integer",paramType = "path")
    @GetMapping("/{id}")
    public List<User>  findid(@PathVariable("id") Integer id){
        List<User> book_list= userService.getAllid(id);
        return book_list;
    }
    //用户删除
    @ApiOperation(value = "删除用户",notes = "根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "Integer",paramType = "path")
    @DeleteMapping("/{id}")//@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id){
        System.out.println("进入删除方法");
        userService.delete(id);
        return "success";
    }
    //修改
    @ApiOperation(value = "更新")
    @ApiImplicitParam(name = "user",value = "用户的信息",required = true,dataType = "User",paramType = "body")
    @PutMapping("")
    public void updateUser(@ApiParam(value = "用户或用户各个属性") @RequestBody User user) {
        userService.upDate1(user);
    }
    //部分更新
    @ApiOperation(value = "部分更新",notes = "实行部分更新")
    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "User",paramType = "query")
    @PatchMapping("")
    public void updateUser1(@ApiParam(value = "用户或用户各个属性") User user) {
        userService.upDate1(user);
    }
    @ApiOperation(value = "添加",notes = "添加用户")
    public String addUser(User user ){  //使用@RequestBody注解 swaager输入一行一行的参数的格式会消失
        userService.addUser(user);
        return "success";
    }
    //传用户名密码直接判断
    @ApiOperation(value = "登录",notes="注意事项")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "name",value = "用户名",required = true,paramType = "query"),
            @ApiImplicitParam(dataType = "Integer",name = "password",value = "密码",required = true,paramType = "query")
    })
    @PostMapping("/login")
    public List<User> login5( @RequestParam String name,@RequestParam Integer password){
        List<User> book_list = userService.login5(name,password);
        System.out.println(book_list);
        if (book_list !=null){
            System.out.println("用户名："+name+"password："+password+"登录成功");
        }else{
            System.out.println("登录失败");
        }
        return book_list;
    }
}
