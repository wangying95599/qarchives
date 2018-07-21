package org.quetzaco.archives.util.watermark;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;


/**
 * @Description Created by dong on 2017/9/19.
 */
public class WaterMarkHeaderRunner extends WaterMarkFontRunner {

    public WaterMarkHeaderRunner(BaseFont baseFont, String text) {
        super(baseFont, text);
    }

    @Override
    public void runner(Rectangle rectangle, PdfContentByte over) {

        over.setFontAndSize(this.baseFont, 14);
        over.setColorFill(BaseColor.RED);
        over.showTextAligned(PdfContentByte.ALIGN_CENTER, text, 500,rectangle.getTop()-20 , 0);
    }
    //
    // PdfReader api

    // PdfReader reader = new PdfReader(filename);
    /*    writer.println(filename);
        writer.print("Number of pages: ");
        writer.println(reader.getNumberOfPages());
    Rectangle mediabox = reader.getPageSize(1);
        writer.print("Size of page 1: [");
        writer.print(mediabox.getLeft());
        writer.print(',');
        writer.print(mediabox.getBottom());
        writer.print(',');
        writer.print(mediabox.getRight());
        writer.print(',');
        writer.print(mediabox.getTop());
        writer.println("]");
        writer.print("Rotation of page 1: ");
        writer.println(reader.getPageRotation(1));
        writer.print("Page size with rotation of page 1: ");
        writer.println(reader.getPageSizeWithRotation(1));
        writer.print("Is rebuilt? ");
        writer.println(reader.isRebuilt());
        writer.print("Is encrypted? ");
        writer.println(reader.isEncrypted());
        writer.println();
        writer.flush();*/
}
