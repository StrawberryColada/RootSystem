package com.larva.mq.model;

public class K_Instances {
	private String id;
	private String hostname;
	private String cpuCounts;
	private String cpuCores;
	private String memTotal;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public String getCpuCounts() {
		return cpuCounts;
	}
	public void setCpuCounts(String cpuCounts) {
		this.cpuCounts = cpuCounts;
	}
	
	public String getCpuCores() {
		return cpuCores;
	}
	public void setCpuCores(String cpuCores) {
		this.cpuCores = cpuCores;
	}
	
	public String getMemTotal() {
		return memTotal;
	}
	public void setMemTotal(String memTotal) {
		this.memTotal = memTotal;
	}
	
}
