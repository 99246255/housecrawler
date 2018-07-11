package com.enumaelish.quartz;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Calendar;

/**
 * Springboot Quartz配置
 */
@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class SchedulerConfig {

    private static final String GROUP = "HZSecondhandHouse";

    private static final String BLANK = " ";
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, @Qualifier("sampleJobTrigger") Trigger sampleJobTrigger) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTriggers(sampleJobTrigger);
        factory.setJobFactory(jobFactory);
        return factory;
    }

    @Bean
    public JobDetailFactoryBean sampleJobDetail() {
        return createJobDetail(StartCrawlerJob.class, GROUP);
    }

    @Bean(name = "sampleJobTrigger")
    public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("sampleJobDetail") JobDetail jobDetail) {
        return createCronTrigger(jobDetail, GROUP);
    }

    private static JobDetailFactoryBean createJobDetail(Class jobClass, String group) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setGroup(group);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String group) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setGroup(group);
        factoryBean.setCronExpression(getCronExpression());
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        return factoryBean;
    }

    /**
     * cron表达式，当前时间往后1分钟执行，每12小时执行一次
     * @return
     */
    public static String getCronExpression(){
        StringBuffer stringBuffer = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        stringBuffer.append(cal.get(Calendar.SECOND)).append(BLANK).append(cal.get(Calendar.MINUTE)+1).append(BLANK).append(cal.get(Calendar.HOUR_OF_DAY)%12).append("/12 * * ?");
        return stringBuffer.toString();
    }
    public static void main(String[] args) {
        System.out.println(getCronExpression());
    }
}
