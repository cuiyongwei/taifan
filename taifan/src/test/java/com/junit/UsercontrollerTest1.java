package com.junit;
import com.taifan.controller.UserController;
import com.taifan.service.UserService;
import com.taifan.service.UserServiceimpl;
import com.taifan.ZhuLei;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ZhuLei.class )
@WebAppConfiguration// 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class UsercontrollerTest1 {
    @Autowired
    private UserController userController;
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceimpl userServiceimpl;
/*    @Autowired
    private UserMapper userMapper;*/

    @Test
    public void testchaxun() {
        System.out.println("测试userController.查询方法");
        //userController.chaxun();
        System.out.println("测试完成");
    }



    // 在所有方法执行之前执行
    @BeforeClass
    public static void globalInit() {
        System.out.println("初始化所有的方法");//初始化所有的方法……
    }
    @AfterClass
// 在所有方法执行之后执行
    public static void globalDestory() {

        System.out.println("毁掉所有的方法");//毁掉所有的方法……
    }

    @Before
// 在每个测试方法之前执行
    public void setUp() {
        System.out.println("在每个测试方法之前执行");
        userController = new UserController();
        userService = new UserServiceimpl();
    }

    @After
// 在每个测试方法之后执行
    public void tearDown() {
        System.out.println("在每个测试方法之后执行");
    }



/*    @Test
    public void Testchu() {
        System.out.println("测试  userMapper.findAll();;方法");
        userMapper.findAll();
    }*/


}
