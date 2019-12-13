package org.clm.javaproject.writefile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import au.com.bytecode.opencsv.CSVWriter;


//当一个文件夹下生成20个文件后，就新建一个文件夹在往里面写20个文件
public class WriteStreamOneFile {
	
	private static String[] zimu_shuzu = {"a","b","c","d","e","f","g","h","i","j","k",
			"l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"}; 
	private static String[] sex_shuzu = {"男","女"};
	private static String[] fuhao_shuzu = {"!","@","#","$","%","^","&","*","(",")",
			"_","+","|","{","}","|"};//16
	
	public static void main(String[] args) {
		WriteStreamOneFile sdf = new WriteStreamOneFile();
		
		//文件夹路径     定义文件名    文件数量     每个文件有多少行数据
		sdf.writefile("D:\\testdata\\streamfile\\","a",10,10);
		
		//sdf.writefile(args[0],args[1], Integer.parseInt(args[2]),Integer.parseInt(args[3]));
	}
	
	public void writefile(String path,String filename , int filenum, int linenum) {
		Long xuhao = 0L;
		for(int k=1;k<=filenum;k++) {
			
			String url = path+filename+k+".csv";
			File file = new File(url);
			if(file.exists()) {
				file.delete();
			}
			
			try {
				CSVWriter writer = new CSVWriter(new FileWriter(url),'|');
				List<String[]> allElements = new ArrayList<String[]>();
				//allElements.add(new String[]{"序号","person","符号"});
				
				for(int i =1;i<=linenum;i++){
					xuhao++;
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
					
					allElements.add(new String[]{String.valueOf(xuhao),obj.toString(),fuhaobuffer.toString()});
				}
				writer.writeAll(allElements);
				writer.close();
				
				//Thread.sleep(1000);
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
			/*catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}

	}
}
