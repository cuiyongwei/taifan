package com.education.controller;

import com.alibaba.fastjson.JSONObject;
import com.education.domain.*;
import com.education.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
        List<User> userList2 = userService.getPaging(pageNum,pageSize);
        List<User> counts = userService.getAllUser();
        int number = counts.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum",pageNum);
        jsonObject.put("pageSize",pageSize);
        jsonObject.put("count",number);
        jsonObject.put("data",userList2);
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

    //自定义导入excel表
    //导入Excel表，解析，字段赋值，存储
    @PostMapping("/import")
    @ApiOperation(value = "自定义导入Excel",notes = "自定义方法导入")
    public boolean addUser(@RequestParam("file") MultipartFile file) {
        boolean a = false;
        String fileName = file.getOriginalFilename();//获取文件名
        try {
          // a = userService.batchImport(fileName, file);
           List<Text> utils = FileUtil.batchImport(fileName, file,Text.class);
            System.out.println(utils);
            userService.addText(utils);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  a;
    }

/*    //导入excel表
    @PostMapping("importExcel")
    @ApiOperation(value = "工具导入excel",notes = "工具导入excel")
    public void importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();//获取文件名
        System.out.println("获取到的文件名"+fileName);
       // String filePath = "F:\\海贼王.xls";
        //解析excel，

        List<Person> personList = FileUtil.importExcel(file, 1, 1,Person.class);
        //也可以使用MultipartFile,使用FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        System.out.println("导入数据一共【"+personList.size()+"】行");
        System.out.println("读出Excel的数据"+personList);
        //TODO 保存数据库
    }

    @PostMapping("/importExcel2")
    @ApiOperation(value = "工具导入excel1",notes = "工具导入excel1")
    public void importExcel2(@RequestParam("file") MultipartFile file) {
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);

        // 需要验证
        importParams.setNeedVerfiy(true);

        try {
            ExcelImportResult<Person> result = ExcelImportUtil.importExcelMore(file.getInputStream(), Person.class,
                    importParams);

            List<Person> successList = result.getList();
            for (Person demoExcel : successList) {
                System.out.println(demoExcel);
            }
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }*/

    //导出excel
    @ApiOperation(value = "导出excel",notes = "导出excel",produces="application/octet-stream")//注解RequestMapping中produces属性可以设置返回数据的类型以及编码是以流的形式下载文件，这样可以实现任意格式的文件下载。
    @GetMapping(value = "export")
    public void export(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //模拟从数据库获取需要导出的数据
        List<Person> counts = userService.getAllPerson();
        //导出操作
        FileUtil.exportExcel(counts,"花名册","草帽一伙",Person.class,"海贼王.xls",response);
      /*  String[] title = {"花名册"};
        Lise.generateSheet(response,title, count, User.class);*/

     /*   String sheetName = "用户表单";
        String titleName = "用车申请数据统计表";
        String fileName = "用车申请统计表单";
        int columnNumber = 3;
        int[] columnWidth = { 10, 20, 30 };
        List<User> dataList = new ArrayList<>();
        User user = new User("1","张三","123");
        dataList.add(user);

        String[] columnName = { "单号", "申请时间", "申请部门" };
        //浏览器下载文件
         FileUtil.ExportWithResponse(sheetName, titleName, fileName,
                columnNumber, columnWidth, columnName, dataList,response);*/
        //文件保存制定位置
        /*  FileUtil.ExportNoResponse(sheetName, titleName, fileName,
                columnNumber, columnWidth, columnName, dataList);*/



/*
        List<User> personList = new ArrayList<>();
        User user1 = new User("1","张三","123");
        personList.add(user1);
        System.out.println("ssssssssssss"+personList);

        //建立一张excel表，首先建立一个工作簿，
        // 然后建立一个sheet，在sheet中建立一行作为表头，
        // 将数据库查询到的数据分别放到对应的表头的下方。
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
       // List<User> count = userService.getAllUser();//获取数据
       // String fileName = "userinf" + ".xls";//设置要导出的文件的名字


        String filename = "userinf" + ".xls";;

        // 新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "id","姓名", "登录密码"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);// 创建第0行 也就是标题

        //创建标题的单元格样式style2以及字体样式headerFont1
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont headerFont1 = (HSSFFont) workbook.createFont(); // 创建字体样式
        headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
        headerFont1.setFontName("黑体"); // 设置字体类型
        headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
        style2.setFont(headerFont1); // 为标题样式设置字体样式

        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);//获取列
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellStyle(style2); // 设置标题样式
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (User teacher : personList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(teacher.getName());
            row1.createCell(1).setCellValue(teacher.getPassword());
            rowNum++;
        }
       *//* response.setContentType("application/ms-excel;charset=UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));
        response.setHeader("Content-Disposition", "attachment;filename="
                .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));*//*

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename="
                + filename+".xls");

    // response.flushBuffer();
      //  workbook.write(response.getOutputStream());
        OutputStream out = response.getOutputStream();
        try {
            workbook.write(out);// 将数据写出去
            String str = "导出" + filename + "成功！";
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
            String str1 = "导出" + filename + "失败！";
            System.out.println(str1);
        } finally {
            out.close();
        }*/

     /*   String sheetName = "用车统计表单";
        String titleName = "用车申请数据统计表";
        String fileName = "用车申请统计表单";
        int columnNumber = 3;
        int[] columnWidth = { 10, 20, 30 };
        String[][] dataList = { { "001", "2015-01-01", "IT" },
                { "002", "2015-01-02", "市场部" }, { "003", "2015-01-03", "测试" } };
        //将List转换为二维数组String[][]
        String[] columnName = { "单号", "申请时间", "申请部门" };
        new ExportExcel().ExportNoResponse(sheetName, titleName, fileName,
                columnNumber, columnWidth, columnName, dataList);*/









    }
}

