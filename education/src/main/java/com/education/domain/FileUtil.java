package com.education.domain;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 导入到出公用方法
 *
 */

public class FileUtil {

    public static final String[] EXCEL_EXT = {"xls","xlsx"};

  /*  public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName,boolean isCreateHeader, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }*/
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));//,ExcelType.HSSF
    }
    /*public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response){
        defaultExport(list, fileName, response);
    }*/

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
       Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
           // response.reset();//清除空白行
            response.setCharacterEncoding("UTF-8");

            response.setHeader("content-Type", "application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

           /*//使用后缀为xlsx
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment;filename=fileName" + ".xlsx");*/

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows, Class<T> pojoClass){
        if (StringUtils.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        }catch (NoSuchElementException e){
            //throw new NormalException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
           // throw new NormalException(e.getMessage());
        }
        return list;
    }
    //工具导入
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)
            throws Exception {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list =null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new Exception("excel文件不能为空");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        System.out.println(list);
        return list;
    }

    //自定义方法
    //导入Excel表，解析，字段赋值，存储
    public static <T> List<T> batchImport(String fileName, MultipartFile file,Class<T> pojoClass) throws Exception {
        boolean notNull = false;
        List<T> userList = new ArrayList<T>(); //创建一个集合
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
        List<T> list =null;
        System.out.println("传过来的类"+pojoClass);
        Class<T> usa = pojoClass;
        Field[] fields= usa.getDeclaredFields();//获取到所有属性
        for (Field field : fields) {// --for() begin
            field.getGenericType();//获取到所有属性
            System.out.println(field.getGenericType());
        }
        System.out.println("获取到的所有属性"+usa.getDeclaredFields());
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {//sheet.getLastRowNum()获得sheet总行数
            Row row = sheet.getRow(r);//获得第行数据对象
            if (row == null){
                continue;
            }

            Object obj =  pojoClass.newInstance();//创建对象

            if( row.getCell(0).getCellType() !=1){// 将区域编号的单元格中的内容当做字符串处理
                // throw new MyException("导入失败(第"+(r+1)+"行,姓名请设为文本格式)");
            }
            Field.setAccessible(fields, true);//私有属性设置可以更改
            int end = row.getLastCellNum();//获取列数
            for (int i=0 ; i<end ;i++){
                Cell cell = row.getCell(i);//获取单元格
                Object value = getCellValue(cell);//获取这个单元格的值
                //判断value是否是Double类型的
                if (value instanceof Double){
                     double d = (double) value;
                    //使用NumberFormat，格式化输出
                    NumberFormat nf = NumberFormat.getInstance();
                    String s = nf.format(d);
                    if (s.indexOf(",") >= 0) {
                    //这种方法对于自动加".0"的数字可直接解决
                    // 但如果是科学计数法的数字就转换成了带逗号的，例如：12345678912345的科学计数法是1.23457E+13，经过这个格式化后就变成了字符串“12,345,678,912,345”，这也并不是想要的结果，所以要将逗号去掉
                        s = s.replace(",", "");
                    }

                    //把double类型的强转为int类型的
                   // Integer phoneNum = new Double((Double) value).intValue();
                    //String phoneNum2 = String.valueOf(value);
                    //Object dlng =value;
                    //然后再set给对应的属性
                   // fields[i+1].set(obj,dlng);
                    fields[i+1].set(obj,s);
                }else {
                    fields[i + 1].set(obj, value);//把获取到的值再set给对应的属性
                }
                System.out.println(fields[i]);

            }
            userList.add((T) obj);//把类里的属性值给list集合
        }
        System.out.println("查看值是否赋在里面了"+pojoClass.getName());
        return userList;
    }



