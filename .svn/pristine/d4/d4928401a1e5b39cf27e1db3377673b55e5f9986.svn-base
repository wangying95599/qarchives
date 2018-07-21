package org.quetzaco.archives.application.biz;

import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import org.quetzaco.archives.model.HistoryDocument;

public interface HistoryDocumentService {
    HistoryDocument insertHistoryDocument(HistoryDocument historyDocument);

    List<HistoryDocument> selectAll();

    PageInfo selectWSDocument(String tm, String dh, int offset, int limit);

    PageInfo selectWGDDocument(String tm, String dh, int offset, int limit);

    PageInfo selectSXDocument(String tm, String dh, int offset, int limit);

    PageInfo selectSWDocument(String tm, String dh, int offset, int limit);

    List<Map> selectTMDocument(String tmid);


}
