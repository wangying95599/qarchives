package org.quetzaco.archives.util.gen;

import java.util.List;

/**
 * 替换时，为了防止误伤，加上前缀w。
 * 按钮的前缀:wbutton
 * 表单的前缀:wform
 * 
 * @author wangy
 *
 */
public class GenUtil {
	
	
	private static final String buttonFile_cancel="btn_cancel.html";
	private static final String buttonFile="btn.html";
	private static final String wbuttonFunctionName="wbuttonFunctionName";
	private static final String wbuttonName="wbuttonName";
	
	private static final String formFile="form.html";
	private static final String wformComment="wformComment";
	private static final String wformModal="wformModal";
	private static final String wformTitle="wformTitle";
	private static final String wformId="wformId";
	private static final String wformBody="wformBody";
	private static final String wformButton="wformButton";
	private static final String formFile_final="0finalform.html";
	
	private static final String formFile_input="form_input.html";
	private static final String winputLabel="winputLabel";
	private static final String wInputId="wInputId";
	private static final String winputName="winputName";
	
	private static final String formFile_area="form_area.html";
	private static final String wareaLabel="wareaLabel";
	private static final String wareaId="wareaId";
	private static final String wareaName="wareaName";
	
	private static final String formFile_date="form_date.html";
	private static final String wdateLabel="wdateLabel";
	private static final String wdateDivId="wdateDivId";
	private static final String wdateId="wdateId";
	private static final String wdateName="wdateName";
	
	private static final String formFile_select="form_select.html";
	private static final String wselectLabel="wselectLabel";
	private static final String wselectId="wselectId";
	private static final String wselectName="wselectName";
	
	private static final String talbeFile="table.html";
	private static final String wtableKey="wtableKey";
	private static final String wtableUpperKey="wtableUpperKey";
	private static final String wtableValue="wtableValue";
	private static final String wtableTitle="wtableTitle";
	private static final String tableFile_final="0finaltable.html";
	
	
	private static final String jsFile="func.js";
	private static final String jsColFile="func_col.js";
	private static final String jsMapperFile="func_mapper.js";
	private static final String wjsKey="wjsKey";
	private static final String wjsUpperKey="wjsUpperKey";
	private static final String wjsKeyId="wjsKeyId";
	private static final String wjsKeyName="wjsKeyName";
	private static final String wjsKeyValue="wjsKeyValue";
	private static final String wjsColBody="wjsColBody";
	private static final String wjsMapperBody="wjsMapperBody";
	private static final String jsFile_final="0finaljs.html";
	
	private static final String wcontrollerFile="Controller.html";
	private static final String wcontrollerKey="wcontrollerKey";
	private static final String wcontrollerUpperKey="wcontrollerUpperKey";
	private static final String controllerFile_final="Controller.java1";
	
	private static final String wserviceFile="Service.html";
	private static final String wserviceImplFile="ServiceImpl.html";
	private static final String wserviceKey="wserviceKey";
	private static final String wserviceUpperKey="wserviceUpperKey";
	private static final String serviceFile_final="Service.java1";
	private static final String serviceImplFile_final="ServiceImpl.java1";
	