/*
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if(!HSSFDateUtil.isCellDateFormatted(cell)) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (cell.getCellType()==0) {
                    System.out.println("是数值类型的");
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        //用于转化为日期格式
                 *//*   Date d = cell.getDateCellValue();
                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = formater.format(d);*//*


              //  Date currentTime = cell.getDateCellValue();
               // SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
               //  cellValue = formatter.format(currentTime);

                  *//*  Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(date);
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellValue =sdf.format(date);
                    sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    cellValue =sdf.format(date);*//*

                       // cellValue = String.valueOf(cell.getStringCellValue());

                            //定义一个新的字符串
                            String anString="";
                            //设置日期格式
                            anString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(cell.getDateCellValue());
                        Date cellValue1;
                            cellValue1 =  new Date(anString);


                    }
                }
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
           *//*case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;*//*
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return replaceBlank(cellValue);
    }*/
  private static Object getCellValue(Cell cell) throws ParseException {
      Object result = "";
      if (cell != null) {
          switch (cell.getCellTypeEnum()) {
              case STRING:
                  result = cell.getStringCellValue();
                  break;
              case NUMERIC:
                  result = cell.getNumericCellValue();
                  if(HSSFDateUtil.isCellDateFormatted(cell)){
                      //用于转化为日期格式
                      String anString="";
                      //设置日期格式
                      anString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(cell.getDateCellValue());
                      DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                     return sdf.parse(anString);
                  }
                  break;
              case BOOLEAN:
                  result = cell.getBooleanCellValue();
                  break;
              case FORMULA:
                  result = cell.getCellFormula();
                  break;
              case ERROR:
                  result = cell.getErrorCellValue();
                  break;
              case BLANK:
                  result = "";
                  break;
              default:
                  result = cell.toString();
                  break;
          }
      }
      //return replaceBlank(result);
      return result;
  }

    public static String replaceBlank(String str) {
        if (str != null) {
            str = str.replace("\n","");
            str = str.replace("\t","");
            str = str.replace("\\s","");
            str = str.replace("\r","");
        }
        return str.trim();
    }



    public static void ExportWithResponse(String sheetName, String titleName,
                                          String fileName, int columnNumber, int[] columnWidth,
                                          String[] columnName, List<User> dataList,
                                          HttpServletResponse response) throws Exception {
        if (columnNumber == columnWidth.length&& columnWidth.length == columnName.length) {
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            // sheet.setDefaultColumnWidth(15); //统一设置列宽
            for (int i = 0; i < columnNumber; i++)
            {
                for (int j = 0; j <= i; j++)
                {
                    if (i == j)
                    {
                        sheet.setColumnWidth(i, columnWidth[j] * 256); // 单独设置每列的宽
                    }
                }
            }
            // 创建第0行 也就是标题
            HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(50);// 设备标题的高度
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont1.setFontName("黑体"); // 设置字体类型
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
            style2.setFont(headerFont1); // 为标题样式设置字体样式

            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    columnNumber - 1)); // 合并列标题
            cell1.setCellValue(titleName); // 设置值标题
            cell1.setCellStyle(style2); // 设置标题样式

            // 创建第1行 也就是表头
            HSSFRow row = sheet.createRow((int) 1);
            row.setHeightInPoints(37);// 设置表头高度

            // 第四步，创建表头单元格样式 以及表头的字体样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// 设置自动换行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont.setFontName("黑体"); // 设置字体类型
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
            style.setFont(headerFont); // 为标题样式设置字体样式


            String[] headers = { "id","姓名", "登录密码"};
            for(int i=0;i<headers.length;i++){
                HSSFCell cell = row.createCell(i);//获取列
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellStyle(style2); // 设置标题样式
                cell.setCellValue(text);
            }

            // 第五步，创建单元格，并设置值

            int rowNum = 1;
            for (User teacher : dataList) {
                HSSFRow row2 = sheet.createRow(rowNum);
                row2.createCell(0).setCellValue(teacher.getName());
                row2.createCell(1).setCellValue(teacher.getPassword());
                rowNum++;
            }
