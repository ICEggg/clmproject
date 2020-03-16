package org.clm.hbase.bulk;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * https://www.cnblogs.com/smartloli/p/9501887.html
 * @author dev
 *
 */
public class PiLiang_BulkTest {
	static class HFileImportMapper2 extends Mapper<LongWritable, Text, ImmutableBytesWritable, KeyValue> {
        
        protected final String CF_KQ = "cf";

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            System.out.println("line : " + line);
            String[] datas = line.split(" ");
            String row = new Date().getTime() + "_" + datas[1];
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(Bytes.toBytes(row));
            KeyValue kv = new KeyValue(Bytes.toBytes(row), this.CF_KQ.getBytes(), datas[1].getBytes(), datas[2].getBytes());
            context.write(rowkey, kv);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("<Usage>Please input hbase-site.xml path.</Usage>");
            return;
        }
        Configuration conf = new Configuration();
        conf.addResource(new Path(args[0]));
        conf.set("hbase.fs.tmp.dir", "partitions_" + UUID.randomUUID());
        String tableName = "person";
        String input = "hdfs://nameservice1/hrds/mashibing/testdata/person";
        String output = "hdfs://nameservice1/tmp/pres";
        System.out.println("table : " + tableName);
        HTable table;
        try {
            try {
                FileSystem fs = FileSystem.get(URI.create(output), conf);
                fs.delete(new Path(output), true);
                fs.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Connection conn = ConnectionFactory.createConnection(conf);
            table = (HTable) conn.getTable(TableName.valueOf(tableName));
            Job job = Job.getInstance(conf);
            job.setJobName("Generate HFile");

            job.setJarByClass(PiLiang_BulkTest.class);
            job.setInputFormatClass(TextInputFormat.class);
            job.setMapperClass(HFileImportMapper2.class);
            FileInputFormat.setInputPaths(job, input);
            FileOutputFormat.setOutputPath(job, new Path(output));

            HFileOutputFormat2.configureIncrementalLoad(job, table);
            try {
                job.waitForCompletion(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
