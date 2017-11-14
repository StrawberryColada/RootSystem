package com.larva.mq.utils;

public interface Constants {
	public final static String DATABASE_NAME="LARVA";
	public final static String TABLE_K_INSTANCES_NAME="K_INSTANCES";
	public final static String TABLE_K_VERSIONS_NAME="K_VERSIONS";
	
	public enum VERSIONTYPE{  
	    MQ_MAIN("0");
		private final String value;

		private VERSIONTYPE(String value) {
            this.value = value;
        }  
        public String getValue(){  
            return this.value;  
        }  
	} 
}
