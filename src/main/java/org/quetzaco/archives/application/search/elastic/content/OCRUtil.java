package org.quetzaco.archives.application.search.elastic.content;
import java.io.File;

import org.quetzaco.archives.application.biz.Impl.RecordServiceImpl;
import org.quetzaco.server.utils.Configuration;

public class OCRUtil {
	final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RecordServiceImpl.class);
	private static String tmpPath = getWorkPath();
	private static String language = Configuration.getProperty("OCR_LANGUAGE");
	private static String getWorkPath(){
		String work_path = Configuration.getProperty("WORK_PATH");
		if(work_path.equalsIgnoreCase(".")){
			return System.getProperty("root")+File.separatorChar+"work";
		}else{
			return work_path;
		}
		
	}
	private static String[] getLangs(){
		String[] langs = language.split(",");
		for(int i=0;i<langs.length;i++){
			if(langs[i].equalsIgnoreCase("zh_CN")){
				langs[i] = "chi_sim";
			}else if(langs[i].equalsIgnoreCase("zh_TW")){
				langs[i] = "chi_tra";
			}else if(langs[i].equalsIgnoreCase("en_US")){
				langs[i] = "eng";
			}else if(langs[i].equalsIgnoreCase("ja_JP")){
				langs[i] = "jpn";
			}
		}
		return langs;
	}
	
	public static String convertToTIF(String input){
		//String output = new RandomGUID().toString()+".tif";
		String output = input.substring(0,input.lastIndexOf("."))+".tif";
		if(input.toLowerCase().endsWith(".tiff")||
				input.toLowerCase().endsWith(".jpg")||
				input.toLowerCase().endsWith(".png")||
				input.toLowerCase().endsWith(".jpg")||
				input.toLowerCase().endsWith(".jpeg")){
			
			String conv_prog= ConvertProcessUtil.getToolCMD("IMGTOOLS_PATH","ImageMagick"+File.separatorChar+"bin","convert");
			//output = tmpPath + File.separator + output;
			String cmd = conv_prog+" "+input+" "+output;
			LOGGER.info("convertToTIF    method  cmd  :: "+cmd);
			try{
				Process p = Runtime.getRuntime().exec(cmd);
				ConvertProcessUtil pu = new ConvertProcessUtil();
				if (pu.waitFor(p) == 0){
					LOGGER.info("Success to convert "+input+" to "+output+".");
				}else{
					LOGGER.info("Fail to convert "+input+" to image.");
					FileUtil.copyFile(input,output);
				}
			}catch(Exception e){
				e.printStackTrace();
				FileUtil.copyFile(input,output);
			}
			
		}else{
			
			FileUtil.copyFile(input,output);
			
		}
		return output;
	}
	
	public static String extract(String input){
		String outTxt = "";
		String[]langs = getLangs();
		LOGGER.debug("extract::::::::langs.length:::: "+langs.length);
		for(int i=0;i<langs.length;i++){
			LOGGER.debug("extract::::::::"+i+"::::::: "+langs[i]);
			outTxt = outTxt + "\n" + extract(input,langs[i]);
			LOGGER.debug("outTxt::"+outTxt+"  "+i+"::::::: ");
		}
		return outTxt;
	}
	
	public static String extract(String input,String lang){
		String outTxt = "";
		try{
			String ocr_prog= ConvertProcessUtil.getToolCMD("OCR_PROG_PATH","tesseract"+File.separatorChar+"bin","tesseract");
			String output = input;
			input = convertToTIF(input);
			String cmd = ocr_prog+" "+input+" "+output+" -l "+lang;
			LOGGER.debug(cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			ConvertProcessUtil pu = new ConvertProcessUtil();
			if (pu.waitFor(p) == 0){
				LOGGER.info("Success to convert "+input+" to "+output+".");
				outTxt = FileUtil.readTxt(output+".txt","UTF-8");
				FileUtil.delFile(output+".txt");
				FileUtil.delFile(input);
			}else{
				LOGGER.info("Fail to convert "+input+" to image.");
			}
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("Fail to convert "+input+" to image.["+e.toString()+"]");
		}

		return outTxt;

	}
	
/*	public static String extractFromOCRServer(String filename){
		String text = "";
		String server = Configuration.getProperty("OCR_SERVER");
		int port = Integer.parseInt( Configuration.getProperty("OCR_PORT") );
		long timeout = Long.parseLong( Configuration.getProperty("OCR_TIMEOUT") );
		String tmpPath = ".";
		String[] langs = Configuration.getProperty("OCR_LANGUAGE").split(";");

		OCRClientHandler client = new OCRClientHandler(server,port, timeout,tmpPath);
		client.setLogger(LOGGER.getLogger());
		client.setOriginFile(new File(filename));
		client.setOriginType( filename.substring(filename.lastIndexOf(".")+1,filename.length()) );

		for(int i=0;i<langs.length;i++){
			if(!langs[i].equalsIgnoreCase("")){
				client.setLang(langs[i]);
				client.execute();
				text = text + "\n" + client.getText();
			}
		}
		return text;
	}*/
	
	public static String getText(String filename){
		String text = "";
		
		if (Configuration.getProperty("OCR_ENABLE").equalsIgnoreCase("ON")){

			/*String server = Configuration.getProperty("OCR_SERVER");
			if(server!=null && !server.equalsIgnoreCase("") ){
				extractFromOCRServer(filename);
			}else*/{
				text = extract(filename);
			}
		}
		
		return text;
	}

	
	
}
