package org.clm.javaproject.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ParseExcel {
	
	//{'mess_info_id':'','source_column_name':'','sdm_var_name_en':'',
	//'sdm_var_name_cn':'','sdm_describe':'','sdm_var_type':'','sdm_is_send':vm.isFlag.Shi}
	public static void main(String[] args) {
		//excel文件路径
        String excelPath = "E:\\testtable.xlsx";

        try {
            //String encoding = "GBK";
            File excel = new File(excelPath);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.out.println("文件类型错误!");
                    return;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0
                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();

                JSONArray final_array = new JSONArray();
                String objstr = "{'mess_info_id':'','source_column_name':'','sdm_var_name_en':'',"
                				+ "'sdm_var_name_cn':'','sdm_describe':'','sdm_var_type':'',"
                				+ "'sdm_is_send':''}";
                
                //获取第一行表头
                Row title_row = sheet.getRow(0);
                /*for(Cell cell : title_row) {
					System.out.println(cell.toString());
				}*/
                
                /*JSONObject obj_example = (JSONObject)JSON.parse(objstr);
                Object[] obj_key_array = obj_example.keySet().toArray();
                for(String str : obj_example.keySet()) {
					System.out.println(str);
				}*/
                
                
                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    //System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        JSONObject obj = new JSONObject();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                        	Cell title_cell = title_row.getCell(cIndex);
                        	Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                            	if(title_cell.toString().equals("序号")){
                            		obj.put("mess_info_id", cell.toString());
                            	}else if(title_cell.toString().equals("源变量英文名")){
                            		obj.put("source_column_name", cell.toString());
                            	}else if(title_cell.toString().equals("变量名（英文）")){
                            		obj.put("sdm_var_name_en", cell.toString());
                            	}else if(title_cell.toString().equals("变量名（中文）")){
                            		obj.put("sdm_var_name_cn", cell.toString());
                            	}else if(title_cell.toString().equals("含义")){
                            		obj.put("sdm_describe", cell.toString());
                            	}else if(title_cell.toString().equals("类型")){
                            		obj.put("sdm_var_type", cell.toString());
                            	}else if(title_cell.toString().equals("是否发送")){
                            		obj.put("sdm_is_send", cell.toString());
                            	}
                            	
                            }
                        }
                        final_array.add(obj);
                    }
                }
                System.out.println(final_array.toString());
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
