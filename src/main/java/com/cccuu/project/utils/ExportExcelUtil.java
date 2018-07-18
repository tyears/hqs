package com.cccuu.project.utils;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.cccuu.project.utils
 * @Author ke
 * @DATE 2017/9/18.
 */
public class ExportExcelUtil {

    /**
     * 创建工作簿对象
     *
     * 工作表名称，工作表标题，工作表数据最好能够对应起来
     * 比如三个不同或相同的工作表名称，三组不同或相同的工作表标题，三组不同或相同的工作表数据
     *
     * 注意：
     * 需要为每个工作表指定工作表名称，工作表标题，工作表数据
     * 如果工作表的数目大于工作表数据的集合，那么首先会根据顺序一一创建对应的工作表名称和数据集合，然后创建的工作表里面是没有数据的
     * 如果工作表的数目小于工作表数据的集合，那么多余的数据将不会写入工作表中
     *
     * @param sheetName 工作表名称的数组
     * @param title 每个工作表名称的数组集合
     * @param data 每个工作表数据的集合的集合
     * @return Workbook工作簿
     * @throws FileNotFoundException 文件不存在异常
     * @throws IOException IO异常
     */
    public static Workbook getWorkBook(String[] sheetName,List<? extends Object[]> title,List<? extends List<? extends Object[]>> data) throws FileNotFoundException, IOException {

        //创建工作簿，支持2007及以后的文档格式
        Workbook wb = new XSSFWorkbook();
        //创建一个工作表sheet
        Sheet sheet = null;
        //申明行
        Row row = null;
        //申明单元格
        Cell cell = null;
        //单元格样式
        CellStyle titleStyle=wb.createCellStyle();
        CellStyle cellStyle=wb.createCellStyle();
        //字体样式
        Font font=wb.createFont();
        //粗体
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        titleStyle.setFont(font);
        //水平居中
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //垂直居中
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //水平居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //标题数据
        Object[] title_temp=null;

        //行数据
        Object[] rowData=null;

        //工作表数据
        List<? extends Object[]> sheetData=null;

        //遍历sheet
        for(int sheetNumber=0;sheetNumber<sheetName.length;sheetNumber++){
            //创建工作表
            sheet=wb.createSheet();
            //设置工作表名称
            wb.setSheetName(sheetNumber, sheetName[sheetNumber]);
            //设置标题
            title_temp=title.get(sheetNumber);
            row=sheet.createRow(0);

            //写入标题
            for(int i=0;i<title_temp.length;i++){
                cell=row.createCell(i);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(title_temp[i].toString());
            }

            try {
                sheetData=data.get(sheetNumber);
            } catch (Exception e) {
                continue;
            }
            //写入行数据
            for(int rowNumber=0;rowNumber<sheetData.size();rowNumber++){
                //如果没有标题栏，起始行就是0，如果有标题栏，行号就应该为1
                row=sheet.createRow(title_temp==null?rowNumber:(rowNumber+1));
                rowData=sheetData.get(rowNumber);
                for(int columnNumber=0;columnNumber<rowData.length;columnNumber++){
                    cell=row.createCell(columnNumber);
                    cell.setCellStyle(cellStyle);
                    if(rowData[columnNumber]!=null){
                        cell.setCellValue(rowData[columnNumber].toString());
                    }else {
                        cell.setCellValue("");
                    }
                }
            }
        }
        return wb;
    }


    /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息
         * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }
}
