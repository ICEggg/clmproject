package org.clm.hive;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class hiveJDBCdemo {
	public static void main(String[] args) {
		Connection conn=null;
		Statement st =null;
		ResultSet rs=null;
		
		String sql = "select * from zzzz77";
		
		try {
			//获取链接
			conn=JDBCUtil.getConnection();
			//创建运行环境
			st=conn.createStatement();
			//运行HQL
			rs=st.executeQuery(sql);
			
			while(rs.next()){
				String name=rs.getString("id");
				System.out.println(name);
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.release(conn, st, rs);
		}
		
	}
}
