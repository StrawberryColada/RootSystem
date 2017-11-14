package com.larva.mq.model;

import java.util.ArrayList;
import java.util.List;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.cmd.Ps;

public class SigarInfo {
	private static Sigar sigar = new Sigar();
	
	/**
	 * 获取cpu占用率
	 * @return
	 */
	public static double getCPUPercent(){
		CpuPerc perc = null;
		try {
			perc = sigar.getCpuPerc();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return perc.getCombined()*100;
	}
	
	/**
	 * 获取cpu数目
	 * @return
	 */
	public static int getCPUCounts(){
		 CpuPerc[] cpus = null;
		try {
			cpus = sigar.getCpuPercList();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return cpus.length;
	}
	
	/**
	 * 内核数
	 * @return
	 */
	public static int getCPUCores(){
		CpuInfo[] infos = null;
		int cpuCores = 0;
		try {
			infos = sigar.getCpuInfoList();
			for(CpuInfo cpu : infos){
				cpuCores += cpu.getTotalCores();
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return cpuCores;
	}
	
	/**
	 * 内存使用率
	 * @return
	 */
	public static double getMemPercent(){
		Mem mem = null;
		try {
			mem = sigar.getMem();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return mem.getUsedPercent();
	}
	
	/**
	 * 内存大小
	 * @return
	 */
	public static String getMemTotal(){
		Mem mem = null;
		try {
			mem = sigar.getMem();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return Sigar.formatSize(mem.getRam());
	}
	
	/**
	 * 获取文件系统
	 * @param args
	 */
	public static FileSystem[] getFSDevName(){
		try {
			return sigar.getFileSystemList();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取IO
	 * @param args
	 */
	public static List<IOStat> getIOStat(FileSystem[] fslist){
		List<IOStat> ioStats = new ArrayList<IOStat>();
		try {
			for (FileSystem fs : fslist) {
                if (fs.getType() == FileSystem.TYPE_LOCAL_DISK) {
                	IOStat ioStat = new IOStat();
                	ioStat.setFilesystem(fs.getDevName());
                    FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
                    ioStat.setReads(String.valueOf(usage.getDiskReads()));
                    ioStat.setWrites(String.valueOf(usage.getDiskWrites()));
                    ioStat.setR_bytes(Sigar.formatSize(usage.getDiskReadBytes()));
                    ioStat.setW_bytes(Sigar.formatSize(usage.getDiskWriteBytes()));
                    ioStats.add(ioStat);
                }
            }
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return ioStats;
	}
	
	/**
	 * 获取硬盘大小
	 * @param fslist
	 * @return
	 */
	public static List<Df> getDf(FileSystem[] fslist){
		 List<Df> dfs = new ArrayList<Df>();
		 for (FileSystem fs : fslist) {
			 FileSystemUsage usage;
	            if (fs instanceof NfsFileSystem) {
	                NfsFileSystem nfs = (NfsFileSystem)fs;
	                if (!nfs.ping()) {
	                    return null;
	                }
	            }
	            Df df = new Df();
	            df.setFilesystem(fs.getDevName());
	            try {
					usage = sigar.getFileSystemUsage(fs.getDirName());
					df.setUsed(Sigar.formatSize((usage.getTotal() - usage.getFree())* 1024));
					df.setAvail(Sigar.formatSize(usage.getAvail()* 1024));
					df.setSize(Sigar.formatSize(usage.getTotal()* 1024));
					df.setUse(usage.getUsePercent() * 100+"%");
					df.setType(fs.getSysTypeName() + "/" + fs.getTypeName());
				} catch (Exception e) {
					df.setUsed("0");
					df.setAvail("0");
					df.setSize("0");
					df.setUse("0%");
					df.setType(fs.getSysTypeName() + "/" + fs.getTypeName());
				}
	            dfs.add(df);
        }
		 return dfs;
	}

	/**
	 * 获取进程信息
	 * @return
	 */
   public static List<Process> getProcessInfo() {
         Ps ps = new Ps();
         List<Process> processInfos = new ArrayList<Process>();
         try {
             long[] pids = sigar.getProcList();
             for (long pid : pids) {
                 List<String> list = ps.getInfo(sigar, pid);
                 Process info = new Process();
                 for (int i = 0; i <= list.size(); i++) {
                     switch (i) {
                     case 0:    info.setPid(list.get(0));    break;
                     case 1: info.setUser(list.get(1));    break;
                     case 2: info.setStartTime(list.get(2));    break;
                     case 3:    info.setMemSize(list.get(3));    break;
                     case 4:    info.setMemUse(list.get(4));    break;
                     case 5:    info.setMemhare(list.get(5));    break;
                     case 6:    info.setState(list.get(6));    break;
                     case 7:    info.setCpuTime(list.get(7));    break;
                     case 8:    info.setName(list.get(8));    break;
                     }
                 }
                 processInfos.add(info);
             }
         } catch (SigarException e) {
             e.printStackTrace();
         }
         return processInfos;
     }
   
   /**
    * 获取操作系统信息
    * @return
    */
   public static Os getOs(){
	   Os os = new Os();
	   OperatingSystem operatingSystem = OperatingSystem.getInstance();  
	   os.setArch(operatingSystem.getArch());
	   os.setVendorName(operatingSystem.getVendorName());
	   return os;
   }
   
   /**
    * 列出网络流量
    * @return
    */
   public static List<NetInterface> getNetInterfaceStat(){
	   List<NetInterface> netInterfaces = new ArrayList<NetInterface>();
	   try {
		    NetInterface netInterface = new NetInterface();
			String ifNames[] = sigar.getNetInterfaceList();  
			for (int i = 0; i < ifNames.length; i++) {  
			    String name = ifNames[i];  
			    NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);  
			    netInterface.setName(name);
			    netInterface.setAddress(ifconfig.getAddress());
			    netInterface.setNetmask(ifconfig.getNetmask());
			    if ((ifconfig.getFlags() & 1L) <= 0L) {  
			    	System.out.println("!IFF_UP...skipping getNetInterfaceStat");  
			        continue;  
			    }  
			    try {  
			        NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);  
			        netInterface.setRxBytes(String.valueOf(ifstat.getRxBytes()));
			        netInterface.setRxDropped(String.valueOf(ifstat.getRxDropped()));
			        netInterface.setRxErrors(String.valueOf(ifstat.getRxErrors()));
			        netInterface.setRxPackets(String.valueOf(ifstat.getRxPackets()));
			        netInterface.setTxBytes(String.valueOf(ifstat.getTxBytes()));
			        netInterface.setTxDropped(String.valueOf(ifstat.getTxDropped()));
			        netInterface.setTxErrors(String.valueOf(ifstat.getTxErrors()));
			        netInterface.setTxPackets(String.valueOf(ifstat.getTxPackets()));
			        netInterfaces.add(netInterface);
			    } catch (SigarNotImplementedException e) {  
			    } catch (SigarException e) {  
			    	System.out.println(e.getMessage());  
			    }  
			}
		} catch (SigarException e) {
			e.printStackTrace();
		} 
	   return netInterfaces;
   }
}
