package org.clm.sparkstreaming.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;


public class ConnectionPool {
	 private static LinkedList<Connection> connectionQueue;
	 private static String url = "jdbc:mysql://127.0.0.1:3306/mytable?characterEncoding=utf8&useSSL=true";
	 private static String user = "root";
	 private static String password = "root";
	 
	    static {
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	        }catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

	    public synchronized static Connection getConnection() {
	        try {
	            if (connectionQueue == null) {
	                connectionQueue = new LinkedList<Connection>();
	                for (int i = 0;i < 5;i ++) {
	                    Connection conn = DriverManager.getConnection(url,user,password);
	                    connectionQueue.push(conn);
	                }
	            }
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	        return connectionQueue.poll();
	    }

	    public static void returnConnection(Connection conn) {
	        connectionQueue.push(conn);
	    }
}
