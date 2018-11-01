package com.enumaelish.config;

import com.enumaelish.service.ScheduledService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 应用启动完成后执行
 **/
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        ScheduledService scheduledService = applicationContext.getBean(ScheduledService.class);
        scheduledService.init();

    }
}
