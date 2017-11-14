package com.larva.mq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.larva.mq.model.K_Instances;
import com.larva.mq.model.K_Versions;

@Component("monitorMapper")
public interface MonitorMapper {
	
	int create_k_instances();
	
    int create_k_versions();
    
    int get_k_table(@Param("database") String database,@Param("tablename") String tablename);

    K_Instances selectInstanceByHostname(@Param("hostname") String hostname);

	void updateInstance(K_Instances instance);

	void updateVersion(@Param("instance_id") String instance_id,@Param("version_type") String version_type,@Param("version") Long version);

	void insertInstance(K_Instances instance);

	void insertVersion(K_Versions version);

	List<K_Versions> selectVersionsByInstanceId(@Param("instance_id") String instance_id);
    
}