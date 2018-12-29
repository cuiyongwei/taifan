package com.taifan1.controller;
import com.taifan1.domain.User;
import com.taifan1.domain.menu;
import com.taifan1.domain.operation;
import com.taifan1.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;

@Api(value = "用户controller",tags={"用户操作接口"})
@RestController
@RequestMapping(value ="/user")
@EnableSwagger2
public class UserController {
    @Autowired
    private UserService userService;

    //查询所有用户
    @ApiOperation(value = "查询所有用户",notes = "查询所有用户")
    @GetMapping(value = "")
    public List<User> findall(){
        List<User> booklist = userService.getall();
        System.out.println(booklist);
        return booklist;
    }
    //添加用户
    @ApiOperation(value = "添加用户",notes = "添加用户")
    @PostMapping(value = "")
    public int adduser(@ApiParam(value = "用户各个属性") @RequestBody User user){
        userService.addUser(user);
        user.getId();
        System.out.println("返回的id"+user.getId());
        return user.getId();
    }
    //修改用户
    @ApiOperation(value = "修改用户",notes = "修改用户")
    @PutMapping(value = "")
    public void  upter(@ApiParam(value = "用户的各个属性") @RequestBody User user ){
        userService.upter(user);
    }
    //删除用户
    @ApiOperation(value = "删除用户",notes = "删除用户")
    @DeleteMapping(value = "/{id}")
    @ApiImplicitParam(name ="id",value ="用户的id",required = true,dataType ="Integer",paramType = "path")
    public void dele(@PathVariable("id") Integer id){
        userService.dele(id);
    }
    //添加菜单
    @ApiOperation(value = "添加菜单",notes = "添加菜单")
    @PostMapping("/menu")
    public void inadd( @RequestBody menu menu){
        userService.inadd(menu);
    }
    //查询所有菜单
    @ApiOperation(value = "查询所有菜单",notes = "查询所有菜单")
    @GetMapping(value = "/menu")
    public List<menu> findall1(){
        List<menu> booklist1 = userService.quan();
        System.out.println(booklist1);
        return booklist1;
    }
    //修改菜单
    @ApiOperation(value="修改菜单",notes = "修改菜单")
    @ApiImplicitParam(name ="menu",value ="menu的属性",dataType ="menu",paramType = "body")
    @PutMapping(value = "/menu")
    public void upde(@RequestBody  menu menu){
        userService.upde(menu);
    }
    //删除菜单
    @ApiOperation(value = "删除菜单",notes = "删除菜单")
    @DeleteMapping("/menu/{id}")
    public void dete(@PathVariable Integer id){
        userService.dete(id);
    }
    //给用户赋予菜单
    @ApiOperation(value = "给用户赋予菜单",notes = "给用户赋予菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="menu_id", value="菜单id数组集", required=true, paramType="query" ,allowMultiple=true, dataType = "String")})
    @PutMapping("/give")
    public void seUser(@RequestParam Integer id,@RequestParam Integer[] menu_id){
        for (int b : menu_id) {
            Integer int1 =  userService.cha1(id,b);
            System.out.println("查出的数据"+int1);
            if (int1==null||int1 ==0){
                int a = userService.setaUse(id, b);
            }else{
                System.out.println("重复");
            }
        }
    }
    //传用户id查询出菜单
    @ApiOperation(value = "传用户id查询出菜单",notes = "查询用户管理的菜单")
    @GetMapping("/menu/{id}")
    public List<menu> slct(@PathVariable Integer id){
        List<menu> booklist = userService.getall(id);
        return booklist;
    }
    //添加功能
    @ApiOperation(value = "添加功能",notes = "添加功能")
    @PostMapping("/operation")
    public void opadd( @RequestBody operation operation){
        userService.opadd(operation);
    }
    //查询所有的功能
    @ApiOperation(value = "查询所有的功能",notes = "查询所有的功能")
    @GetMapping(value = "/operation")
    public List<operation> sect(){
        List<operation> sir = userService.sect();
        return sir;
    }
    //删除功能
    @ApiOperation(value = "删除功能",notes = "删除功能")
    @DeleteMapping("/operation/{id}")
    public void delte(@PathVariable Integer id){
        userService.delte(id);
    }
    //给菜单赋予功能
    @ApiOperation(value = "给菜单赋予功能",notes = "给菜单赋予功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menu_id", value="菜单的id", required=true, dataType = "Integer",paramType="query" ),
            @ApiImplicitParam(name = "id",value = "功能的id数组集",required = true,dataType = "String",allowMultiple=true,paramType = "query")})
    @PutMapping("/operation")
    public void setaa(@RequestParam Integer[] id,@RequestParam Integer menu_id){
        for (int a : id) {
                int a1 = userService.setss(a,menu_id);
        }
    }
    //传菜单id,查询出功能
    @ApiOperation(value = "传菜单id查询出对应的功能",notes = "传菜单id,查询出功能")
    @GetMapping("/menu/query/{id}")
    public List<operation> slt(@PathVariable Integer id){
        List<operation> book_list1= userService.getAllid(id);
        return book_list1;
    }
    //整合
    //传用户id 查询出菜单和功能
    @ApiOperation(value = "传用户id 查询出菜单和功能",notes = "传用户id 查询出菜单和功能")
    @GetMapping("/yonghu/{id}")
    public List<menu> find(@PathVariable("id") Integer id){
        //传用户id 查询出菜单
        List<menu> booklist = userService.getall(id);
        List<menu> maplist = new ArrayList<>();
        System.out.println(booklist);
        for(int i=0;i<booklist.size();i++){
            //把遍历出的菜单数据放对像中
            menu menu1 = booklist.get(i);
            Integer aa = menu1.getId();
            //传菜单id查询出功能
            List<operation> book_list1= userService.getAllid(aa);
            menu1.setOperation(book_list1);
            maplist.add(menu1);
        }
        return maplist;
    }
    //根据用户id 直接查询出所对应的功能
    @ApiOperation(value = "根据用户id 直接查询出所对应的功能",notes = "根据用户id 直接查询出所对应的功能")
    @GetMapping("give/{id}")
    public List<operation> findsss(@PathVariable("id") Integer id){
        List<operation> maplist1 = userService.All(id);
        return maplist1;
    }


}
