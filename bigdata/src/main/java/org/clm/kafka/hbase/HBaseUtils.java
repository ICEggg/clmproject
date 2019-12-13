package org.clm.kafka.hbase;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtils {
	private static Configuration conf = null;
	private static Connection connection = null;
	private static Admin admin=null;
	private static Table table =null;
	static {
		conf = new Configuration();
		conf.set("hbase.zookeeper.quorum",  "10.71.4.51:2181,10.71.4.52:2181,10.71.4.53:2181");
		try {
			connection=ConnectionFactory.createConnection(conf);
			admin = connection.getAdmin();
			
			String tableName = "emp3"; 
			TableName tablename = TableName.valueOf(tableName);
			String[] columnFamilys = { "info", "course" };
			if(admin.tableExists(tablename)) {
				admin.disableTable(tablename);
				admin.deleteTable(tablename);
			}
			
			HTableDescriptor desc = new HTableDescriptor(tablename);
			for (String columnFamily : columnFamilys) {
				desc.addFamily(new HColumnDescriptor(columnFamily));
	        }
			admin.createTable(desc);
			table = connection.getTable(tablename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HBaseUtils util = new HBaseUtils();
		try {
			util.put("aaaaaa");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void put(String string) throws IOException {
        Random random = new Random(); 
        long a = random.nextInt(100000000);            
        String rowkey = "rowkey"+a ; 
        String columnFamily = "info";  
        String column = "name";  
        
        Put put=new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(string));
        table.put(put);
        System.out.println("放入成功");
        //table.close();//释放资源 
        } 
}
