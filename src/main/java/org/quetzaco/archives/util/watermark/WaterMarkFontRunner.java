package org.quetzaco.archives.util.watermark;

import com.itextpdf.text.pdf.BaseFont;

/**
 * @Description Created by dong on 2017/12/6.
 */
public abstract class WaterMarkFontRunner implements WaterMarkRunner {

  protected BaseFont baseFont;
  protected String text ;

  public WaterMarkFontRunner(BaseFont baseFont,String text){
      this.baseFont = baseFont;
      this.text = text;
    }
}
