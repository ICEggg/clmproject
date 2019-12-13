package org.clm.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.mapred.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class HbaseExample {
	// 声明静态配置
    private static Configuration conf = null;
    private static Connection connection = null;
    private static Admin admin = null;
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "10.71.4.51:2181,10.71.4.52:2181,10.71.4.53:2181");
        try {
        	connection = ConnectionFactory.createConnection(conf);
        	admin = connection.getAdmin();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //conf.set("hbase.zookeeper.property.clientPort", "2181");
        //conf.set("hbase.master", "192.168.1.154:6000");
    }
 
    //判断表是否存在
	private static boolean isExist(String tableName) throws IOException {
		TableName name = TableName.valueOf(tableName);
        return admin.tableExists(name);
    }
 
    // 创建数据库表
    public static void createTable(String tableName, String[] columnFamilys)
            throws Exception {
        // 新建一个数据库管理员
    	TableName name = TableName.valueOf(tableName);
        if (admin.tableExists(name)) {
            System.out.println("表 "+tableName+" 已存在！");
            System.exit(0);
        } else {
            // 新建一个students表的描述
            HTableDescriptor tableDesc = new HTableDescriptor(name);
            // 在描述里添加列族
            for (String columnFamily : columnFamilys) {
            	HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(columnFamily);
                tableDesc.addFamily(hColumnDescriptor);
            }
            // 根据配置好的描述建表
            admin.createTable(tableDesc);
            System.out.println("创建表 "+tableName+" 成功!");
        }
    }
 
    // 删除数据库表
    public static void deleteTable(String tableName) throws Exception {
    	TableName name = TableName.valueOf(tableName);
    	// 新建一个数据库管理员
        if (admin.tableExists(name)) {
            // 关闭一个表
        	admin.disableTable(name);
        	admin.deleteTable(name);
            System.out.println("删除表 "+tableName+" 成功！");
        } else {
            System.out.println("删除的表 "+tableName+" 不存在！");
            System.exit(0);
        }
    }
 
    // 添加一条数据
    public static void addRow(String tableName, String row,
            String columnFamily, String column, String value) throws Exception {
    	TableName name = TableName.valueOf(tableName);
    	Table table = connection.getTable(name);
        Put put = new Put(Bytes.toBytes(row));// 指定行
        // 参数分别:列族、列、值
        put.addColumn(columnFamily.getBytes(), column.getBytes(), value.getBytes());
        table.put(put);
    }
 
    // 删除一条(行)数据
    public static void delRow(String tableName, String row) throws Exception {
    	TableName name = TableName.valueOf(tableName);
    	Table table = connection.getTable(name);
        Delete del = new Delete(Bytes.toBytes(row));
        table.delete(del);
    }
 
    // 删除多条数据
    public static void delMultiRows(String tableName, String[] rows)
            throws Exception {
    	TableName name = TableName.valueOf(tableName);
    	Table table = connection.getTable(name);
        List<Delete> delList = new ArrayList<Delete>();
        for (String row : rows) {
            Delete del = new Delete(Bytes.toBytes(row));
            delList.add(del);
        }
        table.delete(delList);
    }
 
    // 获取一条数据
    public static void getRow(String tableName, String row) throws Exception {
    	TableName name = TableName.valueOf(tableName);
    	Table table = connection.getTable(name);
        Get get = new Get(Bytes.toBytes(row));
        Result result = table.get(get);
        // 输出结果,raw方法返回所有keyvalue数组
        if(!get.isCheckExistenceOnly()) {
        	for (Cell cell : result.rawCells()) {
        		String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println(colName+"--"+value);
        	}
        }
    }
 
    // 获取所有数据
    public static void getAllRows(String tableName) throws Exception {
    	TableName name = TableName.valueOf(tableName);
    	Table table = connection.getTable(name);
        Scan scan = new Scan();
        ResultScanner results = table.getScanner(scan);
        // 输出结果
        for(Result result:results) {
        	List<Cell> cs = result.listCells();
        	for (Cell cell : cs) {
        		String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println(colName+"--"+value);
			}
        }
    }
    
    
	public static void main(String... args) throws IOException {
	  try {
            String tableName = "cexample";
            // 第一步：创建数据库表：“student”
            String[] columnFamilys = { "info", "course" };
            //HbaseExample.createTable(tableName, columnFamilys);
            // 第二步：向数据表的添加数据
            // 添加第一行数据
            if (isExist(tableName)) {
            	/*	HbaseExample.addRow(tableName, "zpc", "info", "age", "20");
                HbaseExample.addRow(tableName, "zpc", "info", "sex", "boy");
                HbaseExample.addRow(tableName, "zpc", "course", "china", "97");
                HbaseExample.addRow(tableName, "zpc", "course", "math", "128");
                HbaseExample.addRow(tableName, "zpc", "course", "english", "85");
                // 添加第二行数据
                HbaseExample.addRow(tableName, "henjun", "info", "age", "19");
                HbaseExample.addRow(tableName, "henjun", "info", "sex", "boy");
                HbaseExample.addRow(tableName, "henjun", "course", "china","90");
                HbaseExample.addRow(tableName, "henjun", "course", "math","120");
                HbaseExample.addRow(tableName, "henjun", "course", "english","90");
                // 添加第三行数据
                HbaseExample.addRow(tableName, "niaopeng", "info", "age", "18");
                HbaseExample.addRow(tableName, "niaopeng", "info", "sex","girl");
                HbaseExample.addRow(tableName, "niaopeng", "course", "china","100");
                HbaseExample.addRow(tableName, "niaopeng", "course", "math","100");
                HbaseExample.addRow(tableName, "niaopeng", "course", "english","99");*/
                // 第三步：获取一条数据
                System.out.println("**************获取一条(zpc)数据*************");
                HbaseExample.getRow(tableName, "zpc");
                // 第四步：获取所有数据
                System.out.println("**************获取所有数据***************");
                HbaseExample.getAllRows(tableName);
 /*
                // 第五步：删除一条数据
                System.out.println("************删除一条(zpc)数据************");
                HbaseExample.delRow(tableName, "zpc");
                HbaseExample.getAllRows(tableName);
                // 第六步：删除多条数据
                System.out.println("**************删除多条数据***************");
                String rows[] = new String[] { "henjun","niaopeng" };
                HbaseExample.delMultiRows(tableName, rows);
                HbaseExample.getAllRows(tableName);
                // 第七步：删除数据库
                System.out.println("***************删除数据库表**************");
                HbaseExample.deleteTable(tableName);
                System.out.println("表"+tableName+"存在吗？"+isExist(tableName));*/
            } else {
                System.out.println(tableName + "此数据库表不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	 }

	  
}
