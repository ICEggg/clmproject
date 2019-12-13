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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * hello a
 * hello b
 * hello c
 */

public class WordCount {

	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable>{
		private IntWritable one =new IntWritable(1);
		private Text text =new Text();
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words=line.split(" ");
			for (String string : words) {
				text.set(string);
				context.write(text, one);
			}
		}
		
	}
	
	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable result =new IntWritable();
		@Override
		protected void reduce(Text key, Iterable<IntWritable> value,Context context)
				throws IOException, InterruptedException {
			System.out.println("进入reduce。。。。。。。。。。。。。。");
			System.out.println("key============"+key);
			int sum=0;
			for (IntWritable values : value) {
				sum+=values.get();
			}
			result.set(sum);
			context.write(key,result);
		}
	}
	
	public static class mypartition extends Partitioner<Text, IntWritable>{

		@Override
		public int getPartition(Text key, IntWritable value, int numPartitions) {

			if(key.toString().equals("hello")){
				return 0;
			}else if(key.toString().equals("aio")){
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
		
		Job job =Job.getInstance(conf, "wordcount");
		
		job.setNumReduceTasks(3);
		job.setPartitionerClass(mypartition.class);
		job.setJarByClass(WordCount.class);
		
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
}
