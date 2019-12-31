package org.clm.hbase.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.TimestampsFilter;
import org.apache.hadoop.hbase.util.Bytes;

//时间戳过滤器 TimestampsFilter
public class HbaseTimestampFilter {
	 private static final String ZK_CONNECT_KEY = "hbase.zookeeper.quorum";
	    private static final String ZK_CONNECT_VALUE = "10.71.4.51:2181,10.71.4.52:2181,10.71.4.53:2181";

	    private static Connection conn = null;
	    private static Admin admin = null;
	    
	    public static void main(String[] args) throws Exception {
	        
	        Configuration conf = HBaseConfiguration.create();
	        conf.set(ZK_CONNECT_KEY, ZK_CONNECT_VALUE);
	        conn = ConnectionFactory.createConnection(conf);
	        admin = conn.getAdmin();
	        Table table = conn.getTable(TableName.valueOf("student"));
	        
	        Scan scan = new Scan();
	        
	        List<Long> list = new ArrayList<Long>();
	        list.add(1522469029503l);
	        TimestampsFilter timestampsFilter = new TimestampsFilter(list);
	        scan.setFilter(timestampsFilter);
	        ResultScanner resultScanner = table.getScanner(scan);
	        for(Result result : resultScanner) {
	            List<Cell> cells = result.listCells();
	            for(Cell cell : cells) {
	                System.out.println(Bytes.toString(cell.getRow()) + "\t" + Bytes.toString(cell.getFamily()) + "\t" + Bytes.toString(cell.getQualifier())
	                + "\t" + Bytes.toString(cell.getValue()) + "\t" + cell.getTimestamp());
	            }
	        }
	        
	        
	    }
}