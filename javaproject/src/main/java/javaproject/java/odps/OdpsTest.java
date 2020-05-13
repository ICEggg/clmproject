package javaproject.java.odps;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OdpsTest {
	public static void main(String[] args) {
		String driver = "com.connection.jdbc.Driver";
        String url = "jdbc:connection://127.0.0.1:3306/connection?useUnicode=true&characterEncoding=UTF-8&createDatabaseIfNotExist=true";
        String user = "root";  
        String password = "root";  
  
        try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password); 
			
			DatabaseMetaData dbMetData = conn.getMetaData();  
	        // connection convertDatabaseCharsetType null
	        ResultSet rs = dbMetData.getTables("null", "null", "wc_table",new String[] {"TABLE"});
	  
	        while (rs.next()) {  
	            if (rs.getString(4) != null  
	                    && (rs.getString(4).equalsIgnoreCase("TABLE") || rs  
	                            .getString(4).equalsIgnoreCase("VIEW"))) {  
	                String tableName = rs.getString(3).toLowerCase();  
	                System.out.print(tableName + "\t");  
	                // 根据表名提前表里面信息：  
	                ResultSet colRet = dbMetData.getColumns(null, "%", tableName,  
	                        "%");  
	                while (colRet.next()) {  
	                    String columnName = colRet.getString("COLUMN_NAME");  
	                    String columnType = colRet.getString("TYPE_NAME");  
	                    int datasize = colRet.getInt("COLUMN_SIZE");  
	                    int digits = colRet.getInt("DECIMAL_DIGITS");  
	                    int nullable = colRet.getInt("NULLABLE");  
	                     System.out.println(columnName + " " + columnType + " "+  datasize + " " + digits + " " + nullable);  
	                }  
	  
	            }  
	        }  
	        System.out.println();  
			
			//OdpsTest test = new OdpsTest();
			//test.getTables(conn);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
	}
	
	public void getTables(Connection conn) throws SQLException {  
        DatabaseMetaData dbMetData = conn.getMetaData();  
        // connection convertDatabaseCharsetType null
        ResultSet rs = dbMetData.getTables("null", "null", "wc_table",new String[] {"TABLE"});
  
        while (rs.next()) {  
            if (rs.getString(4) != null  
                    && (rs.getString(4).equalsIgnoreCase("TABLE") || rs  
                            .getString(4).equalsIgnoreCase("VIEW"))) {  
                String tableName = rs.getString(3).toLowerCase();  
                System.out.print(tableName + "\t");  
                // 根据表名提前表里面信息：  
                ResultSet colRet = dbMetData.getColumns(null, "%", tableName,  
                        "%");  
                while (colRet.next()) {  
                    String columnName = colRet.getString("COLUMN_NAME");  
                    String columnType = colRet.getString("TYPE_NAME");  
                    int datasize = colRet.getInt("COLUMN_SIZE");  
                    int digits = colRet.getInt("DECIMAL_DIGITS");  
                    int nullable = colRet.getInt("NULLABLE");  
                     System.out.println(columnName + " " + columnType + " "+  datasize + " " + digits + " " + nullable);  
                }  
  
            }  
        }  
        System.out.println();  
  
        // resultSet数据下标从1开始 ResultSet tableRet =  
        //conn.getMetaData().getTables(null, null, "%", new String[] { "TABLE" });  
        //while (tableRet.next()) {  
        //  System.out.print(tableRet.getString(3) + "\t");  
        //}  
        //System.out.println();  
  
    }  
	
}
