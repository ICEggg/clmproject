package org.clm.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

public class HiveKerberos {
	 private static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	    private static String CONNECTION_URL ="jdbc:hive2://hxmaster:10000/;principal=hive/hxmaster@ANDREW.COM";
	 
	    static {
	        try {
	            Class.forName(JDBC_DRIVER);
	 
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	 
	    public static void main(String[] args) throws Exception  {
	        Class.forName(JDBC_DRIVER);
	 
	        //登录Kerberos账号
	        System.setProperty("java.security.krb5.conf", "D:\\keytab\\krb5.conf");
	 
	        Configuration configuration = new Configuration();
	        configuration.set("hadoop.security.authentication" , "Kerberos" );
	        UserGroupInformation. setConfiguration(configuration);
	        UserGroupInformation.loginUserFromKeytab("user1@ANDREW.COM",
	                "D:\\keytab\\user1.keytab");
	 
	        Connection connection = null;
	        ResultSet rs = null;
	        PreparedStatement ps = null;
	        try {
	            connection = DriverManager.getConnection(CONNECTION_URL);
	            ps = connection.prepareStatement("select * from db1.table1");
	            rs = ps.executeQuery();
	            while (rs.next()) {
	                System.out.println(rs.getString(1));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
