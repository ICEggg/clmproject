package org.clm.javaproject.csv.supercsv;

import java.io.FileReader;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.LMinMax;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

public class MyDataTest {

	private static final CsvPreference PIPE_DELIMITED = new CsvPreference.Builder('"', '|', "\n").build();
	private static final CsvPreference ALWAYS_QUOTE = 
		    new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).useQuoteMode(new AlwaysQuoteMode()).build();
	
	public static void main(String[] args) {
		String file = "D:\\testdata\\supercsvtestfile\\test2.txt";
		
		MyDataTest myDataTest = new MyDataTest();
		
		try {
			myDataTest.readWithCsvListReader(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void readWithCsvListReader(String file) throws Exception {
        
        ICsvListReader listReader = null;
        try {
                listReader = new CsvListReader(new FileReader(file),ALWAYS_QUOTE);
        	
                listReader.getHeader(true); // skip the header (can't be used with CsvListReader)
                final CellProcessor[] processors = getProcessors();
                
                List<Object> customerList;
                while( (customerList = listReader.read(processors)) != null ) {
                        System.out.println(String.format("lineNo=%s, rowNo=%s, customerList=%s", listReader.getLineNumber(),
                                listReader.getRowNumber(), customerList.size()));
                }
                
        }
        finally {
                if( listReader != null ) {
                        listReader.close();
                }
        }
	}
	
	
	public CellProcessor[] getProcessors() {
        
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
        
        final CellProcessor[] processors = new CellProcessor[] { 
                /*new UniqueHashCode(), // customerNo (must be unique)
                new NotNull(), // firstName
                new NotNull(), // lastName
                new ParseDate("dd/MM/yyyy"), // birthDate
                new NotNull(), // mailingAddress
                new Optional(new ParseBool()), // married
                new Optional(new ParseInt()), // numberOfKids
                new NotNull(), // favouriteQuote
                new StrRegEx(emailRegex), // email
                new LMinMax(0L, LMinMax.MAX_LONG) // loyaltyPoints
                */ 
        		 new Optional(new ParseInt()),
        		 new NotNull(),
        		 new NotNull(),
        		 new NotNull(),
        		 new NotNull()
        };
        
        return processors;
	}

}
