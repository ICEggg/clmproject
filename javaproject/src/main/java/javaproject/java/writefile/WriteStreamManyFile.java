package javaproject.java.writefile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import au.com.bytecode.opencsv.CSVWriter;


//往一个文件夹下一直写文件
public class WriteStreamManyFile {
	private static final String ADDRESS_FILE="D:\\testdata\\streamfile\\";
	private static String basedirname = "data"; //每个文件夹的名称
	private static String basefilename = "person"; //每个文件名
	
	private static String[] zimu_shuzu = {"a","b","c","d","e","f","g","h","i","j","k",
			"l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"}; 
	private static String[] sex_shuzu = {"男","女"};
	private static String[] fuhao_shuzu = {"!","@","#","$","%","^","&","*","(",")",
			"_","+","|","{","}","|"};//16
	
	public static void main(String[] args) {
		WriteStreamManyFile sdf = new WriteStreamManyFile();
		//文件夹数量       每个文件有多少行数据
		sdf.writefile(3, 100);
	}
	
	public void writefile(int filenum, int linenum) {
		
		int dirnum = 0;
		while(true) {
			String dirurl = WriteStreamManyFile.ADDRESS_FILE+basedirname+dirnum;
			File dirfile = new File(dirurl);
			if(!dirfile.exists()) {
				dirfile.mkdirs();
			}else {
				deleteDir(dirurl);
			}
			
			
			for(int k=0;k<filenum;k++) {
				String url = dirurl+"\\"+basefilename+k+".csv";
				File file = new File(url);
				if(file.exists()) {
					file.delete();
				}
				
				try {
					CSVWriter writer = new CSVWriter(new FileWriter(url),'|');
					List<String[]> allElements = new ArrayList<String[]>();
					allElements.add(new String[]{"序号","person","符号"});
					
					for(int i =1;i<=linenum;i++){
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
					writer.writeAll(allElements);
					writer.close();
					
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			dirnum++;
			
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

	
	public static boolean deleteDir(String path){
		File file = new File(path);
		if(!file.exists()){//判断是否待删除目录是否存在
			System.err.println("The dir are not exists!");
			return false;
		}
		
		String[] content = file.list();//取得当前目录下所有文件和文件夹
		for(String name : content){
			File temp = new File(path, name);
			if(temp.isDirectory()){//判断是否是目录
				deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
				temp.delete();//删除空目录
			}else{
				if(!temp.delete()){//直接删除文件
					System.err.println("Failed to delete " + name);
				}
			}
		}
		return true;
	}
	
}
