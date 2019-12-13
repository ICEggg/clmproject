package org.clm.hadoop.my;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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

public class Avg {
    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
        private Text text =new Text();
        private IntWritable textscore =new IntWritable();
        @Override
        protected void map(LongWritable key, Text value,Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String[] splits=line.split("\\|");
            String name = splits[0];
            int score= Integer.valueOf(splits[2]);
            text.set(name);
            textscore.set(score);
            context.write(text,textscore);
        }

    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        private Text name =new Text();
        private IntWritable avg =new IntWritable();
        @Override
        protected void reduce(Text key, Iterable<IntWritable> value,Context context)
                throws IOException, InterruptedException {

            int courseCount = 0;
            int sum = 0;
            int average = 0;

            for (IntWritable val : value){
                sum += Integer.parseInt(val.toString());
                courseCount ++;
            }

            average = sum / courseCount;
            name.set(key);
            avg.set(average);

            context.write(name, avg);
        }
    }

    public static class mypartition extends Partitioner<Text, IntWritable> {

        @Override
        public int getPartition(Text key, IntWritable value, int numPartitions) {
            //return (key.hashCode() & Integer.MAX_VALUE) % numPartitions;

            if(key.toString().equals("a")){
                return 0;
            }else if(key.toString().equals("b")){
                return 1;
            }else{
                return 2;
            }
        }

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        Configuration conf =new Configuration();

        // 创建文件系统
        FileSystem fileSystem = FileSystem.get(new URI(args[1]), conf);
        // 如果输出目录存在，我们就删除
        if (fileSystem.exists(new Path(args[1]))) {
            fileSystem.delete(new Path(args[1]), true);
        }

        Job job =Job.getInstance(conf, "avg");

        job.setNumReduceTasks(3);
        //combiner测试
        job.setCombinerClass(Reduce.class);

        job.setPartitionerClass(Avg.mypartition.class);
        job.setJarByClass(Avg.class);

        job.setMapperClass(Avg.Map.class);
        job.setReducerClass(Avg.Reduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
