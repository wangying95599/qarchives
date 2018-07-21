package org.quetzaco.archives.application.biz;

import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.quetzaco.archives.model.HistoryDocument;
import org.quetzaco.archives.model.HistoryFile;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.quetzaco.archives.util.excel.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

/**
 * @Description Created by dong on 2017/8/24.
 */
public class HImportRunner extends QarchivesApplicationTests {

  @Autowired
  HistoryFileService historyFileService;
  @Autowired
  HistoryDocumentService historyDocumentService;

  private String path = "F:\\work\\项目文档\\昆明航空\\昆航数据\\昆航数据";

  private String[] files = {
        "1.昆明航空文书原文.xlsx"
      , "2.昆明航空文书条目.xlsx"
      , "3.昆明数据审核条目-未归档.xlsx"
      , "4.昆明数据审核原文-未归档.xlsx"
      , "5.昆明航空实物档案条目.xlsx"
      , "6.昆明航空实物档案原文.xlsx"
      , "7.昆明航空刊物条目.xlsx"
      , "8.昆明航空刊物档案原文.xlsx"
      , "9.昆明航空其它声像条目.xlsx"
      , "10.昆明航空照片条目.xlsx"
  };

  @Test
  @Commit
  public void runner() {
    try {
/*

      ExcelUtils.getBankListByExcel(path + "/" + files[1], 2, row -> {
        return historyDocumentService.insertHistoryDocument(getDocument1(row));
      });
      ExcelUtils.getBankListByExcel(path + "/" + files[0], 2, row -> {
        return historyFileService.insertHistoryFile(getHistoryFile(row));
      });
      ExcelUtils.getBankListByExcel(path + "/" + files[4], 2, row -> {
        return historyDocumentService.insertHistoryDocument(getDocument4(row));
      });
      ExcelUtils.getBankListByExcel(path + "/" + files[5], 1, row -> {
        return historyFileService.insertHistoryFile(getHistoryFile(row));
      });
      ExcelUtils.getBankListByExcel(path + "/" + files[6], 2, row -> {
        return historyDocumentService.insertHistoryDocument(getDocument6(row));
      });
      ExcelUtils.getBankListByExcel(path + "/" + files[7], 2, row -> {
        return historyFileService.insertHistoryFile(getHistoryFile(row));
      });
      ExcelUtils.getBankListByExcel(path + "/" + files[9], 2, row -> {
        return historyDocumentService.insertHistoryDocument(getDocument9(row));
      });
      ExcelUtils.getBankListByExcel(path + "/" + files[8], 2, row -> {
        return historyDocumentService.insertHistoryDocument(getDocument8(row));
      });
*/


      ExcelUtils.getBankListByExcel(path + "/" + files[2], 2, row -> {
        return historyDocumentService.insertHistoryDocument(getDocument2(row));
      });
      ExcelUtils.getBankListByExcel(path + "/" + files[3], 2, row -> {
        return historyFileService.insertHistoryFile(getHistoryFile(row));
      });

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private HistoryDocument getDocument2(Row row) {
    //ID	QZH	DH	ZRZ	BGQX	MJ	ZTC	BZ	ND	YS	TM	STFS	WJBH	LBBH	NGBM	WJXCSJ	STFLH	GDRQ	SSBM	WJSX	LRR	WJLX	YJR	DJH	DJRQ	SXH
    HistoryDocument historyDocument = new HistoryDocument();
    int i = 0;
    historyDocument.setId(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setQzh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setZrz(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBgqx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setMj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setZtc(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBz(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setNd(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setYs(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setTm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setStfs(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWjbh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setLbbh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setNgbm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWjxcsj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setStflh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setGdrq(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSsbm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWjsx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setLrr(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWjlx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setYjr(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDjh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDjrq(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSxh(ExcelUtils.getCellValue(row.getCell(i++)));
    return historyDocument;
  }

  private HistoryDocument getDocument8(Row row) {
    //ID	ND	QZH	STFLH	BGQX	AJH	DH	TM	ZTC	LBBH	GDRQ	SL	MJ	SSBM	BZ
    HistoryDocument historyDocument = new HistoryDocument();
    int i = 0;
    historyDocument.setId(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setNd(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setQzh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setStflh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBgqx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setAjh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setTm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setZtc(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setLbbh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setGdrq(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSl(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setMj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSsbm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBz(ExcelUtils.getCellValue(row.getCell(i++)));
    return historyDocument;
  }

  private HistoryDocument getDocument9(Row row) {
    //ID	QZH	STFLH	ND	BGQX	AJH	DH	TM	ZTC	LBBH	GDRQ	SL	MJ	SSBM	BZ
    HistoryDocument historyDocument = new HistoryDocument();
    int i = 0;
    historyDocument.setId(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setQzh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setStflh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setNd(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBgqx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setAjh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setTm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setZtc(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setLbbh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setGdrq(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSl(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setMj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSsbm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBz(ExcelUtils.getCellValue(row.getCell(i++)));
    return historyDocument;
  }

  private HistoryDocument getDocument6(Row row) {
    //ID	LBBH	QZH	DH	ND	JH	STFLH	TM	KH	ZRZ	SSBM	BGQX	WJXCSJ	FS	YS	GDRQ	BZ
    HistoryDocument historyDocument = new HistoryDocument();
    int i = 0;
    historyDocument.setId(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setLbbh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setQzh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setNd(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setJh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setStflh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setTm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setKh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setZrz(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSsbm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBgqx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWjxcsj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setFs(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setYs(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setGdrq(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBz(ExcelUtils.getCellValue(row.getCell(i++)));
    return historyDocument;
  }

  private HistoryDocument getDocument4(Row row) {
    //ID	QZH	DH	STFLH	ND	JH	LBBH	SWLX	SWSM	XCSJ	GDRQ	MJ	BGQX	SSBM	FS
    HistoryDocument historyDocument = new HistoryDocument();
    int i = 0;
    historyDocument.setId(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setQzh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setStflh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setNd(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setJh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setLbbh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSwlx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSwsm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setXcsj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setGdrq(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setMj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBgqx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSsbm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setFs(ExcelUtils.getCellValue(row.getCell(i++)));
    return historyDocument;
  }

  private HistoryDocument getDocument1(Row row) {
    //ID	QZH	DH	ZRZ	BGQX	MJ	ZTC	BZ	ND	JH	YS	WH	TM	STFS	LBBH	NGBM	WJXCSJ	STFLH	GDRQ	SSBM	WJSX	LRR	WJLX	YJR	DJH	SYS_FILE_COUNT
    HistoryDocument historyDocument = new HistoryDocument();
    int i = 0;
    historyDocument.setId(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setQzh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setZrz(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBgqx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setMj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setZtc(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setBz(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setNd(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setJh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setYs(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setTm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setStfs(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setLbbh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setNgbm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWjxcsj(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setStflh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setGdrq(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSsbm(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWjsx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setLrr(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setWjlx(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setYjr(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setDjh(ExcelUtils.getCellValue(row.getCell(i++)));
    historyDocument.setSysFileCount(ExcelUtils.getCellValue(row.getCell(i++)));
    return historyDocument;
  }

  private HistoryFile getHistoryFile(Row row) {
    HistoryFile historyFile = new HistoryFile();
    int i = 0;
    historyFile.setFileId(ExcelUtils.getCellValue(row.getCell(i++)));
    historyFile.setFileType(ExcelUtils.getCellValue(row.getCell(i++)));
    historyFile.setEntryId(ExcelUtils.getCellValue(row.getCell(i++)));
    historyFile.setFileTitle(ExcelUtils.getCellValue(row.getCell(i++)));
    historyFile.setFileSize(ExcelUtils.getCellValue(row.getCell(i++)));
    historyFile.setFilePath(ExcelUtils.getCellValue(row.getCell(i++)));
    historyFile.setFileName(ExcelUtils.getCellValue(row.getCell(i++)));
    historyFile.setFileSuffix(ExcelUtils.getCellValue(row.getCell(i++)));
    return historyFile;
  }
}
