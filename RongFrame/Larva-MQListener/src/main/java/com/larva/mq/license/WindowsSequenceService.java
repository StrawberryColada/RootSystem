package com.larva.mq.license;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


/** 
 *在Windows平台上生成机器码 
* @author 杨尚川 
*/  
public final class WindowsSequenceService extends AbstractSequenceService{      
    @Override  
    public String getSequence() {          
        String cpuID=getCPUSerial();  
        String hdID=getHDSerial("C");  
        if(cpuID==null || hdID==null){  
            return null;  
        }  
        String machineCode = getMD5(cpuID+hdID);  
                  
        return machineCode;  
    }  
      
    /** 
     * 
     * @param drive 硬盘驱动器分区 如C,D 
     * @return 该分区的卷标 
     */  
    private String getHDSerial(String drive) {  
    	String result = "";
        try {
          File file = File.createTempFile("realhowto",".vbs");
          file.deleteOnExit();
          FileWriter fw = new java.io.FileWriter(file);

          String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                      +"Set colDrives = objFSO.Drives\n"
                      +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                      +"Wscript.Echo objDrive.SerialNumber";  // see note
          fw.write(vbs);
          fw.close();
          java.lang.Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
          BufferedReader input =
            new BufferedReader
              (new InputStreamReader(p.getInputStream()));
          String line;
          while ((line = input.readLine()) != null) {
             result += line;
          }
          input.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
  
        return result.toString();  
    }  
  
    /** 
     * 获取CPU号,多CPU时,只取第一个 
     * @return 
     */  
    private String getCPUSerial() {  
    	String serial = null;
    	try {
			java.lang.Process process = Runtime.getRuntime().exec(
			        new String[] { "wmic", "cpu", "get", "ProcessorId" });
			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			String property = sc.next();
			serial = sc.next();
			//System.out.println(property + ": " + serial);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return serial;  
    }  
      
    public static void main(String[] args) {          
       SequenceService s = new WindowsSequenceService();  
        String seq = s.getSequence();  
        System.out.println(seq);
    }  
}  

