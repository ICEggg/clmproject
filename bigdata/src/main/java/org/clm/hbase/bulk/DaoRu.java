package org.clm.hbase.bulk;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;

public class DaoRu {
	public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("<Usage>Please input hbase-site.xml path.</Usage>");
            return;
        }
        String output = "hdfs://nameservice1/tmp/pres";
        Configuration conf = new Configuration();
        conf.addResource(new Path(args[0]));
        HTable table = new HTable(conf, "person");
        LoadIncrementalHFiles loader = new LoadIncrementalHFiles(conf);
        loader.doBulkLoad(new Path(output), table);
    }
}
