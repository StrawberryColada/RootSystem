package com.larva.mq.model;

public class Os {
	private String arch;// 操作系统内核类型如： 386、486、586等x86
	private String VendorName;// 操作系统名称  
	public String getArch() {
		return arch;
	}
	public void setArch(String arch) {
		this.arch = arch;
	}
	public String getVendorName() {
		return VendorName;
	}
	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}
	
}
