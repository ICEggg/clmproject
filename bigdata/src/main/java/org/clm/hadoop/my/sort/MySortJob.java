package org.clm.hadoop.my.sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MySortJob {
    public static class Map extends Mapper<LongWritable, Text,MyNewKey,LongWritable>{
        private Text text = new Text();
        private LongWritable longWritable = new LongWritable();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String val = value.toString();
            String[] split = val.split(" ");
            Long firstnum = Long.valueOf(split[0]);
            Long secondnum = Long.valueOf(split[1]);

            MyNewKey myNewKey = new MyNewKey(firstnum,secondnum);
            context.write(myNewKey, new LongWritable(secondnum));
        }
    }


    public static class Reduce extends Reducer<MyNewKey, LongWritable,LongWritable,LongWritable>{
        @Override
        protected void reduce(MyNewKey myNewKey, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            //二次排序
            /*for(LongWritable val: values){
                context.write(new LongWritable(myNewKey.getFirstnum()),val);
            }*/

            //求出第一列相同时第二列的最小值
            long min = Long.MAX_VALUE;
            for(LongWritable val: values){
                if(val.get()<min){
                    min = val.get();
                }
            }
            context.write(new LongWritable(myNewKey.getFirstnum()),new LongWritable(min));
        }
    }

    public static class mypartition extends Partitioner<MyNewKey,LongWritable>{
        /*
         * 默认的实现 (key.hashCode() & Integer.MAX_VALUE) % numPartitions
         * 让key中first字段作为分区依据
         */
        @Override
        public int getPartition(MyNewKey myNewKey, LongWritable longWritable, int numPartitions) {
            return (myNewKey.getFirstnum().hashCode() & Integer.MAX_VALUE) % numPartitions;
        }
    }

    public static void main(String[] args) {
        try {
            Configuration conf =new Configuration();

            // 创建文件系统
            FileSystem fileSystem = FileSystem.get(new URI(args[1]), conf);
            // 如果输出目录存在，我们就删除
            if (fileSystem.exists(new Path(args[1]))) {
                fileSystem.delete(new Path(args[1]), true);
            }

            Job job =Job.getInstance(conf, "comp");

            //设置reduce任务数量
            job.setNumReduceTasks(1);
            //设置combiner
            //job.setCombinerClass(MySortJob.Reduce.class);
            //设置partition
            job.setPartitionerClass(mypartition.class);
            //job.setPartitionerClass(HashPartitioner.class);

            //设置分组
            job.setSortComparatorClass(SecondGroupComparator.class);
            job.setJarByClass(MySortJob.class);

            job.setMapperClass(MySortJob.Map.class);
            job.setReducerClass(MySortJob.Reduce.class);

            job.setMapOutputKeyClass(MyNewKey.class);
            job.setMapOutputValueClass(LongWritable.class);

            job.setOutputKeyClass(LongWritable.class);
            job.setOutputValueClass(LongWritable.class);

            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);

            FileInputFormat.setInputPaths(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            System.exit(job.waitForCompletion(true) ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
