package com.Over2Cloud.ctrl.ServiceListener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;



public class QuartzSchedulerListener implements ServletContextListener {
	private static Logger log = Logger.getLogger(QuartzSchedulerListener.class);
	public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";
    private StdSchedulerFactory factory = null;
	@SuppressWarnings("static-access")
	public void contextDestroyed(ServletContextEvent arg0) {
		try{  
		factory.getDefaultScheduler().shutdown();
        }catch (SchedulerException ex){
        	ex.printStackTrace();
        }
		//
	}
	public void contextInitialized(ServletContextEvent arg0) {
		/*JobDetail job = JobBuilder.newJob(SchedulerJob.class).withIdentity(
				"anyJobName", "group1").build();*/

		JobDetail instantsms = JobBuilder.newJob(InstantmsgScheduler.class).withIdentity("anyJobName", "group1").build();
		JobDetail schedulemsg = JobBuilder.newJob(SchedulerJob.class).withIdentity("anyJobName", "group2").build();
		JobDetail sendmail = JobBuilder.newJob(Instantmailscheduler.class).withIdentity("anyJobName", "group3").build();
		JobDetail schedulesendmail = JobBuilder.newJob(Schedulemailscheduler.class).withIdentity("anyJobName", "group4").build();
		try {
			//sending instant message.
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(
					"anyJobName", "group1").withSchedule(
					CronScheduleBuilder.cronSchedule(" 0/30 * * * * ?")) // time
																		// set (
																		// "0 0 17 * * ?"
																		// )for
																		// 5 pm
																		// and (
																		// " 0/20 * * * * ?"
																		// ) for
																		// 20
																		// seconds
					.build();

			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(instantsms, trigger);
			
			
			//sending Schedule Msg.
			
			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity(
					"anyTriggerName", "group2").withSchedule(
					CronScheduleBuilder.cronSchedule(" 0/50 * * * * ?")) // time set
																		// (
																		// "0 0 17 * * ?"
																		// )for
																		// 5 pm
																		// and (
																		// " 0/20 * * * * ?"
																		// ) for
																		// 20
																		// seconds
					.build();

			Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
			scheduler2.start();
			scheduler2.scheduleJob(schedulemsg, trigger2);
			
			
				//sending instant Mail.
				Trigger mailtrigger = TriggerBuilder.newTrigger().withIdentity(
						"anyJobName", "group3").withSchedule(
						CronScheduleBuilder.cronSchedule(" 0/20 * * * * ?")) // time
																			// set (
																			// "0 0 17 * * ?"
																			// )for
																			// 5 pm
																			// and (
																			// " 0/20 * * * * ?"
																			// ) for
																			// 20
																			// seconds
						.build();

				Scheduler mailscheduler = new StdSchedulerFactory().getScheduler();
				mailscheduler.start();
				mailscheduler.scheduleJob(sendmail, mailtrigger);
				
				
				//sending schedule Mail.
				Trigger schedulemailtrigger = TriggerBuilder.newTrigger().withIdentity(
						"anyJobName", "group4").withSchedule(
						CronScheduleBuilder.cronSchedule(" 0/10 * * * * ?")) // time
																			// set (
																			// "0 0 17 * * ?"
																			// )for
																			// 5 pm
																			// and (
																			// " 0/20 * * * * ?"
																			// ) for
																			// 20
																			// seconds
						.build();

				Scheduler schedulemailscheduler = new StdSchedulerFactory().getScheduler();
				schedulemailscheduler.start();
				schedulemailscheduler.scheduleJob(schedulesendmail, schedulemailtrigger);

					} catch (SchedulerException e) {
			e.printStackTrace();
			log.info("Error in QuartzSchedulerListener.java \n "
					+ e.getStackTrace());
		}

	}
}