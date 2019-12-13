package org.clm.hadoop.my;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ManyMapReduce {
    public static void main(String[] args) throws Exception {

        JobConf conf = new JobConf(ManyMapReduce.class);

        //job1设置
        Job job1 = new Job(conf, "job1");
        job1.setJarByClass(ManyMapReduce.class);
        //job1.setMapperClass(IpFilterMapper.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(NullWritable.class);

        //job1.setReducerClass(IpFilterReducer.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(NullWritable.class);
        FileInputFormat.setInputPaths(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path(args[1]));

        //job1加入控制器
        ControlledJob ctrlJob1 = new ControlledJob(conf);
        ctrlJob1.setJob(job1);

        //job2设置
        Job job2 = new Job(conf, "job2");
        job2.setJarByClass(ManyMapReduce.class);
        //job2.setMapperClass(IpCountMapper.class);
        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(NullWritable.class);

        //job2.setReducerClass(IpCountReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(NullWritable.class);
        FileInputFormat.setInputPaths(job2, new Path(args[1]));
        FileOutputFormat.setOutputPath(job2, new Path(args[2]));

        //job2加入控制器
        ControlledJob ctrlJob2 = new ControlledJob(conf);
        ctrlJob2.setJob(job2);

        //设置作业之间的以来关系，job2的输入以来job1的输出
        ctrlJob2.addDependingJob(ctrlJob1);

        //设置主控制器，控制job1和job2两个作业
        JobControl jobCtrl = new JobControl("myCtrl");
        //添加到总的JobControl里，进行控制
        jobCtrl.addJob(ctrlJob1);
        jobCtrl.addJob(ctrlJob2);


        //在线程中启动，记住一定要有这个
        Thread thread = new Thread(jobCtrl);
        thread.start();
        while (true) {
            if (jobCtrl.allFinished()) {
                System.out.println(jobCtrl.getSuccessfulJobList());
                jobCtrl.stop();
                break;
            }
        }

    }
}
