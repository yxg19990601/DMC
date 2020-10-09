package com.ems.szy.config;

import com.ems.szy.wegw.client.WegwClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@PropertySource("classpath:/application.yml")
@Configuration
public class ScheduledTask {



    @Autowired
    private WegwClient client;

    private boolean isRunning = false;
    private Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    @Scheduled(cron ="${cron}" )
    public void sendMsgTask() throws Exception{
        if (!isRunning) {
            log.error("sendMsgTask running ....");
            client.sendMsg();
            try {
                isRunning = true;
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
                log.error("sendMsgTask done ....");
            }
        }

    }

}
