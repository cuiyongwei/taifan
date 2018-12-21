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
 * 用户创建	POST	/books/
 * 用户修改	PUT	/books/:id/
 * 用户删除	DELETE	/books/:id/
 * 用户获取 GET /books
 * 用户获取某一图书  GET /Books/:id
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
        // 获得所有图书集合
            System.out.println("进入查询方法");
            List<User> book_list = userService.getAll();
            System.out.println("循环的数组"+book_list);
        // 跳转到main页面
        return book_list;
    }
    //根据id查询
    @ApiOperation(value = "查询用户",notes = "根据url的id来查询用户")
    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "Integer",paramType = "path")
    @GetMapping("/{id}")//@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public List<User>  findid(@PathVariable("id") Integer id){
        System.out.println("进入查询方法");
        List<User> book_list= userService.getAllid(id);
        System.out.println(book_list);
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

    //增加
    @ApiOperation(value = "添加",notes = "添加用户")
    //@ApiImplicitParam(name = "user",value = "用户的信息", required = true, dataType = "User", paramType = "body")
    @PostMapping("")   //@RequestMapping(value = "/add",method=RequestMethod.POST)
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


    //登录第一次
   /* @ApiOperation(value = "登录",notes="注意事项")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "name",value = "用户名",required = true,paramType = "query"),
            @ApiImplicitParam(dataType = "Integer",name = "password",value = "密码",required = true,paramType = "query")
    })
    @PostMapping("/login")
    public User joinAnnotation(@RequestParam String name,@RequestParam Integer password){
        User user = new User();
        System.out.println(name+password);
        user.setName(name);
        user.setPassword(password);
        //获取数据放到List集合中
        List<User> book_list = userService.login2(user);
        //遍历集合
        for(int i=0;i<book_list.size();i++){
            System.out.println(book_list.get(i));
            //把遍历出的数据放到数组元素对像
            User dataArray = book_list.get(i);
            user.setName(dataArray.getName());
            user.setPassword(dataArray.getPassword());
            user.setCreatedby(dataArray.getCreatedby());
        }

        //判断
        if (name.equals(user.getName()) && password.equals(user.getPassword())){
            System.out.println("用户名："+name+"密码"+password+"登录成功");
        }else{
            System.out.println("登录失败");
        }

        return user;
    }*/



/*
    @PostMapping("/login")//@RequestMapping(value = "/login6",method = RequestMethod.POST)
    public User login6(@RequestParam("name") String name,
                       @RequestParam("password") Integer password){
        User user = new User();
        System.out.println(name+password);
        user.setName(name);
        user.setPassword(password);
        //获取数据放到List集合中
        List<User> book_list = userService.login2(user);
        //遍历集合
        for(int i=0;i<book_list.size();i++){
            System.out.println(book_list.get(i));
            //把遍历出的数据放到数组元素对像
            User dataArray = book_list.get(i);
            user.setName(dataArray.getName());
            user.setPassword(dataArray.getPassword());
            user.setCreatedby(dataArray.getCreatedby());
        }

        //判断
        if (name.equals(user.getName()) && password.equals(user.getPassword())){
            System.out.println("用户名："+name+"密码"+password+"登录成功");
        }else{
            System.out.println("登录失败");
        }

        return null;
    }
*/



/*    //增加
    @ApiOperation(value = "添加",notes = "添加用户")
    @ApiImplicitParam(name="user",value = "用户详细实体",required = true,dataType = "User",paramType = "body")
    @RequestMapping(value = "",method=RequestMethod.POST)
    public String addUser( @RequestParam("name") String name,
                           @RequestParam("password") Integer password,
                           @RequestParam("createdby") String createdby){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setCreatedby(createdby);
        userService.addUser(user);
        return "aa";
    }*/

