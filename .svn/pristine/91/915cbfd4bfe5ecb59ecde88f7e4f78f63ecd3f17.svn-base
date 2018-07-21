package org.quetzaco.archives.util.watermark;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description Created by dong on 2017/12/1.
 */
public class WaterMarkImageRunner implements WaterMarkRunner {

  final static Logger logger = LoggerFactory.getLogger(WaterMarkImageRunner.class);

  private final String imagePath;

  public WaterMarkImageRunner(String imagePath){
    this.imagePath = imagePath;
  }
  @Override
  public void runner(Rectangle rectangle, PdfContentByte over) {
    try {
      Image img = Image.getInstance(imagePath);
      img.setAlignment(1);
      img.setAbsolutePosition(rectangle.getRight() -150, rectangle.getTop() -150);//坐标
     // img.setRotation(-20);//旋转 弧度
      //img.setRotationDegrees(-45);//旋转 角度
      img.scaleAbsolute(100,100);//自定义大小
    //  img.scalePercent(50);//依照比例缩放
      over.addImage(img);
    } catch (Exception e) {
      logger.error(imagePath+" add error");
    }

  }
}
