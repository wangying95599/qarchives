package org.quetzaco.archives.application.biz;

import org.quetzaco.archives.model.HistoryFile;

import java.util.List;

public interface HistoryFileService {
   HistoryFile insertHistoryFile(HistoryFile historyFile);
   List<HistoryFile> selectAll();
}
