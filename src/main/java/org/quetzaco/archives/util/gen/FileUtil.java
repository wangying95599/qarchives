package org.quetzaco.archives.util.gen;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static void main(String[] args) {
		
	}
	
	public static final String NEW_LINE="\r\n";
	
	
	   public static List<String> readFileByLines(String fileName) {  
	    	String charsetName="UTF-8";
	    	List<String> buf = new ArrayList<String>();

	        BufferedReader reader = null;  

	        try {  

	            System.out.println("以行为单位读取文件内容，一次读一整行：");  
	            
	            InputStreamReader insReader = new InputStreamReader(  
	                    new FileInputStream(fileName), charsetName);  

	            reader = new BufferedReader(insReader);  

	            String tempString = null;  

	            int line = 1;  

	            // 一次读入一行，直到读入null为文件结束  

	            while ((tempString = reader.readLine()) != null) {  

	                // 显示行号  

	                buf.add(tempString);  

	                line++;  

	            }  

	        } catch (IOException e) {  

	            e.printStackTrace();  

	        } finally {  
	            if (reader != null) {  
	                try {  
	                    reader.close();  

	                } catch (IOException e1) {  

	                }  
	            }  
	        }  
	        return buf;
	    }  
	
    public static String readFileByString(String fileName) {  
    	String charsetName="UTF-8";
    	StringBuffer buf = new StringBuffer();

        BufferedReader reader = null;  

        try {  

            System.out.println("以行为单位读取文件内容，一次读一整行：");  
            
            InputStreamReader insReader = new InputStreamReader(  
                    new FileInputStream(fileName), charsetName);  

            reader = new BufferedReader(insReader);  

            String tempString = null;  

            int line = 1;  

            // 一次读入一行，直到读入null为文件结束  

            while ((tempString = reader.readLine()) != null) {  

                // 显示行号  

                buf.append(tempString+NEW_LINE);  

                line++;  

            }  

        } catch (IOException e) {  

            e.printStackTrace();  

        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  

                } catch (IOException e1) {  

                }  
            }  
        }  
        return buf.toString();
    }  
    
	
	public static void writeFileByStringBuffer(String fileName, String content) {

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
          
            fos = new FileOutputStream(new File(fileName));
            bos = new BufferedOutputStream(fos);
            
            long begin0 = System.currentTimeMillis();
            
            bos.write(content.getBytes());
            bos.flush();
            bos.close();
            
            long end0 = System.currentTimeMillis();
            System.out.println(fileName+" 执行耗时:" + (end0 - begin0) + " 毫秒");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
