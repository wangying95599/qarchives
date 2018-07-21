package org.quetzaco.archives.application.search.elastic.content;

import org.apache.lucene.demo.html.HTMLParser;

import java.io.*;

public class FileContentThread extends Thread{
	private Reader reader = null;
	private InputStream inputStream = null;
	private BufferedReader breader = null;
	
	private boolean finished = false;
	private String actualFile = "";
	private String text = null;
	
	public boolean isFinished() {
		return finished;
	}

	public String getText() {
		return text;
	}

	public FileContentThread(String actualFile) {
		this.actualFile = actualFile;
	}
	
	public void run() {
		
		try{
			String extension = actualFile.substring(actualFile.lastIndexOf(".") + 1);
		 if(extension.equalsIgnoreCase("htm")
					|| extension.equalsIgnoreCase("html")
					|| extension.equalsIgnoreCase("form")) {
				
				text = getTextFromHTML(actualFile);
			}else if(extension.equalsIgnoreCase("txt")
					|| extension.equalsIgnoreCase("eml")
					|| extension.equalsIgnoreCase("java")
					|| extension.equalsIgnoreCase("h")
					|| extension.equalsIgnoreCase("c")
					|| extension.equalsIgnoreCase("cpp")) {
				text = getTextFromTXT(actualFile);
			}else{
				FileContent fc = new FileContent();
				text = fc.getText(actualFile);
				fc = null;
			}
		} catch(Exception ex){
			ex.printStackTrace();
		} finally{
			finished = true;
		}
	}
	
	
	private String getTextFromHTML(String actualFile) throws IOException{
		File file = new File(actualFile);
		inputStream = new FileInputStream(file);
		// reader = new
		// InputStreamReader(inputStream,IndexUtil.ENCODING);
//		reader = new InputStreamReader(inputStream, FileUtils
//				.getFileEncoding(file));
		reader = new InputStreamReader(inputStream);
		HTMLParser parser = new HTMLParser(reader);
		reader = (InputStreamReader) parser.getReader();
//		// added by ryan.dong 2008-07-02
		StringBuffer htmlContent = new StringBuffer("");
		breader = new BufferedReader(reader);
		String tempContent = null;
		while ((tempContent = breader.readLine()) != null) {
			htmlContent.append(tempContent);
		}
		text = htmlContent.toString();
		htmlContent = null;
		return text;
	}
	
	private String getTextFromTXT(String actualFile) throws IOException {
		File file = new File(actualFile);
		inputStream = new FileInputStream(file);
		// added by ryan.dong 2008-07-02
		StringBuffer textContent = new StringBuffer("");
		breader = new BufferedReader(new InputStreamReader(inputStream,FileUtils.getFileEncoding(file)));
//				FileUtils.getFileEncoding(file)));
		String tempContent = null;
		while ((tempContent = breader.readLine()) != null) {
			textContent.append(tempContent);
		}
		text = textContent.toString();
		textContent = null;
		return text;
	}
	
	public void cleanReader() {
		try {
			if(inputStream!=null) {
				inputStream.close();
			}
			if(reader != null) {
				reader.close();
			}
			if (breader != null)
				breader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
