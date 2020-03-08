package javaproject.java.xml.saxxml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserHandler extends DefaultHandler{
	public int num=0;
	
	/**
	 * 用来遍历xml文件的开始标签
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if(qName.equals("book")){
			//已知book下属性的名称，根据属性名获取属性值
//			String value = attributes.getValue("id");
//			System.out.println("book的属性值是："+value);
			num++;
			System.out.println("---------------开始遍历第"+num+"本书的---------------------");
			for(int i=0;i<attributes.getLength();i++){
				System.out.print("book元素的第"+(i+1)+"个属性名是："+attributes.getQName(i)+"----");
				System.out.println(attributes.getQName(i)+"的值是："+attributes.getValue(i));
			}
		}else if(qName!="book"&&qName!="bookstore"){
			System.out.println(qName);
		}	
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String value=new String(ch, start, length);
		System.out.println(value);
	}
	
	
	/**
	 * 用来遍历xml文件的结束标签
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(qName.equals("book")){
			System.out.println("---------------结束第"+num+"本书的遍历-------------------");	
		}
		
	}
	
	/**
	 * 用来标识解析开始
	 */
	@Override
	public void startDocument() throws SAXException {
	
		// TODO Auto-generated method stub
		super.startDocument();
		System.out.println("SAX解析开始");
	}
	
	/**
	 * 用来标识解析结束
	 */
	@Override
	public void endDocument() throws SAXException {
	
		// TODO Auto-generated method stub
		super.endDocument();
		System.out.println("SAX解析结束");
	}
}
