package org.quetzaco.archives.application.search.elastic.content;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Timestamp;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.quetzaco.archives.application.biz.Impl.RecordServiceImpl;
import org.quetzaco.server.utils.Configuration;

public class FileUtils {
	final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RecordServiceImpl.class);

	public String getFileContent(String actualFile) {
		Reader reader = null;
		InputStream stream = null;
		String text = null;
		String extension = actualFile
				.substring(actualFile.lastIndexOf(".") + 1);
		try {
			if (extension.equalsIgnoreCase("zip")
					|| extension.equalsIgnoreCase("rtf")
					|| extension.equalsIgnoreCase("dat")
					|| extension.equalsIgnoreCase("mpg")
					|| extension.equalsIgnoreCase("mpeg")
					|| extension.equalsIgnoreCase("mov")
					|| extension.equalsIgnoreCase("mob")
					|| extension.equalsIgnoreCase("rar")
					|| extension.equalsIgnoreCase("exe")) { 
				LOGGER.debug(
						"Not indexing the file as they are not text file");
				text = null;
			} else if (extension.equalsIgnoreCase("doc")
					|| extension.equalsIgnoreCase("docx")
					|| extension.equalsIgnoreCase("dot")
					|| extension.equalsIgnoreCase("dotx")
					|| extension.equalsIgnoreCase("xls")
					|| extension.equalsIgnoreCase("xlsx")
					|| extension.equalsIgnoreCase("ppt")
					|| extension.equalsIgnoreCase("pptx")
					|| extension.equalsIgnoreCase("pps")
					|| extension.equalsIgnoreCase("ppsx")) {
				// modified by Tony Liu 2010-7-16
				text = getTextFromDocument(actualFile);
				// return text;
			} else if (extension.equalsIgnoreCase("rtf")) {
				RTFEditorKit rtf=new RTFEditorKit();  
				DefaultStyledDocument dsd=new DefaultStyledDocument();  
			    try {  
			       	stream = new FileInputStream(new File(actualFile));
			        rtf.read(stream, dsd, 0);  
			        text = new String(dsd.getText(0, dsd.getLength()));  
			    } catch (FileNotFoundException e) {  
			            e.printStackTrace();  
			    } catch (IOException e) {  
			            e.printStackTrace();  
			    } catch (BadLocationException e) {  
			            e.printStackTrace();  
			    }  
			} else if (extension.equalsIgnoreCase("pdf")) {

				text = getTextFromDocument(actualFile);
			} else if (extension.equalsIgnoreCase("msg")) {
				text = getTextFromDocument(actualFile);
			} else if (extension.equalsIgnoreCase("htm")
					|| extension.equalsIgnoreCase("html")) {
				text = getTextFromDocument(actualFile);
			} else if (extension.equalsIgnoreCase("txt")
					|| extension.equalsIgnoreCase("eml")
					|| extension.equalsIgnoreCase("java")
					|| extension.equalsIgnoreCase("h")
					|| extension.equalsIgnoreCase("c")
					|| extension.equalsIgnoreCase("cpp")) {
					text = getTextFromDocument(actualFile);

			} else if (extension.equalsIgnoreCase("jpg")) { // added by Tony Liu
															// 2010-1-7 For Exif
															// and ocr
				//TODO
				FileContent fc = new FileContent();
				text = fc.getExifFromJPG(actualFile) ;
			} else if (extension.equalsIgnoreCase("gif")
					|| extension.equalsIgnoreCase("bmp")
					|| extension.equalsIgnoreCase("jpeg")
					|| extension.equalsIgnoreCase("png")
					|| extension.equalsIgnoreCase("tif")
					|| extension.equalsIgnoreCase("tiff")) {
					text = OCRUtil.getText(actualFile);
			} else {
				LOGGER.debug(
						"Unrecognized file type , will ignore...");
				return null;
			}

		} catch (Exception e) {
			LOGGER.debug("getFileContent ---------Exception");
			
			e.printStackTrace();
		} finally{
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return text;
	}

	private  String getTextFromDocument(String actualFile) {
		String text = null;
		boolean finished = false;
		long startTime = System.currentTimeMillis();
		
		int timeOut = Integer.parseInt(Configuration
				.getProperty("TIMEOUT_GETTEXT_FROM_DOCUMENT_FOR_INDEX"));
		
		/*
		 * 重新启动一个线程，永远读取文档内容，主线程监控，如果文档读取内容超时，主线成将杀死文档读取内容的线程，并继续运行
		 */
		
		FileContentThread ict = new FileContentThread(actualFile);
		ict.start();
		
		while (!finished) {
			finished = ict.isFinished();
			if (finished) {
				text = ict.getText();
				break;
			} 
			long time = System.currentTimeMillis()-startTime;
			if (time < timeOut) {
				try {
					Thread.currentThread().sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				ict.cleanReader();
				ict.stop();
				ict = null;
				LOGGER.debug(
						"The file " + actualFile + " get text failed");
				text = null;
				break;
			}
		}
		if (ict != null) {
			ict.cleanReader();
			ict = null;
		}
		LOGGER.debug(
				"The File " + actualFile + " used time is " + (System.currentTimeMillis()-startTime));
		return text;
	}

	public static String getFileEncoding(File file) {
		String encoding = "";
		try {
			encoding = CharsetDetector.getInstance().detectEncoding(file);
			// added by ryan 2009-09-08
			if (encoding.toLowerCase().startsWith("gb"))
				encoding = "GBK";
			// end
			if (encoding.equalsIgnoreCase("OTHER"))
				encoding = "UTF-8";
		} catch (Exception e) {
			encoding = "UTF-8";
		}
		LOGGER.debug(
				"file:" + file.getAbsolutePath() + " 's enconding is "
						+ encoding);
		return encoding;

	}
	
}
