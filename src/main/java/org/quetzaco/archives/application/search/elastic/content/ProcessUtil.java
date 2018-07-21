/**
 * Copyright (c) 2004-2011 Deya Tech Inc. All rights reserved.
 */
package org.quetzaco.archives.application.search.elastic.content;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.quetzaco.archives.application.biz.Impl.RecordServiceImpl;

/**
 * 
 * JAVA Process Utility.
 * 
 * @author Tony Liu
 * @version 1.0 2011/08/19
 */
public class ProcessUtil {
	final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RecordServiceImpl.class);
	private int exitValue;
	private String errStr;
	
	private long timeout = 0;
	

	
	/**
	 * Set the process timeout
	 * @param timeout
	 */
	public void setTimeout(long timeout){
		this.timeout = timeout;
	}

	/**
	 * Non-block system process execute to instead JAVA Process.waitFor().
	 * @return exitValue.
	 */
	public int waitFor(Process p) {
		exitValue = -1; // returned to caller when p is finished

		InputStream err = p.getErrorStream();
		InputStream in = p.getInputStream();
		try {

			boolean finished = false; // Set to true when p is finished
			
			
			ProcessInfoOutputStream pin = new ProcessInfoOutputStream(in);
			new Thread(pin).start();
			
			ProcessErrorOutputStream per = new ProcessErrorOutputStream(err);
			new Thread(per).start();
			
			
			long cnt = 0;
			while(!finished) {
				
				try {
					
										
					//Ask the process for its exitValue. If the process
					//is not finished, an IllegalThreadStateException
					//is thrown. If it is finished, we fall through and
					//the variable finished is set to true.
					
					
					
					exitValue = p.exitValue();

					finished = true;
				}
				catch (IllegalThreadStateException e) {
					//Process is not finished yet;
					//Sleep a little to save on CPU cycles
					
					Thread.currentThread();
					Thread.sleep(500);
					cnt = cnt + 500;
					if( timeout!=0 && cnt > timeout ){ //blocking if TIMEOUT=0
						LOGGER.debug("!!Process execute timeout!!");
						break;
					}
				}
			}

			
			/*
			long cnt = 0;
			while(!finished) {
				
				try {
					
					StringBuffer sb= new StringBuffer();
					while( in.available() > 0) {
						//Print the output of our system call
						//mencoder的控制台输出信息很多，把缓存区所有的空间占满了，所以程序不能执行后面的程序，
						//mencoder就只能转码28秒的视频，所以需要建立线程及时清空缓存区。
						//如果还有问题，用线程来输出信息试试看
						InputStreamReader isr = new InputStreamReader(in);
			            BufferedReader br = new BufferedReader(isr);
						String line = null; 
			            while ((line = br.readLine()) != null) {
			                System.out.println( line);
			            }
						
					}
					while( err.available() > 0) {
						//Print the output of our system call
						InputStreamReader isr = new InputStreamReader(err);
			            BufferedReader br = new BufferedReader(isr);
						String line = null; 
			            while ((line = br.readLine()) != null) {
			            	sb.append(line);
			                System.out.println( line);
			            }
					}

					if(sb.length()>1){
						errStr = sb.toString();
					}
					
					//Ask the process for its exitValue. If the process
					//is not finished, an IllegalThreadStateException
					//is thrown. If it is finished, we fall through and
					//the variable finished is set to true.
					
					
					
					exitValue = p.exitValue();

					finished = true;
				}
				catch (IllegalThreadStateException e) {
					//Process is not finished yet;
					//Sleep a little to save on CPU cycles
					Thread.currentThread();
					Thread.sleep(100);
					cnt = cnt + 100;
					if( timeout!=0 && cnt > timeout ){ //blocking if TIMEOUT=0
						break;
					}
				}
			}
			*/
		}
		catch (Exception e) {
			//unexpected exception! print it out for debugging...
			LOGGER.debug( " unexpected exception - " +e.getMessage());
		}finally {
			try {
				p.destroy();
				System.gc();
			} catch (Exception e) {
				LOGGER.debug(" unexpected exception while close error inputstream- "	+ e.getMessage());
			}
		}

		return exitValue;
	}

	/**
	 * Get system process execute error message.
	 * @return Error message return from system process.
	 */
	public String getErrInfo(){
		return errStr;
	}

	
	class ProcessInfoOutputStream implements Runnable{

		private InputStream in;
		
		public ProcessInfoOutputStream(InputStream in){
			this.in = in;
			
		}
		
		
		@Override
		public void run() {

			try{

				//Print the output of our system call
				//mencoder的控制台输出信息很多，把缓存区所有的空间占满了，所以程序不能执行后面的程序，
				//mencoder就只能转码28秒的视频，所以需要建立线程及时清空缓存区。
				//如果还有问题，用线程来输出信息试试看
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				String line = null; 
				while ((line = br.readLine()) != null) {
					//System.out.println("INFO > " + line);
				}
				in.close();

			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	class ProcessErrorOutputStream implements Runnable{

		private InputStream in;
		
		
		public ProcessErrorOutputStream(InputStream in){
			this.in = in;
		
		}
		
		
		
		@Override
		public void run() {

			try{
				StringBuffer sb= new StringBuffer();

				//Print the output of our system call
				//mencoder的控制台输出信息很多，把缓存区所有的空间占满了，所以程序不能执行后面的程序，
				//mencoder就只能转码28秒的视频，所以需要建立线程及时清空缓存区。
				//如果还有问题，用线程来输出信息试试看
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				String line = null; 
				while ((line = br.readLine()) != null) {
					sb.append(line);
					//System.out.println("ERR > " + line);
				}
				
				if(sb.length()>1){
					errStr = sb.toString();
				}

				in.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		
			
		}
		
	}
	

}
