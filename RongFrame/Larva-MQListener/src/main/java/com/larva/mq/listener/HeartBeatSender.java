package com.larva.mq.listener;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.larva.mq.utils.StrKit;

public class HeartBeatSender {
	@Value("${larva.mq.queue.heartbeat}")
    private String larva_mq_queue_heartbeat;
	
	private final static String LARVA_MQ_SUFFIX="_key";
	@Resource
    private AmqpTemplate amqpTemplate;
	
	public void sendHeatBeat(Object obj){
		amqpTemplate.convertAndSend(getMouse_mq_queue_heartbeat(), obj);
	}
	
	public String getMouse_mq_queue_heartbeat() {
		if(StrKit.notBlank(this.larva_mq_queue_heartbeat))
			return larva_mq_queue_heartbeat+LARVA_MQ_SUFFIX;
		return null;
	}
	
}
