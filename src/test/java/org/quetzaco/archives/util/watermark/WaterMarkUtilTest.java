package org.quetzaco.archives.util.watermark;

import com.itextpdf.text.pdf.BaseFont;
import org.junit.Test;

/**
 * @Description Created by dong on 2017/12/6.
 */
public class WaterMarkUtilTest {

  @Test
  public void addWaterMark() throws Exception {

    WaterMarkUtil.addWaterMark("D:\\1.pdf", "D:\\2.pdf",
        //根据需要创建不同的实例传入即可  必须都是WaterMarkRunner的子类
        new WaterMarkHeaderRunner(
            //font 路径和字体 可以在配置文件写明
            WaterMarkUtil.getFont("D:\\test\\font\\msyh.ttf"),"some thing write"));
  }

}