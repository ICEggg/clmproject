package javaproject.java.xml.domxml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMtest {

	public void dom() {
		// TODO Auto-generated method stub
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document=db.parse("books.xml");
				NodeList booklist = document.getElementsByTagName("book");
				System.out.println("一共有"+booklist.getLength()+"本书");
				//当不知道节点属性名时
				for(int i=0;i<booklist.getLength();i++){
					Node node=booklist.item(i);
					NamedNodeMap attrs=node.getAttributes();
					System.out.print("第"+(i+1)+"本书共有"+attrs.getLength()+"种属性:");
					for(int j=0;j<attrs.getLength();j++){
						System.out.println(attrs.item(j).getNodeName()+"="+attrs.item(j).getNodeValue());
					}
					
					NodeList childNodes=booklist.item(i).getChildNodes();
					for(int k=0;k<childNodes.getLength();k++){
						if(childNodes.item(k).getNodeType()==Node.ELEMENT_NODE){
							System.out.println("第"+(k+1)+"个节点是："+childNodes.item(k).getNodeName());
							System.out.println("第"+(k+1)+"个节点的值是："+childNodes.item(k).getFirstChild().getNodeValue());
							//System.out.println("第"+(k+1)+"个节点的值是："+childNodes.item(k).getTextContent());
						}
							
					}
				}
				//当知道节点属性名时,直接根据属性来获取
				/*for(int i=0;i<booklist.getLength();i++){
					Element book =(Element)booklist.item(i);
					String attrValue=book.getAttribute("id");
					System.out.println("id的属性值为："+attrValue);
				}*/
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
