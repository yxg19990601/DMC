package com.ems.szy.config;

import com.ems.szy.wegw.client.WegwClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class WegwClientConfig {




    @Autowired
    private WegwClient wegwClient;



    private Logger logger = LoggerFactory.getLogger(WegwClientConfig.class);


    /**
     * 关闭项目的时候，销毁NettyServer
     */
    @PreDestroy
    public void destroy() {
        wegwClient.destroy();
        logger.info("WegwClientConfig destroy ");
    }


    /**
     * 启动项目的时候，带起nettyServer
     */
    @PostConstruct
    public void startServer() {
        new Thread(){
            public void run() {
                try {
                    wegwClient.create();
                    logger.info("WegwClientConfig.run");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
