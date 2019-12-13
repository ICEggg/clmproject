package org.clm.hadoop.my.mr_test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/m0_37597006/article/details/79201808
 */
public class AddressTest {

    public static class  Map1 extends Mapper<LongWritable, Text,Text,Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String val = value.toString();
            String[] split = val.split("\t");
            if(!split[0].equals("factoryname")){
                System.out.println();
                context.write(new Text(split[1]),new Text("factoryname_"+split[0]));
            }
        }
    }

    public static class  Map2 extends Mapper<LongWritable, Text,Text,Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String val = value.toString();
            String[] split = val.split("\t");
            if(!split[0].equals("addressID")) {
                context.write(new Text(split[0]), new Text("addressname_" + split[1]));
            }
        }
    }

    public static class  Reduce extends Reducer<Text, Text,Text,Text> {
        int title = 0;
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            if(title==0){
                context.write(new Text("factory"),new Text("address"));
            }

            String addrss = "";
            List<String> list = new ArrayList<>();
            for(Text val : values){
                String[] split = val.toString().split("_");
                if(split[0].equals("addressname")){
                    addrss=split[1];
                }else if(split[0].equals("factoryname")){
                    list.add(split[1]);
                }
                title++;
            }

            for(String str : list){
                context.write(new Text(str),new Text(addrss));
            }

            /*for(Text val : values){
                context.write(key,val);
            }*/
        }
    }

    public static void main(String[] args) throws Exception{
        Configuration conf =new Configuration();

        // 创建文件系统
        FileSystem fileSystem = FileSystem.get(new URI(args[2]), conf);
        // 如果输出目录存在，我们就删除
        if (fileSystem.exists(new Path(args[2]))) {
            fileSystem.delete(new Path(args[2]), true);
        }

        Job job =Job.getInstance(conf, "add");

        job.setNumReduceTasks(1);
        //job.setPartitionerClass(child_Parent.mypartition.class);
        job.setJarByClass(AddressTest.class);

        job.setReducerClass(AddressTest.Reduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //map输入多个文件，一个文件对应一个map
        MultipleInputs.addInputPath(job,new Path(args[0]), TextInputFormat.class,Map1.class);
        MultipleInputs.addInputPath(job,new Path(args[1]), TextInputFormat.class,Map2.class);

        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
