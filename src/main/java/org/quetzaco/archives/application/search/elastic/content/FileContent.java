/**
 *
 */
package org.quetzaco.archives.application.search.elastic.content;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import com.lowagie.text.pdf.PdfReader;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.icepdf.core.pobjects.Name;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.pobjects.Reference;
import org.icepdf.ri.common.SwingController;
import org.quetzaco.archives.application.biz.Impl.RecordServiceImpl;
import org.quetzaco.server.utils.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author 
 *
 */
public class FileContent {
	public static void main(String[] args) {
		FileContent fc = new FileContent();
		//鎵惧埌 /opt/qarchive/elasticsearch/ cfg/server.conf 
		System.setProperty("root","/opt/qarchive/elasticsearch/");
		String str = fc.getText("D:\\opt\\quetzaco\\docs\\201712\\01\\01\\30FEB596-985B-E585-7C22-6518997BA400.docx");
		System.out.println("          11111111111              "+str);
	}
	final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RecordServiceImpl.class);

	public String getText(String filename){
		String txt = "";

		String ext = filename.substring(filename.lastIndexOf(".")+1).toLowerCase().trim();
		try{
			if(ext.equals("doc")||ext.equals("dot")){
				txt = getTextFromDOC(filename);
			}else if(ext.equals("docx")||ext.equals("dotx")){
				txt = getTextFromDOCX(filename);
			}else if(ext.equals("xls")){
				txt = getTextFromXLS(filename);
			}else if(ext.equals("xlsx")){
				txt = getTextFromXLSX(filename);
			}else if(ext.equals("ppt")||ext.equals("pps")){
				txt = getTextFromPPT(filename);
			}else if(ext.equals("pptx")||ext.equals("ppsx")){
				txt = getTextFromPPTX(filename);
			}else if(ext.equals("msg")){
				txt = getTextFromMSG(filename);
			}else if(ext.equals("pdf")){
				 try {
					 txt = getTextFromPDF(filename);
					 if((txt==null || ("").equals(txt)||("").equals(txt.trim())) && ("ON").equalsIgnoreCase(Configuration.getProperty("OCR_ENABLE")))
							txt=getScanPdfContent(filename);
					} catch (Exception e) {
						e.printStackTrace();
						txt="";
					}
			}else{
				txt = "";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return txt;
	}


	private String getTextFromDOC(String filename) {
		InputStream is = null;
		String text = "";
		try {
			is = new FileInputStream(filename);
			WordExtractor ex = new WordExtractor(is);
			text = ex.getText();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
			}
		}
		return text;
	}

	private String getTextFromDOCX(String filename){
		FileInputStream is = null;
		String text = "";
		try {
			File file = new File(filename);
			is = new FileInputStream(file);
			XWPFDocument document = new XWPFDocument(is);
			POIXMLTextExtractor ex = new XWPFWordExtractor(document);
			text = ex.getText();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
			}
		}
		return text;
	}

	private String getTextFromXLS(String filename) {
		InputStream is = null;
		String text = null;
		try {
			is = new FileInputStream(filename);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
			ExcelExtractor extractor = new ExcelExtractor(wb);
			extractor.setIncludeSheetNames(false);
			extractor.setFormulasNotResults(false);
			extractor.setIncludeCellComments(true);
			text = extractor.getText();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
			}
		}

		return text;
	}

	private String getTextFromXLSXByXSSF(String filename){

		FileInputStream is = null;
		String text = "";
		try {
			File file = new File(filename);
			is = new FileInputStream(file);
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			XSSFExcelExtractor extractor = new XSSFExcelExtractor(xwb);
			extractor.setIncludeSheetNames(false);
			extractor.setFormulasNotResults(false);
			extractor.setIncludeCellComments(true);
			text = extractor.getText();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
			}
		}

		return text;
	}

	private String getTextFromXLSX(String filename) {

		StringBuffer text = new StringBuffer();
		// 瑙ｅ帇Book1.xlsx
		ZipFile xlsxFile = null;
		DocumentBuilderFactory dbf = null;
		ZipEntry sharedStringXML = null;
		InputStream sharedStringXMLIS = null;
		try {
			xlsxFile = new ZipFile(new File(filename));
			dbf = DocumentBuilderFactory.newInstance();
			// SAXBuilder saxBuilder = new SAXBuilder();
			// 鍏堣鍙杝haredStrings.xml杩欎釜鏂囦欢澶囩敤
			sharedStringXML = xlsxFile
					.getEntry("xl/sharedStrings.xml");
			sharedStringXMLIS = xlsxFile
					.getInputStream(sharedStringXML);
			// BufferedReader bufreader = new BufferedReader(new
			// InputStreamReader(sharedStringXMLIS));
			// while((line=bufreader.readLine())!=null) {
			// LOGGER.debug(line);
			// }
			// Document xmlDocument = saxBuilder.build(sharedStringXMLIS);
			Document sharedString = (Document) dbf.newDocumentBuilder().parse(
					sharedStringXMLIS);
			NodeList str = sharedString.getElementsByTagName("t");
			// xmlDocument.
			String sharedStrings[] = new String[str.getLength()];
			for (int n = 0; n < str.getLength(); n++) {
				Element element = (Element) str.item(n);
				// LOGGER.debug("getNodeName:"+element.getNodeName());
				// LOGGER.debug("getNodeValue:"+element.getTextContent());
				sharedStrings[n] = element.getTextContent();
			}
			// 鎵惧埌瑙ｅ帇鏂囦欢澶归噷鐨剋orkbook.xml,姝ゆ枃浠朵腑鍖呭惈浜嗚繖寮犲伐浣滆〃涓湁鍑犱釜sheet
//			ZipEntry workbookXML = xlsxFile.getEntry("xl/workbook.xml");
//			InputStream workbookXMLIS = xlsxFile.getInputStream(workbookXML);
			// BufferedReader bufreader = new BufferedReader(new
			// InputStreamReader(workbookXMLIS));
			// while((line=bufreader.readLine())!=null) {
			// LOGGER.debug(line);
			// }
//			Document doc = dbf.newDocumentBuilder().parse(workbookXMLIS);
			// 鑾峰彇涓�鍏辨湁鍑犱釜sheet
//			NodeList nl = doc.getElementsByTagName("sheet");

			for (int i = 1; i < Integer.MAX_VALUE; i++) {
//				Element element = (Element) nl.item(i);// 灏唍ode杞寲涓篹lement锛岀敤鏉ュ緱鍒版瘡涓妭鐐圭殑灞炴��
//				LOGGER.debug(element.getAttribute("name"));// 杈撳嚭sheet鑺傜偣鐨刵ame灞炴�х殑鍊�
//				if (element.getAttribute("name").indexOf("Sheet") == -1) {
//					continue;
//				}
				// 鎺ョ潃灏辫鍒拌В鍘嬫枃浠跺す閲屾壘鍒板搴旂殑name鍊肩殑xml鏂囦欢锛屾瘮濡傚湪workbook.xml涓湁<sheet
				// name="Sheet1" sheetId="1" r:id="rId1" /> 鑺傜偣
				// 閭ｄ箞灏卞彲浠ュ湪瑙ｅ帇鏂囦欢澶归噷鐨剎l/worksheets涓嬫壘鍒皊heet1.xml,杩欎釜xml鏂囦欢澶归噷灏辨槸鍖呭惈鐨勮〃鏍肩殑鍐呭
				ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/sheet"
						+ i + ".xml");
				if(sheetXML == null) {
					break;
				}
				InputStream sheetXMLIS = xlsxFile.getInputStream(sheetXML);
				// BufferedReader bufreader = new BufferedReader(new
				// InputStreamReader(sheetXMLIS));
				// while((line=bufreader.readLine())!=null) {
				// LOGGER.debug(line);
				// }
				Document sheetdoc = dbf.newDocumentBuilder().parse(sheetXMLIS);
				NodeList rowdata = sheetdoc.getElementsByTagName("row");
				for (int j = 0; j < rowdata.getLength(); j++) {
					// 寰楀埌姣忎釜琛�
					// 琛岀殑鏍煎紡锛�
					Element row = (Element) rowdata.item(j);
					// 鏍规嵁琛屽緱鍒版瘡涓涓殑鍒�
					NodeList columndata = row.getElementsByTagName("c");
					for (int k = 0; k < columndata.getLength(); k++) {
						Element column = (Element) columndata.item(k);
						NodeList values = column.getElementsByTagName("v");
						if (values.getLength() <= 0) {
							continue;
						}
						// LOGGER.debug("values:"+values.getLength());
						Element value = (Element) values.item(0);
						if (column.getAttribute("t") != null
								& column.getAttribute("t").equals("s")) {
							// 濡傛灉鏄叡浜瓧绗︿覆鍒欏湪sharedstring.xml閲屾煡鎵捐鍒楃殑鍊�
//							System.out.print(sharedStrings[Integer
//									.parseInt(value.getTextContent())] + "   ");
							text.append(sharedStrings[Integer.parseInt(value.getTextContent())]);
						} else {
//							System.out.print(value.getTextContent() + "  ");
							text.append(value.getTextContent());
						}
					}
				}

				sheetXMLIS.close();
			}
		} catch (Exception e) {
			try {
				if(sharedStringXMLIS!=null) {
					sharedStringXMLIS.close();
					sharedStringXMLIS=null;
				}
				if(xlsxFile != null) {
					xlsxFile.close();
					xlsxFile = null;
				}
			} catch (Exception e2) {
			}
			text.append(getTextFromXLSXByXSSF(filename));

//			LOGGER.debug("getTextFromXLSX Exception:"+e + " getTextFromXLSXByXSSF Text:"+text);

		} finally{
			try {
				if(sharedStringXMLIS!=null) {
					sharedStringXMLIS.close();
				}
				if(xlsxFile != null) {
					xlsxFile.close();
				}
			} catch (Exception e2) {
			}
		}
		return text.toString();
	}

	private String getTextFromPPT(String filename) {
		InputStream is = null;
		String text = "";
		try {
			is = new FileInputStream(new File(filename));
			PowerPointExtractor ppt = new PowerPointExtractor(is);
			text = ppt.getText();
		} catch (Exception e) {
		} finally{
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
			}
		}

		return text;
	}

	private String getTextFromPPTX(String filename){
		FileInputStream is = null;
		String text = "";
		try {
			is = new FileInputStream(new File(filename));
			XMLSlideShow xmlS = new XMLSlideShow(is);
			XSLFPowerPointExtractor extractor =  new XSLFPowerPointExtractor(xmlS);
			text = extractor.getText(true,true);

		} catch (Exception e) {
		} finally{
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
			}
		}
		return  text;
	}

	private String getTextFromMSG(String filename){
		String text = "";
		InputStream is = null;
		try {
			is = new FileInputStream(new File(filename));
			MAPIMessage msg = new MAPIMessage(is);
			String txtOut = "";
			try {
				String displayFrom = msg.getDisplayFrom();
				txtOut = txtOut + displayFrom;
			}catch (ChunkNotFoundException e) {
				// ignore
			}
			try {
				String displayTo = msg.getDisplayTo();
				txtOut = txtOut +displayTo;
			}catch (ChunkNotFoundException e) {
				// ignore
			}
			try {
				String displayCC = msg.getDisplayCC();
				txtOut = txtOut+displayCC;
			}catch (ChunkNotFoundException e) {
				// ignore
			}
			try {
				String displayBCC = msg.getDisplayBCC();
				txtOut=txtOut+displayBCC;
			}catch (ChunkNotFoundException e) {
				// ignore
			}
			try {
				String subject = msg.getSubject();
				txtOut=txtOut+subject;
			}catch (ChunkNotFoundException e) {
				// ignore
			}
			try {
				String body = msg.getTextBody();
				txtOut=txtOut+(body);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String encoding = CharsetDetector.getInstance().detectEncoding(txtOut);
			if (encoding.toLowerCase().startsWith("gb")){
				encoding="GBK";
			}else if (encoding.equalsIgnoreCase("OTHER")){
				encoding = "UTF-8";
			}
			text = new String(txtOut.getBytes("iso8859-1"),encoding);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
			}
		}
		return text;
	}

	

	private String getTextFromPDF(String filename) throws Exception {
		String text = "";
		FileInputStream inputStream = null;
		PDDocument pdfDocument = null;
		try {
			inputStream = new FileInputStream(filename);
			PDFParser parser = new PDFParser(inputStream);
			parser.parse();
			PDFTextStripper stripper = new PDFTextStripper();
			pdfDocument = parser.getPDDocument();
			text = stripper.getText(pdfDocument);

		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if(inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
				if (pdfDocument != null) {
						pdfDocument.close();
						pdfDocument=null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text;
	}
	
	public String getExifFromJPG(String filename){
		String text = "";
		try{
			Metadata metadata = JpegMetadataReader.readMetadata(new File(filename));
			Directory exif = metadata.getDirectory(ExifDirectory.class);
			Iterator tags = exif.getTagIterator();
			while (tags.hasNext()) {
				Tag tag = (Tag)tags.next();
				text = text + "\n" + tag.getTagName() + "=" + tag.getDescription();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return text;

	}

	public static String getScanPdfContent(String input){
		String text="";
		LOGGER.info("getScanPdfContent::::::::::: "+input);
		String img=input.substring(0,input.lastIndexOf("."))+".jpg";
		int pages=getNumberOfPagesOfPDF(input);
		LOGGER.info("getScanPdfContent::::::::::: "+pages);
		for(int i=0;i<pages ;i++){

			String tem=img.replace(".jpg", "-"+i+".jpg");
			LOGGER.info("getScanPdfContent::::::::::: tem:::"+tem);
			if(pdfPageToJPG(input, tem ,i)){
				File imgFile=new File(tem);
				LOGGER.info("getScanPdfContent::in loop::::::::: imgFile:::"+imgFile);
				if(!imgFile.exists()){
					break;
				}
				text+=OCRUtil.getText(tem);
				FileUtil.delFile(tem);
			}
		}
		return text;
	}
	public static  boolean pdfPageToJPG(String input,String output ,int page){
		LOGGER.debug(input+"\n"+output);
				try{
					String conv_prog= ConvertProcessUtil.getToolCMD("IMGTOOLS_PATH","ImageMagick"+File.separatorChar+"bin","convert");
					String cmd = conv_prog+" -background white -alpha remove -density 200  "+input+"["+page+"] "+output;
					LOGGER.info("cmd "+cmd+".");
					Process p = Runtime.getRuntime().exec(cmd);
					ConvertProcessUtil pu = new ConvertProcessUtil();
					if (pu.waitFor(p) == 0){
						LOGGER.info("Success to convert "+input+" to "+output+".");
						return true;

					}else{
						LOGGER.info("Fail to convert "+input+" to image.");
						return false;
					}
				//	return true;
				}catch(Exception e){
					e.printStackTrace();
					return false;
				}

	}
public static int getNumberOfPagesOfPDF(String pdfFile){

		int cnt = -1;
		PdfReader ppp = null;
		try{
			ppp = new com.lowagie.text.pdf.PdfReader(pdfFile);
			ppp.removeUsageRights();
			cnt = ppp.getNumberOfPages();
		}catch(IOException e){
			LOGGER.error(e.getMessage());
		}finally{
			ppp.close();
		}
		return cnt;
	}
	public static boolean isScanPDF(String pdf) {
		try {

			SwingController controller = new SwingController();
			LOGGER.debug(" SwingController will print warn info  not error");
			controller.openDocument(pdf);  //will print warn info  not error
			org.icepdf.core.pobjects.Document document = controller.getDocument();
			Object lock = new Object();
			Page page = document.getPageTree().getPage(0, lock);
			Hashtable resources;

			if(page==null||page.getEntries()==null)
				return false;

			Object tmp = page.getEntries().get(new Name("Resources"));
			if (tmp instanceof Reference) {
				resources = (Hashtable) page.getLibrary().getObject(tmp);
			} else {
				resources = (Hashtable) tmp;
			} // check for xObjects and get the image References
			tmp = resources.get(new Name("XObject"));
			LOGGER.debug("XObject  in  resources" +tmp );
			if (tmp != null) {
				Hashtable xObject = (Hashtable) tmp;
				Enumeration keys = xObject.keys();
				int i = 0;
				while (keys.hasMoreElements()) {
					Name name = (Name) keys.nextElement();
					if (name.getName().toLowerCase().startsWith("xi")) {
						LOGGER.debug(name.getName() +" is  start with xi ," + pdf +" is Scan PDF");
						return true;
					}

					if (name.getName().toLowerCase().startsWith("im")) {
						LOGGER.debug("name is  im  --------- is scan :: if " +(i+1) +" =  1");
						if(i>=2){
							LOGGER.debug("i>=2 "+ pdf +" is not a scan pdf");
							return false;
						}
						i++;

					}
				}

				return i==1;
			}

			document.getPageTree().releasePage(0, lock);
		} catch (Exception e) {
			//e.printStackTrace();
			LOGGER.warn("not a scan pdf "+e.getMessage());
		}
		return false;

	}

}