/*
    //修改的第二种方法
    @RequestMapping(value = "/upDate1/{id}/{name}/{password}/{createdby}",method = RequestMethod.PATCH)
    public void  upDate1(@PathVariable("id") Integer id,
                         @PathVariable("name") String name,
                         @PathVariable("password") Integer password,
                         @PathVariable("createdby") String createdby){
        System.out.println("从前端获取到的值"+id+name+password+createdby);
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setCreatedby(createdby);
        userService.upDate1(user);
    }


    //用户添加第2中方法
    @RequestMapping(value="/addU1",method = RequestMethod.POST)
    public String addU1(){
        User user = new User();
        user.setName("bbb");
        user.setPassword(222);
        user.setCreatedby("haha");
        System.out.println("获取到的值"+user);
        userService.addUser1(user);
        return "addU1";
    }


    //登录判断方法3
    //登录
    @RequestMapping(value = "/login6",method = RequestMethod.GET)
    public User login6(@RequestParam("name") String name,
                       @RequestParam("password") Integer password){
        User user = new User();
        System.out.println(name+password);
        user.setName(name);
        user.setPassword(password);
        //获取数据放到List集合中
        List<User> book_list = userService.login2(user);
        //遍历集合
        for(int i=0;i<book_list.size();i++){
            System.out.println(book_list.get(i));
            //把遍历出的数据放到数组元素对像
            User dataArray = book_list.get(i);
            user.setName(dataArray.getName());
            user.setPassword(dataArray.getPassword());
            user.setCreatedby(dataArray.getCreatedby());
        }

        //判断
        if (name.equals(user.getName()) && password.equals(user.getPassword())){
            System.out.println("用户名："+name+"密码"+password+"登录成功");
        }else{
            System.out.println("登录失败");
        }

        return null;
    }


*//*
    //登录
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public User login(String name,Integer password){
        System.out.println("用户名和密码为："+name+password);
        List<User> book_list = userService.login();
        List<User> book_list1 = userService.getAll();
        if (book_list.equals(book_list1)){
            System.out.println("登录成功");
        }else {
            System.out.println("无法比较");
        }
        System.out.println("查出的数据为"+book_list);
        if (book_list != null){
            System.out.println("用户名和密码不为空"+book_list);
        }

        return null;
    }*//*

*/


/*    //修改
    @RequestMapping(value = "/upDate",method = RequestMethod.PATCH)
    public String upDate(){
        userService.upDate();
        return "upDate";
    }*/


/*    //修改的第三种方法
    @RequestMapping(value = "/upDate4")
    @ResponseBody
    public void  upDate5(User user){
        System.out.println("从前端获取到的值"+user);
        userService.upDate1(user);
    }*/

    //登录判断方法2
    //登录
/*    @RequestMapping(value = "/login1")
    public User login1(String name,Integer password){
        User user = new User();
        System.out.println(name+password);
        user.setName(name);
        user.setPassword(password);
        //获取数据放到List集合中
        List<User> book_list = userService.login2(user);
        //遍历集合
        for(int i=0;i<book_list.size();i++){
            System.out.println(book_list.get(i));
            //把遍历出的数据放到数组元素对像
            User dataArray = book_list.get(i);
            user.setName(dataArray.getName());
            user.setPassword(dataArray.getPassword());
            user.setCreatedby(dataArray.getCreatedby());
        }
        //方法二.迭代器遍历for(Iterator<String> iter=book_list.iterator();iter.hasNext();){System.out.println(iter.next());}
        //我们用增强for循环（foreach）对其进行遍历，遍历如下：增强for循环for(String s:book_list){System.out.println(s);}增强for循环实质上是将迭代器封装成了for（单个元素：list）的形式。
        //判断
        if (name.equals(user.getName()) && password.equals(user.getPassword())){
            System.out.println("用户名："+name+"密码"+password+"登录成功");
        }else{
            System.out.println("登录失败");
        }

        return null;
    }*/








/*    //判断登录方法3
    @RequestMapping(value = "/login3")
    public String login3(){
        User user = new User();
        user.setName("awei");
        user.setPassword(123);
        user.setCreatedby("张三");
        User u = userService.login3(user);
        if (u!=null){
            System.out.println("登录成功");
        }

        return null;
    }*/

/*

    @RequestMapping(value="/login")
    public ModelAndView login(
            String name,Integer password,
            ModelAndView mv,
            HttpSession session){
        // 根据登录名和密码查找用户，判断用户登录
        User user = userService.login(name, password);
        System.out.println("用户名和密码为："+name+password);
        if(user != null){
            // 登录成功，将user对象设置到HttpSession作用范围域
            session.setAttribute("user", user);
            // 转发到main请求
            mv.setView(new RedirectView("aa"));
        }else{
            // 登录失败，设置失败提示信息，并跳转到登录页面
            mv.addObject("message", "登录名或密码错误，请重新输入!");
            mv.setViewName("loginForm");
        }
        return mv;
    }
*/




    /*   //用户修改

       //用户修改，先获取要更改的用户
       @RequestMapping(value = "/toUpdate")
       public String toUpdate(int id){
           System.out.println("进入修改方法");
           User user = userService.toUpdate(id);
           System.out.printf("要更改的数据"+user);

           return "ss";
       }

       //修改
       @RequestMapping(value = "/Update")
       public String Update(User user){
           userService.update(user);
           return "";
       }*/









