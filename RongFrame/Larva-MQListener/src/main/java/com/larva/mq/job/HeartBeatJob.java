package com.larva.mq.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.larva.mq.listener.HeartBeatSender;
import com.larva.mq.model.HeartBeat;
import com.larva.mq.model.K_Instances;
import com.larva.mq.model.K_Versions;
import com.larva.mq.model.SigarInfo;
import com.larva.mq.service.IMonitorService;
import com.larva.mq.utils.ConvertUtils;
import com.larva.mq.utils.Constants.VERSIONTYPE;

public class HeartBeatJob {
	private static final Logger log = LoggerFactory.getLogger(HeartBeatJob.class);

	private static HeartBeat heartBeat;
	
	@Value("${larva.quartz.heartbeat}")
    private String larva_quartz_heartbeat;
	
	@Resource
	private HeartBeatSender heartBeatSender;
	
	@Resource
	private IMonitorService monitorService;
	
	public void doJob() {  
		try {
			if(heartBeat == null){
				String hostname = ConvertUtils.getHostname();
				String cpuCores = String.valueOf(SigarInfo.getCPUCores());
				String cpuCounts = String.valueOf(SigarInfo.getCPUCounts());
				String memTotal = String.valueOf(SigarInfo.getMemTotal());
				heartBeat = new HeartBeat();
				heartBeat.setHostname(hostname);
				heartBeat.setCpuCores(cpuCores);
				heartBeat.setCpuCounts(cpuCounts);
				heartBeat.setMemTotal(memTotal);
				heartBeat.setInterval(larva_quartz_heartbeat);
				heartBeat.setNoticetime(System.currentTimeMillis());
				
				K_Instances instance = monitorService.selectInstanceByHostname(hostname);
				String instance_id = UUID.randomUUID().toString();
				if(instance!=null){//1。查询数据库实例，如果存在则跟本机配置比较，不一致则修改版本好
					instance_id = instance.getId();
					boolean isChange = false;
					if(!cpuCores.equals(instance.getCpuCores()))
						isChange =true;
					else if(!cpuCounts.equals(instance.getCpuCounts()))
						isChange =true;
					else if(!memTotal.equals(instance.getMemTotal()))
						isChange =true;
					if(isChange){
						Long version = System.currentTimeMillis();
						instance.setCpuCores(cpuCores);
						instance.setCpuCounts(cpuCounts);
						instance.setMemTotal(memTotal);
						//1。1 更新实例且更新版本
						monitorService.updateInstance(instance);
						monitorService.updateVersion(instance.getId(),VERSIONTYPE.MQ_MAIN.getValue(),version);
					}
				}else{//2.查询数据库实例，如果不存在则新增实例
					instance = new K_Instances();
					instance.setId(instance_id);
					instance.setCpuCores(cpuCores);
					instance.setCpuCounts(cpuCounts);
					instance.setHostname(hostname);
					instance.setMemTotal(memTotal);
					monitorService.insertInstance(instance);
					
					K_Versions k_version = new K_Versions();
					k_version.setId(UUID.randomUUID().toString());
					k_version.setInstance_id(instance_id);
					k_version.setVersion(System.currentTimeMillis());
					k_version.setVersion_name(hostname+"-客户端版本");
					k_version.setVersion_type(VERSIONTYPE.MQ_MAIN.getValue());
					
					monitorService.insertVersion(k_version);
				}
				//3.获取版本信息
				List<K_Versions> versions = monitorService.selectVersionsByInstanceId(instance_id);
				Map<String,Long> m = new HashMap<String,Long>();
				for(K_Versions v : versions){
					m.put(v.getVersion_type(), v.getVersion());
				}
				heartBeat.setId(instance_id);
				heartBeat.setVersion(m);
			}
			heartBeat.setCpuPercent(String.valueOf(SigarInfo.getCPUPercent()));
			heartBeat.setMemPercent(String.valueOf(SigarInfo.getMemPercent()));
			heartBeatSender.sendHeatBeat(heartBeat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args) {
	ConnectionFactory cf = new CachingConnectionFactory();
	  // set up the queue, exchange, binding on the broker
	  RabbitAdmin admin = new RabbitAdmin(cf);
	  Queue queue = new Queue("mouse.mq.queue");
	  admin.declareQueue(queue);
	  TopicExchange exchange = new TopicExchange("mouse.mq.exchange");
	  admin.declareExchange(exchange);
	  admin.declareBinding(
	  BindingBuilder.bind(queue).to(exchange).with("foo.*"));
	  // set up the listener and container
	  SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
	    Object listener = new Object() {
	    public void handleMessage(String foo) {
	      System.out.println(foo);
	    }
	  };
	  MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
	  container.setMessageListener(adapter);
	  container.setQueueNames("mouse.mq.queue");
	  container.start();
	  // send something
	  RabbitTemplate template = new RabbitTemplate(cf);
	  template.convertAndSend("mouse.mq.exchange", "foo.bar", "Hello, world!");
	  container.stop();
	}*/
}
