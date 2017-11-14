package com.larva.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.larva.mq.service.IMonitorService;


/**
 * Created by sxjun on 15-9-9.
 */
public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        IMonitorService monitorService = (IMonitorService) context.getBean("monitorService");
        boolean isExist = monitorService.createTables();
        if(isExist){
        	log.info("数据库初始化成功");
        }else{
        	log.error("数据库初始化失败");
        }
        log.info("启动成功");
    }
}
