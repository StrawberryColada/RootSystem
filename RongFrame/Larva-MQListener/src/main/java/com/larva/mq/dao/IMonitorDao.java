package com.larva.mq.dao;

import java.util.List;

import com.larva.mq.model.K_Instances;
import com.larva.mq.model.K_Versions;

/**
 * Created by sxjun on 15-9-10.
 */
public interface IMonitorDao {

    int create_k_instances();
    
    int create_k_versions();
    
    int get_k_table(String database,String tablename);

    K_Instances selectInstanceByHostname(String hostname);

	void updateInstance(K_Instances instance);

	void updateVersion(String instance_id, String version_type, Long version);

	void insertInstance(K_Instances instance);

	void insertVersion(K_Versions version);

	List<K_Versions> selectVersionsByInstanceId(String instance_id);
}
