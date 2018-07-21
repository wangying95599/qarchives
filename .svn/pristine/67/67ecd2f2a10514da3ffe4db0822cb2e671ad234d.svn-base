package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.application.biz.ArchiveService;
import org.quetzaco.archives.application.dao.ArchiveMapper;
import org.quetzaco.archives.application.dao.RecordMapper;
import org.quetzaco.archives.model.Archive;
import org.quetzaco.archives.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deya on 2017/7/15.
 */
@Service
public class ArchiveServiceImpl implements ArchiveService{
    @Autowired
    ArchiveMapper archiveMapper;

    @Autowired
    RecordMapper recordMapper;

    @Override
    public PageInfo selectArchives(Long deptId, int offset, int limit) {
        PageHelper.startPage(offset, limit);
        List<Archive> list = archiveMapper.selectArchives(deptId);
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);

        return page;
    }
    @Override
    public void createArchive(Archive archive) {
         archiveMapper.createArchive(archive);
    }

    @Override
    public Archive selectArchiveDetial(Long archiveId) {
        return archiveMapper.selectArchiveDetial(archiveId);
    }

    @Override
    public List<Record> selectRecordFromArchive(Long archiveId) {
        return archiveMapper.selectRecordFromArchive(archiveId);
    }

  @Override
  public Archive selectByFileCode(String reelNum) {
    return archiveMapper.selectByFileCode(reelNum,true);// archiveMapper.selectByFileCode(reelNum);
  }

    @Override
    public List<String> insertRecordToArchive(Long archiveId, List<Record> records) {
        List<String> dupTitles = new ArrayList<String>();

        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);

            Record dupRecord = archiveMapper.isDuplicateTitle(archiveId, record.getTitle());

            if (dupRecord == null) {
                Long recordId = record.getId();
                //recordMapper.insertDocumentToRecord(recordId,docId);
                archiveMapper.insertRecordToArchive(archiveId, recordId);
                recordMapper.recordArchiveToArchive(recordId);
            } else {
                dupTitles.add(record.getTitle());
            }
        }
        return dupTitles;
    }

    @Override
    public PageInfo selectArchiveFromBox(Long boxId, int offset, int limit) {
        PageHelper.startPage(offset, limit);
        List<Archive> list = archiveMapper.selectArchiveFromBox(boxId);
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);

        return page;
    }

    @Override
    public PageInfo searchArchiveList(Archive archive, int offset, int limit) {
        PageHelper.startPage(offset,limit);
        List<Archive> list = archiveMapper.searchArchiveList(archive);
        PageInfo page = new PageInfo(list);

        return page;
    }

    @Override
    public PageInfo searchGlobalArchiveList(Archive archive, int offset, int limit) {
      PageHelper.startPage(offset, limit);
      List<Archive> list = archiveMapper.searchGlobalArchiveList(archive);
      PageInfo page = new PageInfo(list);

      return page;
    }

    @Override
    public List<Archive> selectArchiveArchive(Long deptId, String important) {
        return archiveMapper.selectArchiveArchive(deptId,important);
    }

    @Override
    public void removeRecordFromArchive(Long archiveId, Long recordId) {
        archiveMapper.removeRecordFromArchive(archiveId, recordId);
        recordMapper.archiveToRecord(recordId);
    }

    @Override
    public int modifyRecord(Archive archive) {
        return archiveMapper.updateByPrimaryKeySelective(archive);
    }
}
