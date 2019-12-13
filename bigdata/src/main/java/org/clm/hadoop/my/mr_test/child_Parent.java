package org.clm.hadoop.my.mr_test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;

/**
 * child	parent
 * Tom	Lucy
 * Tom	Jack
 * Jone	Lucy
 * Jone	Jack
 * Lucy	Mary
 * Lucy	Ben
 * Jack	Alice
 * Jack	Jesse
 *
 * https://blog.csdn.net/m0_37597006/article/details/79201808
 */
public class child_Parent {
    public static class  Map extends Mapper<LongWritable, Text,Text,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String val = value.toString();
            String[] split = val.split("\t");
            context.write(new Text(split[0]),new Text("parent_"+split[1]));
            context.write(new Text(split[1]),new Text("child_"+split[0]));
        }
    }

    public static class  Reduce extends Reducer<Text, Text,Text,Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String parent_array[]={"",""};
            String child_array[]={"",""};
            int parent_array_index=0;
            int child_array_index=0;

            for(Text val : values){
                System.out.println(val.toString()+"----------------------------------------------");
                String[] str = val.toString().split("_");
                if(str[0].equals("parent")){
                    parent_array[parent_array_index] = str[1];
                    parent_array_index++;
                }else if(str[0].equals("child")){
                    child_array[child_array_index] = str[1];
                    child_array_index++;
                }
            }

            if(parent_array_index>0&&child_array_index>0){
                for (int i = 0; i <parent_array.length ; i++) {
                    for (int j = 0; j <child_array.length ; j++) {
                        context.write(new Text(child_array[j]),new Text(parent_array[i]));
                    }
                }
            }

            /*for(Text val : values){
                context.write(key,val);
            }*/
        }
    }

    public static void main(String[] args) throws Exception{
        Configuration conf =new Configuration();

        // 创建文件系统
        FileSystem fileSystem = FileSystem.get(new URI(args[1]), conf);
        // 如果输出目录存在，我们就删除
        if (fileSystem.exists(new Path(args[1]))) {
            fileSystem.delete(new Path(args[1]), true);
        }

        Job job =Job.getInstance(conf, "cp");

        job.setNumReduceTasks(1);
        //job.setPartitionerClass(child_Parent.mypartition.class);
        job.setJarByClass(child_Parent.class);

        job.setMapperClass(child_Parent.Map.class);
        job.setReducerClass(child_Parent.Reduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
