package org.quetzaco.archives.util.watermark;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;

/**
 * @Description Created by dong on 2017/12/1.
 */
public class WaterMarkImageWithSignRunner extends WaterMarkImageRunner {

  private final String text;
  private final BaseFont baseFont;
  private final static String USER_NAME_MARK="{QUETZAC0_USER_NAME}";

  public WaterMarkImageWithSignRunner(String imagePath,BaseFont baseFont,String text) {
    super(imagePath);
    this.text = text;
    this.baseFont = baseFont;
  }


  @Override
  public void runner(Rectangle rectangle, PdfContentByte over) {
    super.runner(rectangle,over);

    over.setFontAndSize(baseFont, 14);
    over.setColorFill( new BaseColor(210, 210, 210));
    for(int i = 1 ;i<4;i++){
      for(int j = 1 ;j<5;j++){
        over.showTextAligned(PdfContentByte.ALIGN_CENTER, text, rectangle.getRight()*i/4,rectangle.getTop()*j/5 , 45);
      }
    }
    /*over.showTextAligned(PdfContentByte.ALIGN_CENTER, text, rectangle.getRight()/2,rectangle.getTop()/2 , 45);
    over.showTextAligned(PdfContentByte.ALIGN_CENTER, text, rectangle.getRight()*3/4,rectangle.getTop()/4 , 45);
    over.showTextAligned(PdfContentByte.ALIGN_CENTER, text, rectangle.getRight()/4,rectangle.getTop()*3/4 , 45);*/
    PdfGState pdfGState = new PdfGState();
    pdfGState.setFillOpacity(0.2f);
    over.setGState(pdfGState);
  }
}
