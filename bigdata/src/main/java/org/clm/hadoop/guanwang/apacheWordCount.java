package org.clm.hadoop.guanwang;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

//官网代码
public class apacheWordCount {
	//Map
	public static class WordCountMap extends Mapper<Object, Text, Text, IntWritable>{
		private final IntWritable one = new IntWritable(1);
		private Text word = new Text();
		public void map(Object key,Text value, Context context) throws IOException, InterruptedException{
			String line =value.toString();
			StringTokenizer token = new StringTokenizer(line);
			while(token.hasMoreTokens()){
				word.set(token.nextToken());
				context.write(word,one);
			}
		}
	}
	
	//Reducer
	public static class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
		private  IntWritable result = new IntWritable();
		public void reduce (Text key,Iterable<IntWritable> values,Context context) throws IOException, InterruptedException{
			int sum=0;
			for (IntWritable val : values) {
				sum+=val.get();
			}
			result.set(sum);
			context.write(key,result);
		}
	}
	
	
	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		Configuration conf =new Configuration();
	//	String[] otherArgs =new GenericOptionsParser(conf,args).getRemainingArgs();
		
//		if(otherArgs.length !=3){
//			System.out.println("Usage: WordCount <in> <out>");
//			System.exit(2);
//		}
		Job job =Job.getInstance(conf, "wordcount");
		job.setJarByClass(apacheWordCount.class);
		job.setJobName("wordcount");
		job.setMapperClass(WordCountMap.class);
		job.setCombinerClass(WordCountReduce.class);
		job.setReducerClass(WordCountReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job,  new Path(args[1]));
//		FileInputFormat.addInputPath(job, new Path("hdfs://nameservice1/hrds/mashibing/testdata/word"));
//		FileOutputFormat.setOutputPath(job,  new Path("hdfs://nameservice1/hrds/mashibing/testdata/outwordresult"));
		
//		job.setInputFormatClass(TextInputFormat.class);
//		job.setOutputFormatClass(TextOutputFormat.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
