package org.clm.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

public class MyUdfAdd extends UDF{
	
	public int evaluate(String arg) {
		int num = Integer.parseInt(arg);
		return num+1;
	}

}
