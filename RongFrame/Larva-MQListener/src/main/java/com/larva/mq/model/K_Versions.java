package com.larva.mq.model;

public class K_Versions {
	private String id;
	private String instance_id;
	private String version_type;
	private String version_name;
	private Long version;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getInstance_id() {
		return instance_id;
	}
	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}
	
	public String getVersion_type() {
		return version_type;
	}
	public void setVersion_type(String version_type) {
		this.version_type = version_type;
	}
	
	public String getVersion_name() {
		return version_name;
	}
	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}
	
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
