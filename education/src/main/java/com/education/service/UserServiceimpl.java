package com.education.service;

import com.education.domain.*;
import com.education.mapper.MenuMapper;
import com.education.mapper.RoleMapper;
import com.education.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
//@Transactional(readOnly = true)
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


    //导入Excel表，解析，字段赋值，存储
    @Transactional(readOnly = false,rollbackFor = Exception.class)//该属性用于设置当前事务是否为只读事务，设置为true表示只读，false则表示可读写，默认值为false。例如：@Transactional(readOnly=true)如果发生Exception异常了,就进行回滚
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
            List<Util> userList = new ArrayList<Util>(); //创建一个集合
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            // ！fileName.matches("^.+\\.(?i)(xls)$") 以任意个字符开头，用.分割 忽略大小写，xls不区分大小写。比如aaaa.xls或者123ad.Xls  如果str不能进行正则匹配，
            //throw new MyException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream(); //getInputStream方法可以得到一个输入流，客户端的Socket对象上的getInputStream方法得到输入流其实就是从服务器端发回的数据。
        Workbook wb = null;
        if (isExcel2003) {//HSSFWorkbook只能操作excel2003以下版本,XSSFWorkbook只能操作excel2007以上版本，所以利用Workbook接口创建对应的对象操作excel来处理兼容性
            wb = new HSSFWorkbook(is); //2003版本以下的读 文件流
        } else {
            wb = new XSSFWorkbook(is); //2007版本以上的
        }



        Sheet sheet = wb.getSheetAt(0);//wd.getsheetAt(0)方法可以获取到excel的第一个sheet页
        if(sheet!=null){
            notNull = true;
        }
        Util util;

        Class<Util> usa = Util.class;
        Field fields[]= usa.getDeclaredFields();//返回Class中所有的字段

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {//sheet.getLastRowNum()获得sheet总行数
            Row row = sheet.getRow(r);//获得第行数据对象
            if (row == null){
                continue;
            }

            util = new Util();

            if( row.getCell(0).getCellType() !=1){// 将区域编号的单元格中的内容当做字符串处理
               // throw new MyException("导入失败(第"+(r+1)+"行,姓名请设为文本格式)");
            }
            Field.setAccessible(fields, true);
            fields[1].set(util,row.getCell(0).getStringCellValue());
            /*String name = row.getCell(0).getStringCellValue();//获取第一个单元格的内容

                if(name == null || name.isEmpty()){
                    //throw new MyException("导入失败(第"+(r+1)+"行,姓名未填写)");
                }*/

                System.out.println("获取第一个单元格的内容"+ util.getName());


                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            fields[2].set(util,row.getCell(1).getStringCellValue());
               /* String phone = row.getCell(1).getStringCellValue();//获取第二个单元格的内容
                if(phone==null || phone.isEmpty()){
                   // throw new MyException("导入失败(第"+(r+1)+"行,电话未填写)");
                }*/

            fields[3].set(util,row.getCell(2).getStringCellValue());
                /*String add = row.getCell(2).getStringCellValue();//获取第三个单元格的内容
                if(add==null){
                    //throw new MyException("导入失败(第"+(r+1)+"行,不存在此单位或单位未填写)");
                }*/
            fields[4].set(util,row.getCell(3).getDateCellValue());
               /* Date date = new Date();
                if(row.getCell(3).getCellType() !=0){
                    //throw new MyException("导入失败(第"+(r+1)+"行,入职日期格式不正确或未填写)");
                }else{
                    date = row.getCell(3).getDateCellValue();//获取第四个单元格内容
                }*/
            fields[5].set(util,row.getCell(4).getStringCellValue());
               // String des = row.getCell(4).getStringCellValue();//获取第五个单元内容
            //把获取到的值给util
        /*    util.setName(row.getCell(0).getStringCellValue());
            util.setPhone(row.getCell(1).getStringCellValue());
            util.setAddress(row.getCell(2).getStringCellValue());
            util.setEnrolDate(row.getCell(3).getDateCellValue());
            util.setDes(row.getCell(4).getStringCellValue());

            Class<Util> usa = Util.class;
            Field fields[]= usa.getDeclaredFields();// 获取当前类所有属性
            System.out.println("util的所有属性"+ usa.getDeclaredFields());

            Field.setAccessible(fields, true);
            for (Field field : fields) {
                // 获取属性名 属性值 属性类型
                System.out.println("获取到的值"+fields[1]);
                System.out.println("属性名:" + field.getName( ) + "第一个属性："+fields[1].getName()+"第二个属性"+fields[2].getName()+ "\t第一个属性值:" + field.get(util) + "  \t属性类型:" + field.getType());
            }




            Method setNameMethod = usa.getMethod("setName", String.class);//获取setName()方法

            Method setPhoneMethod = usa.getMethod("setPhone", String.class);//setPhone()方法

            setNameMethod.invoke(fields[1].getName(), row.getCell(0).getStringCellValue());
            Method getNameMethod = usa.getMethod("getName"); //获取getName()方法
            System.out.println(getNameMethod.invoke(util));*/

          /*  util.setName(name);
            util.setPhone(phone);
            util.setAddress(add);
            util.setEnrolDate(date);
            util.setDes(des);*/
          util.getName();
          util.getPhone();
          util.getAddress();
          util.getEnrolDate();
          util.getDes();
            System.out.println("查看值是否赋在里面了"+util);

            userList.add(util);//把带满对象的值给userList集合
        }
        for (Util userResord : userList) {
            String name = userResord.getName();
            int cnt = userMapper.selectByName(name);//获取相同名字的数量
            if (cnt == 0) {
                userMapper.addutil(userResord);//如果不存在就添加值
                System.out.println(" 插入 "+userResord);
            } else {
                userMapper.upDateutil(userResord);//如果存在就跟新
                System.out.println(" 更新 "+userResord);
            }
        }
        return notNull;

    }

    //从excel中获取的值添加到数据库
    @Override
    public void addText(List<Text> utils) {
        for (int k =0;k<utils.size();k++){

           /* Text text = utils.get(k);
            System.out.println(text);
            System.out.println(utils.get(k));*/
            System.out.println("打印"+utils.get(k));
            System.out.println(utils.get(k).getName());
            userMapper.addText(utils.get(k));
        }

    }

    //获取person数据
    @Override
    public List<Person> getAllPerson() {
        return userMapper.getAllPerson();
    }


}
