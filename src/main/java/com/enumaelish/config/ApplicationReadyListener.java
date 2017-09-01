package com.enumaelish.config;

import com.enumaelish.quartz.StartCrawlerJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 应用启动完成后执行
 **/
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    protected static final Logger logger = LoggerFactory.getLogger(StartCrawlerJob.class);
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("启动完成");
    }
}
