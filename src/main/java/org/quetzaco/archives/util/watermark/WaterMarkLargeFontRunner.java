package org.quetzaco.archives.util.watermark;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class WaterMarkLargeFontRunner implements WaterMarkRunner {
    private final String text;
    private final BaseFont baseFont;
    private final static String USER_NAME_MARK="{QUETZAC0_USER_NAME}";

    public WaterMarkLargeFontRunner(BaseFont baseFont,String text) {
        this.text = text;
        this.baseFont = baseFont;
    }

    @Override
    public void runner(Rectangle rectangle, PdfContentByte over) {
        over.setFontAndSize(baseFont, 75.0F);
        over.setColorFill(new BaseColor(210, 210, 210));
        over.showTextAligned(1, text, rectangle.getRight() / 2.0F+20, rectangle.getTop() / 2.0F-20, 54.74F);
        PdfGState pdfGState = new PdfGState();
        pdfGState.setFillOpacity(0.2F);
        over.setGState(pdfGState);
    }
}
