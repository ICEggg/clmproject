package javaproject.java.xmlrpc.authentication;


import javaproject.java.xmlrpc.action.Calculator;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	private XmlRpcServletServer server;

    public void init(ServletConfig pConfig) throws ServletException {
        super.init(pConfig);
		try {
			// 创建一个XmlRpcServletServer对象
            server = new XmlRpcServletServer();

            // 创建一个接收器并映射到XmlRpcServletServer对象
			PropertyHandlerMapping phm = (PropertyHandlerMapping)newXmlRpcHandlerMapping();
			phm.addHandler("Calculator", Calculator.class);
			server.setHandlerMapping(phm);
            // 更多XmlRpcServletServer对象设置 
            XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl)server.getConfig();
            serverConfig.setEnabledForExtensions(true);
            serverConfig.setContentLengthOptional(false);
		}
		catch(XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void doPost(HttpServletRequest pRequest, HttpServletResponse pResponse)
          throws IOException, ServletException {
          server.execute(pRequest, pResponse);
    }
}
