package org.quetzaco.archives.util.watermark;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;

public class WaterMarkTwoLineRunner implements WaterMarkRunner {
    private final String text;
    private final BaseFont baseFont;
    private final static String USER_NAME_MARK="{QUETZAC0_USER_NAME}";

    public WaterMarkTwoLineRunner(BaseFont baseFont,String text) {
        this.text = text;
        this.baseFont = baseFont;
    }
    @Override
    public void runner(Rectangle rectangle, PdfContentByte over) {
        String[] texts = text.split("，");
        over.setFontAndSize(baseFont, 90.0F);
        over.setColorFill(new BaseColor(210, 210, 210));
        over.showTextAligned(1, texts[0], rectangle.getRight() / 2.0F, rectangle.getTop() *6/ 11.0F, 54.74F);
        over.showTextAligned(1, texts[1], rectangle.getRight() *2/ 3.0F, rectangle.getTop() / 3.0F, 54.74F);
        PdfGState pdfGState = new PdfGState();
        pdfGState.setFillOpacity(0.2F);
        over.setGState(pdfGState);
    }
}
