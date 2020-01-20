package org.clm.javaproject.csv.jacksoncsv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JackSonCsvTest {
	
	//根据bean的创建schema
	public void way(File csvFile) {
		try {
			CsvMapper mapper = new CsvMapper();
			Pojo value = new Pojo();
			CsvSchema schema = mapper.schemaFor(Pojo.class); // schema from 'Pojo' definition
			String csv = mapper.writer(schema).writeValueAsString(value);
			MappingIterator<Pojo> it = mapper.readerFor(Pojo.class).with(schema)
			  .readValues(csvFile);
			// Either read them all one by one (streaming)
			while (it.hasNextValue()) {
			  Pojo value2 = it.nextValue();
			  System.out.println(value2.getId()+"-"+value2.getName()+"-"+value2.getScore()+"-"+value2.getTmp());
			}
			// or, alternatively all in one go
			List<Pojo> all = it.readAll();
			
			
			/*for (Pojo pojo : all) {
				System.out.println(pojo.getId()+"-"+pojo.getScore()+"-"+pojo.getScore()+"-"+pojo.getTmp());
			}*/
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//根据文件的第一行定义schema
	public void way2(File csvFile) {
		try {
			CsvMapper mapper = new CsvMapper();
			CsvSchema schema = CsvSchema.emptySchema().withHeader(); // use first row as header; otherwise defaults are fine
			MappingIterator<Map<String,String>> it = mapper.readerFor(Map.class)
			   .with(schema)
			   .readValues(csvFile);
			while (it.hasNext()) {
			  Map<String,String> rowAsMap = it.next();
			  
			  for (String str : rowAsMap.keySet()) {
				  System.out.println(rowAsMap.get(str));
			  }
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		File csvFile = new File("D:\\testdata\\supercsvtestfile\\test2.txt");
		JackSonCsvTest js = new JackSonCsvTest();
		
		//js.way(csvFile);
		js.way2(csvFile);
		
		
	}
}