/*
            // 第四.一步，创建表头的列
            for (int i = 0; i < columnNumber; i++)
            {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName[i]);
                cell.setCellStyle(style);
            }


           // 第五步，创建单元格，并设置值
            for (int i = 0; i < dataList.length; i++)
            {
                row = sheet.createRow((int) i + 2);
                // 为数据内容设置特点新单元格样式1 自动换行 上下居中
                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// 设置自动换行
                zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

                // 设置边框
                zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);

                // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中
                HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
                zidonghuanhang2.setWrapText(true);// 设置自动换行
                zidonghuanhang2
                        .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式
                zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中

                // 设置边框
                zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);
                HSSFCell datacell = null;
                for (int j = 0; j < columnNumber; j++)
                {
                    datacell = row.createCell(j);
                    datacell.setCellValue(dataList[i][j]);
                    datacell.setCellStyle(zidonghuanhang2);
                }
            }*/

            // 第六步，将文件存到浏览器设置的下载位置

            String filename = fileName + ".xls";
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));


            OutputStream os = response.getOutputStream();
            try {
                wb.write(os);// 将数据写出去
                String str = "导出" + fileName + "成功！";
                System.out.println(str);
            } catch (Exception e) {
                e.printStackTrace();
                String str1 = "导出" + fileName + "失败！";
                System.out.println(str1);
            } finally {
                os.close();
            }

        } else {
            System.out.println("列数目长度名称三个数组长度要一致");
        }

    }


    public static void ExportNoResponse(String sheetName, String titleName,
                                 String fileName, int columnNumber, int[] columnWidth,
                                 String[] columnName, List<User> dataList) throws Exception {
        if (columnNumber == columnWidth.length&& columnWidth.length == columnName.length) {
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            // sheet.setDefaultColumnWidth(15); //统一设置列宽
            for (int i = 0; i < columnNumber; i++)
            {
                for (int j = 0; j <= i; j++)
                {
                    if (i == j)
                    {
                        sheet.setColumnWidth(i, columnWidth[j] * 256); // 单独设置每列的宽
                    }
                }
            }
            // 创建第0行 也就是标题
            HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(50);// 设备标题的高度
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont1.setFontName("黑体"); // 设置字体类型
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
            style2.setFont(headerFont1); // 为标题样式设置字体样式

            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    columnNumber - 1)); // 合并第0到第17列
            cell1.setCellValue(titleName); // 设置值标题
            cell1.setCellStyle(style2); // 设置标题样式

            // 创建第1行 也就是表头
            HSSFRow row = sheet.createRow((int) 1);
            row.setHeightInPoints(37);// 设置表头高度

            // 第四步，创建表头单元格样式 以及表头的字体样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// 设置自动换行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont.setFontName("黑体"); // 设置字体类型
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
            style.setFont(headerFont); // 为标题样式设置字体样式

            // 第四.一步，创建表头的列
          /*  for (int i = 0; i < columnNumber; i++)
            {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName[i]);
                cell.setCellStyle(style);
            }*/


            String[] headers = { "id","姓名", "登录密码"};
            for(int i=0;i<headers.length;i++){
                HSSFCell cell = row.createCell(i);//获取列
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellStyle(style2); // 设置标题样式
                cell.setCellValue(text);
            }

            // 第五步，创建单元格，并设置值

            int rowNum = 1;
            for (User teacher : dataList) {
                HSSFRow row2 = sheet.createRow(rowNum);
                row2.createCell(0).setCellValue(teacher.getName());
                row2.createCell(1).setCellValue(teacher.getPassword());
                rowNum++;
            }

            /*


            for (int i = 0; i < dataList.length; i++)
            {
                row = sheet.createRow((int) i + 2);
                // 为数据内容设置特点新单元格样式1 自动换行 上下居中
                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// 设置自动换行
                zidonghuanhang
                        .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

                // 设置边框
                zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);

                // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中
                HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
                zidonghuanhang2.setWrapText(true);// 设置自动换行
                zidonghuanhang2
                        .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式
                zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中

                // 设置边框
                zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);
                HSSFCell datacell = null;
                for (int j = 0; j < columnNumber; j++)
                {
                    datacell = row.createCell(j);
                    datacell.setCellValue(dataList[i][j]);
                    datacell.setCellStyle(zidonghuanhang2);
                }
            }*/

            // 第六步，将文件存到指定位置
            try {
                //FileOutputStream fout = new FileOutputStream("D:students.xls");
                FileOutputStream fout = new FileOutputStream("D:\\students.xls");
                wb.write(fout);
                String str = "导出" + fileName + "成功！";
                System.out.println(str);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
                String str1 = "导出" + fileName + "失败！";
                System.out.println(str1);
            }
        } else {
            System.out.println("列数目长度名称三个数组长度要一致");
        }

    }



    }
