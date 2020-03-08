package javaproject.java.xmlrpc.authentication;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.common.XmlRpcHttpRequestConfig;
import org.apache.xmlrpc.server.AbstractReflectiveHandlerMapping;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.webserver.XmlRpcServlet;

public class MyServlet extends XmlRpcServlet{
	private static final long serialVersionUID = 1L;

	private boolean isAuthenticated(String pUserName, String pPassword) {
		return "1001".equals(pUserName) && "111111".equals(pPassword);
	}

	protected XmlRpcHandlerMapping newXmlRpcHandlerMapping() throws XmlRpcException {
		PropertyHandlerMapping mapping = (PropertyHandlerMapping) super
				.newXmlRpcHandlerMapping();

		AbstractReflectiveHandlerMapping.AuthenticationHandler handler = new AbstractReflectiveHandlerMapping.AuthenticationHandler() {
			public boolean isAuthorized(XmlRpcRequest pRequest) {
				XmlRpcHttpRequestConfig config = (XmlRpcHttpRequestConfig) pRequest
						.getConfig();
				return isAuthenticated(config.getBasicUserName(), config
						.getBasicPassword());
			};
		};
		mapping.setAuthenticationHandler(handler);
		return mapping;
	}
}
