package org.clm.hbase.baseapi;


public interface HBaseUtils {
	public void getAllTables();
	public void putData(String tableName, String rowKey, String familyName, String columnName, String value);
	public void putData(String tableName, String rowKey, String familyName, String columnName, String value,long timestamp);
}
