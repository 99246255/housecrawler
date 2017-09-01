package com.enumaelish.quartz;

import com.enumaelish.webmagic.HZHouseTransactionPipeline;
import com.enumaelish.webmagic.HZHouseTransactionProcessor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Spider;

@DisallowConcurrentExecution
public class StartCrawlerJob implements Job {

	protected static final Logger logger = LoggerFactory.getLogger(StartCrawlerJob.class);
	@Autowired
	HZHouseTransactionPipeline hzHouseTransactionPipeline;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始爬取信息");
        Spider.create(new HZHouseTransactionProcessor()).addPipeline(hzHouseTransactionPipeline).addRequest(HZHouseTransactionProcessor.getRequest(1)).thread(3).run();
		logger.info("爬取信息结束");
	}

}