	private static final String configFormFile="0configform.txt";
	private static final String item_sep=",";
	private static final String classFilePath = GenUtil.class.getResource("").getPath()+"html\\"; 
	private static final String configFilePath = GenUtil.class.getResource("").getPath();
	
	
	/**
	 * id 用于 前端 选择元素，全局唯一
	 * name 用于 后台获取值 
	 * 这里，id用于打开创建modal时，清空数据。打开编辑modal时，填充数据。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		genFinalForm();
		
		genFinalTable();
		
		genFinalJs();
		
		genFinalController();
		
		genFinalService();
	}
	
	public static void genFinalService() {
		List<String> configs = FileUtil.readFileByLines(configFilePath+configFormFile);
		 String config = configs.get(0);
		 String[] itemArray = config.split(":");
		 String[] value = itemArray[1].split(",");
		
		 String  content = FileUtil.readFileByString(classFilePath+wserviceFile);
		 content = content.replaceAll(wserviceKey, value[0]);
		 content = content.replaceAll(wserviceUpperKey, value[1]);
		 
		 System.out.println("genFinalService \n"+content);
		 
		 String fileName = value[1]+serviceFile_final;
		 FileUtil.writeFileByStringBuffer("D:\\ideaspace\\qarchives\\src\\main\\java\\org\\quetzaco\\archives\\util\\gen\\0"+fileName, content);
	
		 
		 content = FileUtil.readFileByString(classFilePath+wserviceImplFile);
		 content = content.replaceAll(wserviceKey, value[0]);
		 content = content.replaceAll(wserviceUpperKey, value[1]);
		 
		 System.out.println("genFinalServiceImpl \n"+content);
		 
		 fileName = value[1]+serviceImplFile_final;
		 FileUtil.writeFileByStringBuffer("D:\\ideaspace\\qarchives\\src\\main\\java\\org\\quetzaco\\archives\\util\\gen\\0"+fileName, content);
	
	}
	
	public static void genFinalController() {
		List<String> configs = FileUtil.readFileByLines(configFilePath+configFormFile);
		 String config = configs.get(0);
		 String[] itemArray = config.split(":");
		 String[] value = itemArray[1].split(",");
		
		 String  content = FileUtil.readFileByString(classFilePath+wcontrollerFile);
		 content = content.replaceAll(wcontrollerKey, value[0]);
		 content = content.replaceAll(wcontrollerUpperKey, value[1]);
		 
		 System.out.println("genFinalController \n"+content);
		 
		 String fileName = value[1]+controllerFile_final;
		 FileUtil.writeFileByStringBuffer("D:\\ideaspace\\qarchives\\src\\main\\java\\org\\quetzaco\\archives\\util\\gen\\0"+fileName, content);
	
	}
	
	public static void genFinalJs() {
		List<String> configs = FileUtil.readFileByLines(configFilePath+configFormFile);
		String config = configs.get(0);
		 String[] itemArray = config.split(":");
		 String[] value = itemArray[1].split(",");
		
		 String  content = FileUtil.readFileByString(classFilePath+jsFile);
		 content = content.replaceAll(wjsKey, value[0]);
		 content = content.replaceAll(wjsUpperKey, value[1]);
		 
		 StringBuffer mapBuf = new StringBuffer();
		 StringBuffer colBuf = new StringBuffer();
		 
		 for(String item:configs) {
			 if(item.startsWith("form")) {
				 continue;
			 }
			 String[] itemArrays = item.split(":");
			 String type = itemArrays[0];
			 String[] values = itemArrays[1].split(",");
			 
			 String jsValue = values[0];
			 String jsId= values[1];
			 String jsName=values[2];

			 //input:采购编号,project_code_input,purchaseCode
			 mapBuf.append(genJsMapper(jsId+item_sep+jsName)+FileUtil.NEW_LINE);
			 colBuf.append(genJsCol(jsName+item_sep+jsValue)+FileUtil.NEW_LINE);
		 }
		 content = content.replaceAll(wjsMapperBody, mapBuf.toString());
		 content = content.replaceAll(wjsColBody, colBuf.toString());
		 
		 System.out.println("final js \n"+content);
		 
		 FileUtil.writeFileByStringBuffer("D:\\ideaspace\\qarchives\\src\\main\\java\\org\\quetzaco\\archives\\util\\gen\\"+jsFile_final, content);
	
	}
	
	public static String genJsMapper(String itemValue) {
		
		String content = FileUtil.readFileByString(classFilePath+jsMapperFile);
		System.out.println("genJsMapper \n"+content);
		
		String[] itemValueStr = itemValue.split(item_sep);
		
		content = content.replaceAll(wjsKeyId, itemValueStr[0]);
		content = content.replaceAll(wjsKeyName, itemValueStr[1]);
		
		
		System.out.println("genJsMapper2 \n"+content);
		return content;
	}
	
	public static String genJsCol(String itemValue) {
		
		String content = FileUtil.readFileByString(classFilePath+jsColFile);
		System.out.println("genJsCol \n"+content);
		
		String[] itemValueStr = itemValue.split(item_sep);
		
		content = content.replaceAll(wjsKeyName, itemValueStr[0]);
		content = content.replaceAll(wjsKeyValue, itemValueStr[1]);
		
		
		System.out.println("genJsCol2 \n"+content);
		return content;
	}
	
	public static void genFinalTable() {
		List<String> configs = FileUtil.readFileByLines(configFilePath+configFormFile);
		String config = configs.get(0);
		 String[] itemArray = config.split(":");
		 String[] value = itemArray[1].split(",");
		
		 String  table = FileUtil.readFileByString(classFilePath+talbeFile);
		 
		 table = table.replaceAll(wtableKey, value[0]);
		 table = table.replaceAll(wtableUpperKey, value[1]);
		 table = table.replaceAll(wtableValue, value[2]);
		 table = table.replaceAll(wtableTitle, value[3]);
		 
		 System.out.println("final table \n"+table);
		 
		 FileUtil.writeFileByStringBuffer("D:\\ideaspace\\qarchives\\src\\main\\java\\org\\quetzaco\\archives\\util\\gen\\"+tableFile_final, table);
	
	}
	
	/**
	 * 解析好后，for -if else 拼成表单
	 * @param args
	 */
	public static void genFinalForm() {
		String  form = null;
		 StringBuffer formBody = new StringBuffer();
		 String  btn = null;
		 
		 String formkey="";
		 for(String item:getConfig()) {
			 
			 String[] itemArray = item.split(":");
			 String type = itemArray[0];
			 String value = itemArray[1];
			 if("form".equals(type)) {
				 form = genForm(value);
			 }if("formkey".equals(type)) {
				 formkey = value.split(",")[0];
			 }else if("formbutton".equals(type)) {
				 btn = genButton(value);
			 }else if("input".equals(type)) {
				 formBody.append(genFormInput(value)+FileUtil.NEW_LINE);
			 }else if("area".equals(type)) {
				 formBody.append(genFormArea(value)+FileUtil.NEW_LINE);
			 }else if("date".equals(type)) {
				 formBody.append(genFormDate(value, formkey)+FileUtil.NEW_LINE);
			 }else if("select".equals(type)) {
				 formBody.append(genFormSelect(value)+FileUtil.NEW_LINE);
			 }
			 
		 }
		
		 
		 form = form.replaceAll(wformBody, formBody.toString());
		 form = form.replaceAll(wformButton, btn);
		 
		 System.out.println("final form \n"+form);
		 
		 FileUtil.writeFileByStringBuffer("D:\\ideaspace\\qarchives\\src\\main\\java\\org\\quetzaco\\archives\\util\\gen\\"+formFile_final, form);
	
	}
	
