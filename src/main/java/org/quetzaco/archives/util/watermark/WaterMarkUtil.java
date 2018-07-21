package org.quetzaco.archives.util.watermark;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description Created by dong on 2017/12/6.
 */
public class WaterMarkUtil {

  public static BaseFont getFont(String fontPath) throws IOException, DocumentException {
    return BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
  }

  public static void addWaterMark(String srcPdf, String targetPdf ,WaterMarkRunner runner) {
    com.itextpdf.text.pdf.PdfReader reader = null;
    com.itextpdf.text.pdf.PdfStamper stamp = null;
    try {
      reader = new com.itextpdf.text.pdf.PdfReader(srcPdf);
      int pages = reader.getNumberOfPages();
      stamp = new com.itextpdf.text.pdf.PdfStamper(reader, new FileOutputStream(targetPdf));
      for (int i = 1; i <= pages; i++) {
        com.itextpdf.text.pdf.PdfContentByte over = stamp.getUnderContent(i);
        over.beginText();
        runner.runner(reader.getPageSize(i), over);
        over.endText();
        if(runner instanceof WaterMarkFrontPageRunner)
          break;
      }
      stamp.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (com.itextpdf.text.DocumentException e) {
      e.printStackTrace();
    } finally {
      try {
        reader.close();
      } catch (Exception e) {
      }
      reader = null;
    }
  }
}
