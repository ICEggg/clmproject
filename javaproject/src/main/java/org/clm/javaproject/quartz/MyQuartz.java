package org.clm.javaproject.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MyQuartz {
	public static void main(String[] args) {
		try{
	        //1、获得一个scheduler
	        SchedulerFactory sf=new StdSchedulerFactory();
	        Scheduler scheduler =sf.getScheduler();
	        //2、获得一个jobDetail
	        JobDetail job = JobBuilder.newJob(MyJob.class)
	                 .withIdentity("myJob")
	                 .build();

	        //3、获得一个trigger
	         Trigger trigger = TriggerBuilder.newTrigger()
	                  .withIdentity("trigger1", "group1")
	                  .startNow()
	                  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
	                          .withIntervalInSeconds(5)
	                          .withRepeatCount(10))
	                         .build();
	        //4、把任务和触发器放到scheduler中
	        scheduler.scheduleJob(job, trigger);
	        //5、开始任务调度
	        scheduler.start();
	        }
	        catch(SchedulerException e){
	            e.printStackTrace();
	        }
	}
}
