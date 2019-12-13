package org.clm.javaproject.xml.saxxml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Saxtest {
	
	public void sax(){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser=factory.newSAXParser();
			SAXParserHandler handler=new SAXParserHandler();
			parser.parse("books.xml", handler);
		}
		catch(ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
