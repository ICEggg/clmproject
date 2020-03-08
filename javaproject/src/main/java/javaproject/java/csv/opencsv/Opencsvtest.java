package javaproject.java.csv.opencsv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Opencsvtest {
	private static final String ADDRESS_FILE="D:\\testdata\\opencsvtestfile\\";
	private static String[] zimu_shuzu = {"a","b","c","d","e","f","g","h","i","j","k",
			"l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"}; 
	private static String[] sex_shuzu = {"男","女"};
	private static String[] fuhao_shuzu = {"!","@","#","$","%","^","&","*","(",")",
			"_","+","|","{","}","|"};//16
	
	public static void main(String[] args) throws IOException {
		/*String filename = "personbbb.csv";
		String url = Opencsvtest.ADDRESS_FILE+filename;
		
		Opencsvtest test = new Opencsvtest();
        test.write(url);
        test.read(url);*/
		
		Opencsvtest test = new Opencsvtest();
		test.changetocsv("D:\\testdata\\jf_example\\aaa.txt","D:\\testdata\\jf_example_result\\22out.csv");
	}
	
	//转换成csv
	public void changetocsv(String in,String out) {
		try {
			CSVReader reader = new CSVReader(new FileReader(in),'\t');
			CSVWriter writer = new CSVWriter(new FileWriter(out),',');
			File file = new File(out);
			if(file.exists()) {
				file.delete();
			}
			
			String [] nextLine;
			int i = 1;
			List<String[]> allElements = new ArrayList<String[]>();
			while ((nextLine = reader.readNext()) != null) {
				allElements.add(nextLine);
				if(i>=22) {
					break;
				}
				i++;
			}
			writer.writeAll(allElements);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//写文件
	public void write(String url) {
		try {
			File file = new File(url);
			if(file.exists()) {
				file.delete();
			}
			
			CSVWriter writer = new CSVWriter(new FileWriter(url),'|');
			List<String[]> allElements = new ArrayList<String[]>();
			allElements.add(new String[]{"序号","person","符号"});
			
			for(int i =1;i<=100;i++){
				JSONObject obj = new JSONObject();
				StringBuffer namebuffer = new StringBuffer();
				int num = (int) (Math.random()*10);
				for(int j =0;j<num;j++) {
					String zumu = zimu_shuzu[(int)(Math.random()*24)];
					namebuffer.append(zumu);
				};
				obj.put("name", namebuffer.toString());
				obj.put("age", (int)(Math.random()*100));
				obj.put("sex", sex_shuzu[(int)(Math.random()*2)]);
				//System.out.println(obj.toString());
				
				StringBuffer fuhaobuffer = new StringBuffer();
				int fuhaonum = (int) (Math.random()*10);
				for(int j =0;j<fuhaonum;j++) {
					String fuhao = fuhao_shuzu[(int)(Math.random()*16)];
					fuhaobuffer.append(fuhao);
				};
				
				allElements.add(new String[]{String.valueOf(i),obj.toString(),fuhaobuffer.toString()});
			}
			System.out.println(allElements.get(0)[0]);
			writer.writeAll(allElements);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//读文件
	public void read (String url) {
		try {
			//��ȡ����csv
			//"1","{""sex"":""Ů"",""name"":""vvthdpfhi"",""age"":26}","|)"
			CSVReader reader = new CSVReader(new FileReader(url),'|');
			String [] nextLine;
			while ((nextLine = reader.readNext()) != null) {
			    System.out.println("Name: ["+nextLine[0]+"]\nperson: ["+nextLine[1]+"]\nfuhao: ["+nextLine[2]+"]");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