/*   //用户增加
    @RequestMapping(value ="addUser")
    public String addUser(@RequestBody User user){//@RequestBody User user表示，使用@RequestBody注解获取JSON数据后，将JSON数据设置到对应的User对象的属性当中
        System.out.println("进入用户添加"+user);
        userService.addUser(user);
        return "add";
    }*/



 /*   @RequestMapping("/addUser1")
    @ResponseBody
    public User addUser1(Integer id, String name, Integer password, String created_by){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setCreatedby(created_by);
        System.out.println("获取到的值"+user);
        userService.addUser(user);
        return user;
    }*/


/*
    //用户增加
    @RequestMapping("/addUser3")
    @ResponseBody
    public String addUser(@RequestBody User user){//@RequestBody User user表示，使用@RequestBody注解获取JSON数据后，将JSON数据设置到对应的User对象的属性当中
        System.out.println("进入用户添加");
        System.out.println("ssssssssssssssss"+user);
        userService.addUser(user);
        return "addUser3";
    }
*/
//用户增加  以数组的方式传参数
/* @RequestMapping("/addUser")
@ResponseBody
public String addUser(HttpServletRequest request){//过HttpServletRequest来获取前端页面参数
    System.out.println("进入用户添加");
     request.getParameter("name");
     request.getParameter("password");
     request.getParameter("created_by");

   List<String> dataList = new ArrayList<String>();
    for(String s : dataArray){
        dataList.add(s);
    }
    String user_list =  request.getParameter("name")+request.getParameter("password")+ request.getParameter("created_by");
    String[] dataArray = new String[]{request.getParameter("name"),request.getParameter("password"),"request.getParameter(\"created_by\")"};
    System.out.println("获取到的数据"+request.getParameter("name")+request.getParameter("password")+ request.getParameter("created_by"));
    System.out.println("获取到的数据2"+dataArray);
    userService.addUser(dataArray);
    return "addUser3";
}*/

  /*  //用户增加  以数组的方式传参数
    @RequestMapping("/addUser1")
    @ResponseBody
    public String addUser1(String name,int password,String created_by){//过HttpServletRequest来获取前端页面参数
        System.out.println("进入用户添加");
        System.out.println("获取到的数据2"+name+password+created_by);
        userService.addUser(name,password,created_by);
        return "addUser3";
    }*/


    //用户登录



/*    //两种获取参数的方式


    //@PathVariabley 获取参数 url=//http://localhost:8089/aaa/4
    @RequestMapping(value ="/aaa/{id}")//必须绑定
    public void aaa(@PathVariable("id") Integer id){
        System.out.println("进入删除方法"+id);

    }
    //RequestMapping 获取参数 url=//http://localhost:8089/aaa?id=3
    @RequestMapping(value ="/aa2")
    public void ccc(@RequestParam(value = "id") Integer id){
        System.out.println("进入删除方法"+id);
    }*/


/*     @RequestMapping(value ="",method=RequestMethod.GET,consumes="application/json",produces="application/json",params="myParam=myValue",headers="Referer=http://www.ifeng.com/")

    value:  指定请求的实际地址， 比如 /action/info之类。
    method：  指定请求的method类型， GET、POST、PUT、DELETE等
    consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;     方法仅处理request Content-Type为“application/json”类型的请求. produces标识==>处理request请求中Accept头中包含了"application/json"的请求，同时暗示了返回的内容类型为application/json;
    produces:  指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回,但是必须要和@ResponseBody注解一起使用才可以
    params： 指定request中必须包含某些参数值是，才让该方法处理    仅处理请求中包含了名为“myParam”，值为“myValue”的请求，起到了一个过滤的作用。
    headers： 指定request中必须包含某些指定的header值，才能让该方法处理请求.Headers里面可以匹配所有Header里面可以出现的信息，不局限在Referer信息

    其中，consumes， produces使用content-typ信息进行过滤信息；headers中可以使用content-type进行过滤和判断。*/


}
