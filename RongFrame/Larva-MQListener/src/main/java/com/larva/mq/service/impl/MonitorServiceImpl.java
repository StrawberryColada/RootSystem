package com.larva.mq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.mq.dao.IMonitorDao;
import com.larva.mq.model.K_Instances;
import com.larva.mq.model.K_Versions;
import com.larva.mq.service.IMonitorService;
import com.larva.mq.utils.Constants;

/**
 * Created by sxjun on 15-9-10.
 */
@Service("monitorService")
public class MonitorServiceImpl implements IMonitorService {
    @Resource
    private IMonitorDao monitorDao;

	@Override
	public boolean createTables() {
		boolean isExist = false;
		try {
			if(monitorDao.get_k_table(Constants.DATABASE_NAME, Constants.TABLE_K_INSTANCES_NAME)==0)
				monitorDao.create_k_instances();
			if(monitorDao.get_k_table(Constants.DATABASE_NAME, Constants.TABLE_K_VERSIONS_NAME)==0)
				monitorDao.create_k_versions();
			isExist = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}

	@Override
	public K_Instances selectInstanceByHostname(String hostname) {
	    return monitorDao.selectInstanceByHostname(hostname);
	}

	@Override
	public void updateInstance(K_Instances instance) {
		monitorDao.updateInstance(instance);
	}

	@Override
	public void updateVersion(String instance_id, String version_type, Long version) {
		monitorDao.updateVersion(instance_id, version_type, version);
	}

	@Override
	public void insertInstance(K_Instances instance) {
		monitorDao.insertInstance(instance);
	}

	@Override
	public void insertVersion(K_Versions version) {
		monitorDao.insertVersion(version);
	}

	@Override
	public List<K_Versions> selectVersionsByInstanceId(String instance_id) {
		return monitorDao.selectVersionsByInstanceId(instance_id);
	}
    
}
