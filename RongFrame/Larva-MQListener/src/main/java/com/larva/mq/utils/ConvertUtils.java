package com.larva.mq.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 
 * @author sxjun
 */ 
 public class ConvertUtils { 
     public static String byte2HexString(byte[] b) { 
         char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', 
             '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}; 
         char[] newChar = new char[b.length * 2]; 
         for (int i = 0; i < b.length; i++) { 
             newChar[2 * i] = hex[(b[i] & 0xf0) >> 4]; 
             newChar[2 * i + 1] = hex[b[i] & 0xf]; 
         } 
         return new String(newChar); 
     } 
     
     public static String getHostname(){  
         String host = null;  
         try  
         {  
             InetAddress ia = InetAddress.getLocalHost();   
             host = ia.getHostName();//获取计算机主机名  
         }catch(UnknownHostException e){  
             e.printStackTrace();  
         }  
         return host;
     }  
 
 } 
