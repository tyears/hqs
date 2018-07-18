package com.cccuu.project.utils;

import jxl.Workbook;
import jxl.write.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author YK 生成Execl
 */
public class ExeclUtil {

	/**
	 * 将List<Map>格式的数据集转换为execl
	 * @param fileName 表名
	 * @param response
	 * @param list
	 */
	@SuppressWarnings({ "rawtypes"})
	public static void listExecl(String fileName,HttpServletResponse response, List<Map<String,Object>> list){
		OutputStream os=null;
		WritableWorkbook wwb= null;
		WritableSheet sheet= null;
		try {
			os=response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("GB2312"),"ISO8859-1")+".xls");
			response.setContentType("application/msexcel");
			//创建工作薄
			wwb= Workbook.createWorkbook(os);
			//创建新的一页
			sheet=wwb.createSheet("First sheet", 0);
			if (list == null || list.size() == 0) {
				throw new RuntimeException("传入的list为null或空");
			}
			Map keyMap = list.get(0);

			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat titleFormat = new WritableCellFormat(wfont);

			Iterator iterator = keyMap.keySet().iterator();
			String[] keys = new String[keyMap.keySet().size()];
			int i=0;
			while (iterator.hasNext()) {
				String key = ConvertUtil.obj2Str(iterator.next());
				Label cell = new Label(i,0,key,titleFormat);
				sheet.addCell(cell);
				keys[i] = key;
				i++;
			}
			int rowIndex = 1;
			int columnIndex = 0;

			for (Map map : list) {
				for (String key : keys) {
					Label cell = new Label(columnIndex,rowIndex,ConvertUtil.obj2Str(map.get(key)));
					sheet.addCell(cell);
					columnIndex ++ ;
				}
				columnIndex = 0;
				rowIndex ++ ;
			}
			//把创建的内容写入输出流，并关闭
			os.flush();
			wwb.write();
			wwb.close();
			os.close();
		}catch(Exception e){
			System.out.println(e.getMessage());		//未知异常不影响数据，简单处理（不抛出）
		}
	}


}

