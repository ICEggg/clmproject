package org.clm.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	private static String driver="org.apache.hive.jdbc.HiveDriver";
	private static String url="jdbc:hive2://172.168.0.101:32500/hyrendde";
	
	static{
		try {
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(url,"hyshf","hyshf");
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void release(Connection conn,Statement st,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			}
			catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				rs=null;
			}
		}
		
		if(st!=null){
			try {
				st.close();
			}
			catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				st=null;
			}
		}
		
		if(conn!=null){
			try {
				conn.close();
			}
			catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				conn=null;
			}
		}
	}
}