	public static List<String> getConfig() {
		
		
		List<String> config = FileUtil.readFileByLines(configFilePath+configFormFile);
		
		System.out.println("替換后的btn \n"+config);
		
		return config;
	}
	
	public static String genButton(String itemValue) {
		
		String btn = FileUtil.readFileByString(classFilePath+buttonFile);
		System.out.println("模板btn \n"+btn);
		
		String[] itemValueStr = itemValue.split(item_sep);
		
		btn = btn.replaceAll(wbuttonName, itemValueStr[0]);
		btn = btn.replaceAll(wbuttonFunctionName, itemValueStr[1]);
		
		System.out.println("替換后的btn \n"+btn);
		
		return btn;
	}
	
	public static String genForm(String itemValue) {
		
		String form = FileUtil.readFileByString(classFilePath+formFile);
		System.out.println("模板form \n"+form);
		
		String[] itemValueStr = itemValue.split(item_sep);
		
		form = form.replaceAll(wformTitle, itemValueStr[0]);
		form = form.replaceAll(wformComment, itemValueStr[1]);
		form = form.replaceAll(wformModal, itemValueStr[1]+"Modal");
		form = form.replaceAll(wformId, "create"+itemValueStr[1]+"Form");
		
		
		System.out.println("替換后的form \n"+form.replaceAll("\r\n", "\n"));
		
		return form;
	}
	/**
	 * String item="input:采购编号,project_code_input,purchaseCode";
	 * @param itemValue
	 * @return
	 */
	public static String genFormInput(String itemValue) {
		
		String form = FileUtil.readFileByString(classFilePath+formFile_input);
		System.out.println("form_input1 \n"+form);
		
		String[] itemValueStr = itemValue.split(item_sep);
		
		form = form.replaceAll(winputLabel, itemValueStr[0]);
		form = form.replaceAll(wInputId, itemValueStr[1]);
		form = form.replaceAll(winputName, itemValueStr[2]);
		
		
		System.out.println("form_input2 \n"+form);
		return form;
	}
	
	/**
	 *  String item="area:短信信息,messages_text_area,smsInfo";
	 * @param itemValue
	 * @return
	 */
	public static String genFormArea(String itemValue) {
		
		String form = FileUtil.readFileByString(classFilePath+formFile_area);
		System.out.println("模板form_area \n"+form);
		
		String[] itemValueStr = itemValue.split(item_sep);
		form = form.replaceAll(wareaLabel, itemValueStr[0]);
		form = form.replaceAll(wareaId, itemValueStr[1]);
		form = form.replaceAll(wareaName, itemValueStr[2]);
		
		
		System.out.println("替換后的form \n"+form);
		return form;
	}
	/**
	 * String item="date:评标时间,bidding_time_input,biddingTime";
	 * @param itemValue
	 * @return
	 */
	public static String genFormDate(String itemValue,String key) {
		
		String form = FileUtil.readFileByString(classFilePath+formFile_date);
		System.out.println("模板form_date \n"+form);
		
		String[] itemValueStr = itemValue.split(item_sep);
		
		form = form.replaceAll(wdateLabel, itemValueStr[0]);
		form = form.replaceAll(wdateDivId, key+"_datetimepicker1");
		form = form.replaceAll(wdateId, itemValueStr[1]);
		form = form.replaceAll(wdateName, itemValueStr[2]);
		
		
		System.out.println("替換后的form \n"+form);
		return form;
	}
	/**
	 * String item="select:采购方式,project_code_input,purchaseType";
	 * @param itemValue
	 * @return
	 */
	public static String genFormSelect(String itemValue) {
		
		String form = FileUtil.readFileByString(classFilePath+formFile_select);
		System.out.println("模板form_select \n"+form);
		
		String[] itemValueStr = itemValue.split(item_sep);
		
		form = form.replaceAll(wselectLabel, itemValueStr[0]);
		form = form.replaceAll(wselectId, itemValueStr[1]);
		form = form.replaceAll(wselectName, itemValueStr[2]);
		
		
		System.out.println("替換后的form \n"+form);
		return form;
	}
}
