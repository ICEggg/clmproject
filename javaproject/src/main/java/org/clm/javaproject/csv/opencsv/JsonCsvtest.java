package org.clm.javaproject.csv.opencsv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.CDL;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;

public class JsonCsvtest {
	public static void main(String[] args) {
		org.json.JSONArray array = new org.json.JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("id", 1);
		obj1.put("name", "aaa");
		obj1.put("age", 10);
		
		JSONObject obj2 = new JSONObject();
		obj2.put("id", 2);
		obj2.put("name", "bbb");
		obj2.put("age", 20);
		
		array.put(obj1);
		array.put(obj2);
		
		System.out.println(CDL.toString(array));
		
		/*String url = "D:\\testdata\\opencsvtestfile\\jsontocsv";
		File file = new File(url);
		try {
			List<String[]> list = new ArrayList<>();
			String[] array1 = new String[]{"1","aaa","10"};
			String[] array2 = new String[]{"2","bbb","20"};
			list.add(array1);
			list.add(array2);
			
			byte[] bytes = list.toString().getBytes();
			System.out.println(list.toString().getBytes());
			
			CSVWriter writer = new CSVWriter(new FileWriter(url));
			writer.writeAll(list);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
	}
}
