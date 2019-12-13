package org.clm.javaproject.xmlrpc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class Client1 {
	public static void main(String[] args) {

		try {
			// 客户端配置对象，并且设置用户名密码
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setBasicUserName("1001");
			config.setBasicPassword("111111");
			config.setServerURL(new URL("http://127.0.0.1:8080/XML_RPC/Authentication"));

			//创建一个XmlRpcClient对象，并给它绑定一个配置对象
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);

			// 创建参数集合
			Vector<Integer> params= new Vector<Integer>();
			params.addElement(999);
			params.addElement(8);
			Integer result=(Integer)client.execute("Calculator.add",params);

			System.out.println(result);

		} catch (MalformedURLException e) {
			System.out.println(e.toString());
		} catch (XmlRpcException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
