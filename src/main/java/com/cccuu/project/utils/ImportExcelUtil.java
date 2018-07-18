package com.cccuu.project.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @Package com.cccuu.project.utils
 * @Author ke
 * @DATE 2017/9/18.
 */
public class ImportExcelUtil {

    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception{
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in,fileName);
        if(null == work){
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if(sheet==null){continue;}

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum()+1; j < sheet.getLastRowNum()+1; j++) {
                row = sheet.getRow(j);
                if(row==null){continue;}

//                遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    if(cell==null||cell.equals("")){
                        li.add("");
                        continue;
                    }
                    li.add(this.getCellValue(cell));
                }
                list.add(li);
            }
        }
        work.close();
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003L.equals(fileType)){
            wb = new HSSFWorkbook(inStr);  //2003-
        }else if(excel2007U.equals(fileType)){
            wb = new XSSFWorkbook(inStr);  //2007+
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     */
//    public  Object getCellValue(Cell cell){
//        Object value = null;
//        DecimalFormat df = new DecimalFormat("0.00");  //格式化number String字符
//        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
//        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字
//
//        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_STRING:
//                value = cell.getRichStringCellValue().getString();
//                break;
//            case Cell.CELL_TYPE_NUMERIC:
//                if("General".equals(cell.getCellStyle().getDataFormatString())){
//                value = df.format(cell.getNumericCellValue());
//            }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
//                value = sdf.format(cell.getDateCellValue());
//            }else{
//                value = df2.format(cell.getNumericCellValue());
//            }
//            break;
//            case Cell.CELL_TYPE_BOOLEAN:
//                value = cell.getBooleanCellValue();
//                break;
//            case Cell.CELL_TYPE_BLANK:
//                value = "";
//                break;
//            default:
//                break;
//        }
//        return value;
//    }


    //解决excel类型问题，获得数值
    public  String getCellValue(Cell cell) {
        String value = "";
        if(null==cell){
            return value;
        }
        switch (cell.getCellType()) {
//            DecimalFormat df1 = new DecimalFormat("0.00");
            //数值型
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.format(date);
                }else {// 纯数字
                    BigDecimal big=new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    //解决1234.0  去掉后面的.0
                    if(null!=value&&!"".equals(value.trim())){
                        String[] item = value.split("[.]");
                        if(1<item.length&&"0".equals(item[1])){
                            value=item[0];
                        }
                    }
                }
                break;
            //字符串类型
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case Cell.CELL_TYPE_FORMULA:
                //读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue().toString();
                }
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                value = " "+ cell.getBooleanCellValue();
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            // 故障
            case Cell.CELL_TYPE_ERROR:
                value = "";
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if("null".endsWith(value.trim())){
            value="";
        }
        return value;
    }
}
