package com.larva.mq.service;

import java.util.List;

import com.larva.mq.model.K_Instances;
import com.larva.mq.model.K_Versions;

/**
 * Created by sxjun on 15-9-10.
 */
public interface IMonitorService {
	boolean createTables();

	K_Instances selectInstanceByHostname(String hostname);

	void updateInstance(K_Instances instance);

	void updateVersion(String instance_id, String version_type, Long version);

	void insertInstance(K_Instances instance);

	void insertVersion(K_Versions version);

	List<K_Versions> selectVersionsByInstanceId(String instance_id);
}
