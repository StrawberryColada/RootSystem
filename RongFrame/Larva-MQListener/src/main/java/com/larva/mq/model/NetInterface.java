package com.larva.mq.model;

public class NetInterface {
	private String name;// 网络设备名 
	private String address;// IP地址  
	private String netmask;// 子网掩码  
	private String rxPackets;// 接收的总包裹数  
	private String txPackets;// 发送的总包裹数
	private String rxBytes;// 接收到的总字节数  
	private String txBytes;// 发送的总字节数  
	private String rxErrors;// 接收到的错误包数 
	private String txErrors;// 发送数据包时的错误数 
	private String rxDropped;// 接收时丢弃的包数  
	private String txDropped;// 发送时丢弃的包数 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNetmask() {
		return netmask;
	}
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}
	public String getRxPackets() {
		return rxPackets;
	}
	public void setRxPackets(String rxPackets) {
		this.rxPackets = rxPackets;
	}
	public String getTxPackets() {
		return txPackets;
	}
	public void setTxPackets(String txPackets) {
		this.txPackets = txPackets;
	}
	public String getRxBytes() {
		return rxBytes;
	}
	public void setRxBytes(String rxBytes) {
		this.rxBytes = rxBytes;
	}
	public String getTxBytes() {
		return txBytes;
	}
	public void setTxBytes(String txBytes) {
		this.txBytes = txBytes;
	}
	public String getRxErrors() {
		return rxErrors;
	}
	public void setRxErrors(String rxErrors) {
		this.rxErrors = rxErrors;
	}
	public String getTxErrors() {
		return txErrors;
	}
	public void setTxErrors(String txErrors) {
		this.txErrors = txErrors;
	}
	public String getRxDropped() {
		return rxDropped;
	}
	public void setRxDropped(String rxDropped) {
		this.rxDropped = rxDropped;
	}
	public String getTxDropped() {
		return txDropped;
	}
	public void setTxDropped(String txDropped) {
		this.txDropped = txDropped;
	}
	
}
