package com.education.domain;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class Lise {

    public static final String DELIMITER = ".";
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


    public static <T> boolean generateSheet(List<T> data, String saveFilePath, String sheetName, Map<String, String[]> maps) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow row = sheet.createRow(0);
        FileOutputStream out = new FileOutputStream(saveFilePath);
        // 遍历集合数据，产生数据行
        Iterator<T> it = data.iterator();
        int index = 0;
        boolean flag = true;
        try {
            while (it.hasNext()) {
                row = sheet.createRow(index++);
                T t = (T) it.next();
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (field.toString().contains("static")) {
                        continue;
                    }
                    HSSFCell cell = row.createCell((short) i);

                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    for (Map.Entry<String, String[]> map : maps.entrySet()) {
                        if (map.getKey().equals(value)) {
                            // 设置第一列的1-10行为下拉列表
                            CellRangeAddressList regions = new CellRangeAddressList(1, 65535, i, i);
                            // 创建下拉列表数据
                            DVConstraint constraint = DVConstraint.createExplicitListConstraint(map.getValue());
                            // 绑定
                            HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
                            sheet.addValidationData(dataValidation);
                        }
                    }
                    // 判断值的类型后进行强制类型转换
//                    String textValue = null;
//                    if (value instanceof Date) {
//                        Date date = (Date) value;
//                        textValue = sdf.format(date);
//                    } else {
//                        // 其它数据类型都当作字符串简单处理
//                        if (value == null) {
//                            value = "";
//                        }
//                        textValue = value.toString();
//                    }
//                    if (textValue != null) {
////                        Pattern p = Pattern.compile("^//d+(//.//d+)?{1}quot;");
////                        Matcher matcher = p.matcher(textValue);
////                        if (matcher.matches()) {
////                            // 是数字当作double处理
////                            cell.setCellValue(Double.parseDouble(textValue));
////                        } else {

                    cell.setCellValue(value.toString());
////                        }
//                    }
                }
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            out.flush();
            wb.write(out);
            out.close();
        }
        return flag;
    }

    public static Class getFileClass(String model) {
        Class clazz = null;
        try {
            clazz = Class.forName(model);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Map<String, String> getExcelTitle(Class clazz) {
        Map<String, String> titleMap = Maps.newLinkedHashMap();
        try {
            Object obj = clazz.newInstance();
            Field[] fs = clazz.getDeclaredFields();
            for (Field f : fs) {
                Annotation annotation = f.getAnnotation(ApiModelProperty.class);
                if (annotation != null) {
                    ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                    String notes = apiModelProperty.notes();
                    if (apiModelProperty.required()) {
                        notes = "*" + notes;
                    }
                    titleMap.put(apiModelProperty.value(), notes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titleMap;
    }

    public static String getSheetName(Class clazz) {
        String sheetName = "Sheet1";
        try {
            Object obj = clazz.newInstance();
            Annotation[] classAnnotation = clazz.getAnnotations();
            for (Annotation cAnnotation : classAnnotation) {
                ApiModel apiModel = (ApiModel) cAnnotation;
                sheetName = apiModel.description();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheetName;
    }

    public static String getFileName(Class clazz) {
        String fileName = "";
        try {
            Object obj = clazz.newInstance();
            Annotation[] classAnnotation = clazz.getAnnotations();
            for (Annotation cAnnotation : classAnnotation) {
                ApiModel apiModel = (ApiModel) cAnnotation;
                fileName = apiModel.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static List<String> getFiltrate(Class clazz) {
        List<String> list = new ArrayList<String>();
        try {
            Object obj = clazz.newInstance();
            Field[] fs = clazz.getDeclaredFields();
            for (Field f : fs) {
                Annotation annotation = f.getAnnotation(ApiModelProperty.class);
                if (annotation != null) {
                    ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                    if (apiModelProperty.required()) {
                        list.add(apiModelProperty.value());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static CellStyle setTitleStyle(HSSFWorkbook wb, HSSFCell cell) {
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        if (cell.getStringCellValue().contains("*")) {
            font.setColor(HSSFFont.COLOR_RED);
        }
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        //cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(false);
        cellStyle.setLocked(true);
        return cellStyle;
    }

    public static CellStyle setContentStyle(HSSFWorkbook wb) {
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(false);
        return cellStyle;
    }

    public static CellStyle getStyle(HSSFWorkbook wb) {
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setColor(HSSFColor.RED.index);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(false);
        return cellStyle;
    }

    public static <T> boolean generateSheet(HttpServletResponse response, String[] title, List<T> data, String fileName, String sheetName) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 遍历集合数据，产生数据行
        OutputStream os = response.getOutputStream();
        response.reset();
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, tenant, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
        int index = 0;
        boolean flag = true;
        try {
            if (title != null) {
//                CellView cellView = new CellView();
//                cellView.setAutosize(true);
                HSSFRow row = sheet.createRow(0);
                for (int i = 0; i < title.length; i++) {
                    HSSFCell cell = row.createCell((short) i);
                    Object value = title[i];
                    cell.setCellValue(value.toString());
                    cell.setCellStyle(setTitleStyle(wb, cell));
                }
            }
            if (data != null) {
                Field[] fields = null;
                int i = 1;
                for (Object obj : data) {
                    HSSFRow row = sheet.createRow(i);
                    fields = obj.getClass().getDeclaredFields();
                    int j = 0;
                    for (Field v : fields) {
                        HSSFCell cell = row.createCell((short) j);
                        v.setAccessible(true);
                        Object value = v.get(obj);
                        if (value == null) {
                            value = "";
                        }
                        cell.setCellValue(value.toString());
                        cell.setCellStyle(setContentStyle(wb));
                        j++;
                    }
                    i++;
                }
                sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, title.length - 1));
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            os.flush();
            wb.write(os);
            os.close();
        }
        return flag;
    }

    public static <T> boolean generateSheet(HttpServletResponse response, String[] title, List<T> data, Class clazz) {
        boolean flag = false;
        String sheetName = getSheetName(clazz);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String datetime = sdf.format(new Date());
        String fileName = getFileName(clazz) + datetime + DELIMITER + OFFICE_EXCEL_2003_POSTFIX;
        List<String> filtrateList = getFiltrate(clazz);
        try {
            flag = generateSheet(response, title, data, fileName, sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
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

    public static <T> boolean generateSheet(HttpServletResponse response, String model, Map<String, List<String>> maps) throws IOException {
        Class clazz = null;
        try {
            clazz = Class.forName(model);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(getSheetName(clazz));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String datetime = sdf.format(new Date());
        String fileName = getFileName(clazz) + datetime + DELIMITER + OFFICE_EXCEL_2003_POSTFIX;
        // 遍历集合数据，产生数据行
        OutputStream os = response.getOutputStream();
        response.reset();
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, tenant, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/vnd.ms-excel");
        boolean flag = true;
        try {
            HSSFRow row = sheet.createRow(0);
            HSSFPatriarch p = sheet.createDrawingPatriarch();
            Field[] fs = clazz.getDeclaredFields();
            int i = 0;
            for (Field f : fs) {
                Annotation annotation = f.getAnnotation(ApiModelProperty.class);
                if (annotation != null) {
                    ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                    String value = apiModelProperty.value();
                    if (apiModelProperty.required()) {
                        value = "*" + value;
                    }
                    HSSFCell cell = row.createCell((short) i);
                    if (apiModelProperty.example() != null && !apiModelProperty.example().equals("")) {
                        //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
                        //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
                        HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 2, 2, (short) 4, 5));
                        comment.setString(new HSSFRichTextString(apiModelProperty.example()));
                        cell.setCellComment(comment);

                    }
                    cell.setCellValue(value);
                    sheet.setColumnWidth(i, value.getBytes().length * 2 * 256);
                    cell.setCellStyle(setTitleStyle(wb, cell));
                    if (f.getType().getName().equals("java.util.Date")) {
                        String dataFormat = "yyyy-MM-dd";
                        if (apiModelProperty.notes() != null && !apiModelProperty.notes().equals("")) {
                            dataFormat = apiModelProperty.notes();
                        }
                        HSSFCellStyle cellStyle = wb.createCellStyle();
                        HSSFDataFormat format= wb.createDataFormat();
                        cellStyle.setDataFormat(format.getFormat(dataFormat));
                        for(int j=1;j<=5000;j++){
                            HSSFCell cell2 = sheet.createRow(j).createCell((short) i);
                            cell2.setCellStyle(cellStyle);
                        }
                    }
                }
                i++;
            }
            setDataValidation(wb, sheet, fs, maps);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            os.flush();
            wb.write(os);
            os.close();
        }
        return flag;
    }

    private static void setDataValidation(HSSFWorkbook wb, HSSFSheet sheet, Field[] fs, Map<String, List<String>> maps) {
        String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int index = 0;
//        int j = 0;
        for (Field f : fs) {
            Annotation annotation = f.getAnnotation(ApiModelProperty.class);
            if (annotation != null) {
                ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                if (maps != null) {
                    int j = 0;
                    for (Map.Entry<String, List<String>> map : maps.entrySet()) {
                        if (map.getKey().equals(apiModelProperty.value())) {
                            List<String> list = map.getValue();
//                            list.add(0,apiModelProperty.value());
                            String[] values = list.toArray(new String[list.size()]);
                            if(values.length<5){
                                DataValidationHelper helper = sheet.getDataValidationHelper();
                                DataValidationConstraint constraint = helper.createExplicitListConstraint(values);
                                constraint.setExplicitListValues(values);
                                CellRangeAddressList regions = new CellRangeAddressList( 1, 5000, index, index);
                                DataValidation data_validation = helper.createValidation(constraint, regions);
                                sheet.addValidationData(data_validation);
                            } else { //255以上的下拉，即下拉列表元素很多的情况
                                String sheetName = apiModelProperty.value();
                                HSSFSheet sheet2 = wb.createSheet(sheetName);
                                HSSFRow row = sheet2.createRow(0);
                                String strFormula = sheetName + "!$" + arr[j] + "$1:$" + arr[j] + "$5000";
                                CellRangeAddressList regions = new CellRangeAddressList(1, 5000, index, index);
                                DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
                                HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
                                sheet.addValidationData(dataValidation);
                                for (int k = 0; k < values.length; k++) {
                                    if (j == 0) {
                                        row = sheet2.createRow(k);
                                        row.createCell(j).setCellValue(values[k]);
                                    } else {
                                        int rowCount = sheet2.getLastRowNum();
                                        if (k <= rowCount) {
                                            sheet2.getRow(k).createCell(j).setCellValue(values[k]);
                                        } else {
                                            sheet2.createRow(k).createCell(j).setCellValue(values[k]);
                                        }
                                    }
                                }
                                j++;
                            }
                        }
                    }
                }
            }
            index++;
        }
    }

    public static List<Object> getDatasByrc(List<Map<String, String>> list, String model) {
        if (list != null && list.size() > 0) {
            List<Object> results = new ArrayList<Object>();
            try {
                Class clazz = Class.forName(model);
                Field[] fs = clazz.getDeclaredFields();
                for (Map<String, String> objMap : list) {
                    if (objMap != null && objMap.size() > 0 && fs.length >= objMap.size()) {
                        Object obj = clazz.newInstance();
                        int i = 0;
                        for (Field f : fs) {
                            Annotation annotation = f.getAnnotation(ApiModelProperty.class);
                            if (annotation != null) {
                                ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                                PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
                                Method wM = pd.getWriteMethod();
                                for (Map.Entry<String, String> entry : objMap.entrySet()) {
                                    if (entry.getKey().equals(i + "")) {
                                        Object objValue = entry.getValue();
                                        if (f.getType().getName().equals("java.util.Date")
                                                && apiModelProperty.notes() != null && !apiModelProperty.notes().equals("")) {
                                            SimpleDateFormat sdf = new SimpleDateFormat(apiModelProperty.notes());
                                            if (entry.getValue() != null && !entry.getValue().equals("")) {
                                                objValue = sdf.parse(entry.getValue());
                                            }
                                        }
                                        if (f.getType().getName().equals("java.lang.Integer")) {
                                            if (entry.getValue() != null && !entry.getValue().equals("")) {
                                                objValue = Integer.parseInt(entry.getValue());
                                            }
                                        }
                                        if (objValue != null && !objValue.equals("")) {
                                            wM.invoke(obj, objValue);
                                        }
                                    }
                                }
                            }
                            i++;
                        }
                        results.add(obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return results;
        }
        return null;
    }

    public static List<Map<String, String>> readXls(MultipartFile file) {
        String filePath = file.getOriginalFilename();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = null;
            if (filePath.endsWith(OFFICE_EXCEL_2003_POSTFIX)) {
                workbook = new HSSFWorkbook(is);
            } else if (filePath.endsWith(OFFICE_EXCEL_2010_POSTFIX)) {
                workbook = new XSSFWorkbook(is);
            } else {
                return null;
            }
            Sheet sheet = workbook.getSheetAt(0);
            for(int i = sheet.getFirstRowNum();i <= sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Map<String, String> rowData = new LinkedHashMap(row.getPhysicalNumberOfCells());
                int end = row.getLastCellNum();
                for (int j = 0; j < end; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        Object obj = getCellValue(cell);
                        if(obj!=null&&!obj.equals("")) {
                            rowData.put(j + "", obj.toString());
                        }
                    }
                }
                if (rowData.size() > 0) {
                    rowData.entrySet();
                    list.add(rowData);
                }
            }
            workbook.close();
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    public static List<Map<String, String>> readXls(MultipartFile file,String model) {
        String filePath = file.getOriginalFilename();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = null;
            if (filePath.endsWith(OFFICE_EXCEL_2003_POSTFIX)) {
                workbook = new HSSFWorkbook(is);
            } else if (filePath.endsWith(OFFICE_EXCEL_2010_POSTFIX)) {
                workbook = new XSSFWorkbook(is);
            } else {
                return null;
            }
            Sheet sheet = workbook.getSheetAt(0);
            Class clazz = null;
            try {
                clazz = Class.forName(model);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Field[] fs = clazz.getDeclaredFields();
            Row title = sheet.getRow(0);
            if(title.getLastCellNum()==fs.length) {
                int i = 0;
                for (Field f : fs) {
                    Annotation annotation = f.getAnnotation(ApiModelProperty.class);
                    if (annotation != null) {
                        ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                        String value = apiModelProperty.value();
                        if(apiModelProperty.required()){
                            value = "*"+value;
                        }
                        if(value.equals(title.getCell(i).getStringCellValue())){
                            i++;
                        }else{
                            return null;
                        }
                    }
                }
            }else {
                return null;
            }
            for(int i = sheet.getFirstRowNum()+1;i <= sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                if(row!=null) {
                    Map<String, String> rowData = new LinkedHashMap(row.getPhysicalNumberOfCells());
                    int end = row.getLastCellNum();
                    for (int j = 0; j < end; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            Object obj = getCellValue(cell);
                            if (obj != null && !obj.equals("")) {
                                rowData.put(j + "", obj.toString());
                            }
                        }
                    }
                    if (rowData.size() > 0) {
                        rowData.entrySet();
                        list.add(rowData);
                    }
                }
            }
            workbook.close();
        } catch (Exception e) {
            return null;
        }
        return list;
    }

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
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    //用于转化为日期格式
                    Date d = cell.getDateCellValue();
                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = formater.format(d);
                }
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
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
    }

    public static <T> boolean generateSheet(HttpServletResponse response, List<T> data, String model) throws IOException {
        Class clazz = null;
        try {
            clazz = Class.forName(model);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(getSheetName(clazz));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String datetime = sdf.format(new Date());
        String fileName = getFileName(clazz) + datetime + DELIMITER + OFFICE_EXCEL_2003_POSTFIX;
        // 遍历集合数据，产生数据行
        OutputStream os = response.getOutputStream();
        response.reset();
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, tenant, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/vnd.ms-excel");
        boolean flag = true;
        try {
            HSSFRow titleRow = sheet.createRow(0);
            HSSFPatriarch p = sheet.createDrawingPatriarch();
            Field[] fs = clazz.getDeclaredFields();
            int i = 0;
            for (Field f : fs) {
                Annotation annotation = f.getAnnotation(ApiModelProperty.class);
                if (annotation != null) {
                    ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                    String value = apiModelProperty.value();
                    HSSFCell cell = titleRow.createCell((short) i);
                    cell.setCellValue(value);
                    sheet.setColumnWidth(i, value.getBytes().length * 2 * 256);
                    cell.setCellStyle(setTitleStyle(wb, cell));
                }
                i++;
            }
            if (data != null) {
                Field[] fields = null;
                int index = 1;
                for (Object obj : data) {
                    HSSFRow row = sheet.createRow(index);
                    fields = obj.getClass().getDeclaredFields();
                    int j = 0;
                    for (Field v : fields) {
                        HSSFCell cell = row.createCell((short) j);
                        v.setAccessible(true);
                        Object value = v.get(obj);
                        if (value == null) {
                            value = "";
                        }
                        if (v.getType().getName().equals("java.util.Date")) {
                            DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                            value= dataFormat.format(value);
                        }
                        cell.setCellValue(value.toString());
                        cell.setCellStyle(setContentStyle(wb));
                        j++;
                    }
                    index++;
                }
                sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, i - 1));
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            os.flush();
            wb.write(os);
            os.close();
        }
        return flag;
    }

    public static List<Object> getDatasByQuestion(MultipartFile file) {
        String filePath = file.getOriginalFilename();
        try {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            InputStream is = file.getInputStream();
            Workbook workbook = null;
            if (filePath.endsWith(OFFICE_EXCEL_2003_POSTFIX)) {
                workbook = new HSSFWorkbook(is);
            } else if (filePath.endsWith(OFFICE_EXCEL_2010_POSTFIX)) {
                workbook = new XSSFWorkbook(is);
            } else {
                return null;
            }
            Sheet sheet = workbook.getSheetAt(0);
            for(int i = sheet.getFirstRowNum();i <= sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Map<String, String> rowData = new LinkedHashMap(row.getPhysicalNumberOfCells());
                int end = row.getLastCellNum();
                for (int j = 0; j < end; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        Object obj = getCellValue(cell);
                        if(obj!=null&&!obj.equals("")) {
                            rowData.put(j + "", obj.toString());
                        }
                    }
                }
                if (rowData.size() > 0) {
                    rowData.entrySet();
                    list.add(rowData);
                }
            }
            workbook.close();
            if (list != null && list.size() > 0) {
                List<Object> results = new ArrayList<Object>();
                try {
                    Class clazz = Class.forName("com.sintecho.chiron.model.resourceCenter.QuestionTopicWithOptionsModel");
                    Field[] fs = clazz.getDeclaredFields();
                    for (Map<String, String> objMap : list) {
                        if (objMap != null && objMap.size() > 0 && fs.length >= objMap.size()) {
                            Object obj = clazz.newInstance();
                            int i = 0;
                            for (Field f : fs) {
                                Annotation annotation = f.getAnnotation(ApiModelProperty.class);
                                if (annotation != null) {
                                    ApiModelProperty apiModelProperty = (ApiModelProperty) annotation;
                                    PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
                                    Method wM = pd.getWriteMethod();
                                    for (Map.Entry<String, String> entry : objMap.entrySet()) {
                                        Object objValue = entry.getValue();
                                        if (f.getType().getName().equals("java.util.Date")
                                                && apiModelProperty.notes() != null && !apiModelProperty.notes().equals("")) {
                                            SimpleDateFormat sdf = new SimpleDateFormat(apiModelProperty.notes());
                                            if (entry.getValue() != null && !entry.getValue().equals("")) {
                                                objValue = sdf.parse(entry.getValue());
                                            }
                                        }
                                        if (f.getType().getName().equals("java.lang.Integer")) {
                                            if (entry.getValue() != null && !entry.getValue().equals("")) {
                                                objValue = Integer.parseInt(entry.getValue());
                                            }
                                        }
                                        if (objValue != null && !objValue.equals("")) {
                                            wM.invoke(obj, objValue);
                                        }
                                    }
                                }
                                i++;
                            }
                            results.add(obj);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return results;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static boolean isLetterDigitOrChinese(String code,String name,String englishName,String description) {
        boolean flag = false;
        String codeRegex = "^[0-9a-zA-Z]{1,30}+$";
        String nameRegex = "^[\u4e00-\u9fa5a-zA-Z0-9]{1,30}+$";
        if(code.matches(codeRegex)&&name.matches(nameRegex)){
            flag = true;
        }
        if(flag&&englishName!=null&&!englishName.trim().equals("")){
            String englishNameRegex = "^[A-Za-z]{1,100}+$";
            flag = englishName.matches(englishNameRegex);
        }
        if(flag&&description!=null&&!description.trim().equals("")&&description.length() > 500){
            flag = false;
        }
        return flag;
    }

    public static boolean isLetterDigitOrChineseUser(String code,String name,String description) {
        boolean flag = false;
        String codeRegex = "^[a-z0-9A-Z]{6,18}+$";
        String nameRegex = "^[a-zA-Z\u4e00-\u9fa5]{1,20}+$";
        if(code.matches(codeRegex)&&name.matches(nameRegex)){
            flag = true;
        }
        if(flag&&description!=null&&!description.trim().equals("")&&description.length() > 200){
            flag = false;
        }
        return flag;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
