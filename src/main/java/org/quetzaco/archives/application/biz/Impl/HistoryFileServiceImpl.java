package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.HistoryFileService;
import org.quetzaco.archives.application.dao.HistoryFileMapper;
import org.quetzaco.archives.model.HistoryFile;
import org.quetzaco.archives.model.HistoryFileExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryFileServiceImpl implements HistoryFileService{
    @Autowired
    HistoryFileMapper historyfilemapper;

    @Override
    public HistoryFile insertHistoryFile(HistoryFile historyFile) {
          historyfilemapper.insert(historyFile);
          return historyFile;
    }

    @Override
    public List<HistoryFile> selectAll() {
      return  historyfilemapper.selectByExample(new HistoryFileExample());
    }
}
