package com.larva.mq.model;

import java.io.Serializable;

public class IOStat implements Serializable{
	private static final long serialVersionUID = 1L;
	private String filesystem;
	private String reads;
	private String writes;
	private String r_bytes;
	private String w_bytes;
	
	public String getFilesystem() {
		return filesystem;
	}
	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}
	public String getReads() {
		return reads;
	}
	public void setReads(String reads) {
		this.reads = reads;
	}
	public String getWrites() {
		return writes;
	}
	public void setWrites(String writes) {
		this.writes = writes;
	}
	public String getR_bytes() {
		return r_bytes;
	}
	public void setR_bytes(String r_bytes) {
		this.r_bytes = r_bytes;
	}
	public String getW_bytes() {
		return w_bytes;
	}
	public void setW_bytes(String w_bytes) {
		this.w_bytes = w_bytes;
	}
	

}
