package javaproject.java.csv.opencsv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class OpenCsvReadFile {
	
	public static void main(String[] args){
		OpenCsvReadFile test = new OpenCsvReadFile();
		//文件路径       数据有多少列
		//test.readfile(args[0],args[1]);
		try {
			test.readfile("D:\\testdata\\supercsvtestfile\\test2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readfile(String url) throws IOException {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(url),'|');
			String [] nextLine;
		    while ((nextLine = reader.readNext()) != null) {
		    	//System.out.println(nextLine[1]+"--"+nextLine[2]+"--"+nextLine[3]+"--"+nextLine[4]+"--"+nextLine[5]
		    	//		+"--"+nextLine[6]+"--"+nextLine[7]+"--"+nextLine[8]);
		    	System.out.println(nextLine.length);
		    }
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
      
	}
}	
