package com.larva.mq.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.mq.dao.IMonitorDao;
import com.larva.mq.mapper.MonitorMapper;
import com.larva.mq.model.K_Instances;
import com.larva.mq.model.K_Versions;

/**
 * Created by sxjun on 15-9-10.
 */
@Repository("monitorDao")
public class MonitorDaoImpl implements IMonitorDao {
    @Resource
    private MonitorMapper monitorMapper;

    @Override
    public int create_k_instances() {
    	return monitorMapper.create_k_instances();
    }
    
	@Override
	public int create_k_versions() {
		return monitorMapper.create_k_versions();
	}

	@Override
	public int get_k_table(String database,String tablename) {
		return monitorMapper.get_k_table(database,tablename);
	}

	@Override
	public K_Instances selectInstanceByHostname(String hostname) {
		return monitorMapper.selectInstanceByHostname(hostname);
	}

	@Override
	public void updateInstance(K_Instances instance) {
		monitorMapper.updateInstance(instance);
	}

	@Override
	public void updateVersion(String instance_id, String version_type, Long version) {
		monitorMapper.updateVersion(instance_id, version_type, version);
	}

	@Override
	public void insertInstance(K_Instances instance) {
		monitorMapper.insertInstance(instance);
	}

	@Override
	public void insertVersion(K_Versions version) {
		monitorMapper.insertVersion(version);
	}

	@Override
	public List<K_Versions> selectVersionsByInstanceId(String instance_id) {
		return monitorMapper.selectVersionsByInstanceId(instance_id);
	}
}
