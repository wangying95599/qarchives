package org.quetzaco.archives.util.config;

import static org.junit.Assert.assertNotEquals;

import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.quetzaco.archives.model.HistoryFile;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.quetzaco.archives.util.excel.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
public class ExcelUtilsTest extends QarchivesApplicationTests {
    final static Logger logger=LoggerFactory.getLogger(ExcelUtils.class);
    @Autowired
    ExcelUtils excelUtils;
    @Test
    public void getBankListByExcel() throws Exception {
          /* File file=new File();
           InputStream in=new FileInputStream(file);
           byte[]b=new byte[(int)file.length()];
           in.read(b);*/

        //List<List<Object>> bankListByExcel = ExcelUtils.getBankListByExcel("G:/project/kmhk/昆航数据/1.昆明航空文书原文.xlsx");
      //  assertNotEquals(null,bankListByExcel );
        //assertNotEquals(0,bankListByExcel.size() );

        List<HistoryFile> bankListByExcel = ExcelUtils.getBankListByExcel("G:/project/kmhk/昆航数据/1.昆明航空文书原文.xlsx",(2-1), new ExcelUtils.ExcelRunner<HistoryFile>() {
            @Override
            public HistoryFile construct(Row row) {
                HistoryFile historyFile = new HistoryFile();
                int i = 0;
                historyFile.setFileId(ExcelUtils.getCellValue(row.getCell(i++)));
                historyFile.setFileType(ExcelUtils.getCellValue(row.getCell(i++)));
                historyFile.setEntryId(ExcelUtils.getCellValue(row.getCell(i++)));
                historyFile.setFileSize(ExcelUtils.getCellValue(row.getCell(i++)));
                historyFile.setFilePath(ExcelUtils.getCellValue(row.getCell(i++)));
                historyFile.setFileName(ExcelUtils.getCellValue(row.getCell(i++)));
                historyFile.setFileSuffix(ExcelUtils.getCellValue(row.getCell(i++)));
                return historyFile;
            }
        });
        assertNotEquals(null,bankListByExcel );
        assertNotEquals(0,bankListByExcel.size() );
        assertNotEquals("301409",bankListByExcel.get(0).getFileId() );
    }



}