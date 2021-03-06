package javaproject.java.csv.supercsv;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.LMinMax;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

public class SuperCsvTest {
	
	public static void main(String[] args) {
		String file = "D:\\testdata\\supercsvtestfile\\test.txt";
		
		SuperCsvTest superCsvTest = new SuperCsvTest();
		try {
			//superCsvTest.readWithCsvBeanReader(file);
			//superCsvTest.readWithCsvListReader(file);
			superCsvTest.readWithCsvMapReader(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void readWithCsvBeanReader(String file) throws Exception {
	        
	        ICsvBeanReader beanReader = null;
	        try {
	                beanReader = new CsvBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
	                
	                // the header elements are used to map the values to the bean (names must match)
	                final String[] header = beanReader.getHeader(true);
	                final CellProcessor[] processors = getProcessors();
	                
	                CustomerBean customer;
	                while( (customer = beanReader.read(CustomerBean.class, header, processors)) != null ) {
	                        System.out.println(String.format("lineNo=%s, rowNo=%s, customer=%s", beanReader.getLineNumber(),
	                                beanReader.getRowNumber(), customer));
	                }
	                
	        }
	        finally {
	                if( beanReader != null ) {
	                        beanReader.close();
	                }
	        }
	}
	
	
	public void readWithCsvListReader(String file) throws Exception {
	        
	        ICsvListReader listReader = null;
	        try {
	                listReader = new CsvListReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
	                
	                listReader.getHeader(true); // skip the header (can't be used with CsvListReader)
	                final CellProcessor[] processors = getProcessors();
	                
	                List<Object> customerList;
	                while( (customerList = listReader.read(processors)) != null ) {
	                        System.out.println(String.format("lineNo=%s, rowNo=%s, customerList=%s", listReader.getLineNumber(),
	                                listReader.getRowNumber(), customerList));
	                }
	                
	        }
	        finally {
	                if( listReader != null ) {
	                        listReader.close();
	                }
	        }
	}
	
	
	public void readWithCsvMapReader(String file) throws Exception {
	        
	        ICsvMapReader mapReader = null;
	        try {
	                mapReader = new CsvMapReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
	                
	                // the header columns are used as the keys to the Map
	                final String[] header = mapReader.getHeader(true);
	                final CellProcessor[] processors = getProcessors();
	                
	                Map<String, Object> customerMap;
	                while( (customerMap = mapReader.read(header, processors)) != null ) {
	                        System.out.println(String.format("lineNo=%s, rowNo=%s, customerMap=%s", mapReader.getLineNumber(),
	                                mapReader.getRowNumber(), customerMap));
	                }
	                
	        }
	        finally {
	                if( mapReader != null ) {
	                        mapReader.close();
	                }
	        }
	}
	
	public CellProcessor[] getProcessors() {
        
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
        
        final CellProcessor[] processors = new CellProcessor[] { 
                new UniqueHashCode(), // customerNo (must be unique)
                new NotNull(), // firstName
                new NotNull(), // lastName
                new ParseDate("dd/MM/yyyy"), // birthDate
                new NotNull(), // mailingAddress
                new Optional(new ParseBool()), // married
                new Optional(new ParseInt()), // numberOfKids
                new NotNull(), // favouriteQuote
                new StrRegEx(emailRegex), // email
                new LMinMax(0L, LMinMax.MAX_LONG) // loyaltyPoints
        };
        
        return processors;
}
	
}
