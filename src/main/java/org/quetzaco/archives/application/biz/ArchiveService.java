package org.quetzaco.archives.application.biz;

import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.model.Archive;
import org.quetzaco.archives.model.Record;

import java.util.List;

/**
 * Created by deya on 2017/7/15.
 */
public interface ArchiveService {

  PageInfo selectArchives(Long deptId, int offset, int limit);

  void createArchive(Archive archive);

  Archive selectArchiveDetial(Long archiveId);

  List<Record> selectRecordFromArchive(Long archiveId);

  Archive selectByFileCode(String reelNum);

    List<String> insertRecordToArchive(Long archiveId, List<Record> records);

  PageInfo selectArchiveFromBox(Long boxId, int offset, int limit);

  PageInfo searchArchiveList(Archive archive,int offset,int limit);

  PageInfo searchGlobalArchiveList(Archive archive, int offset, int limit);

  List<Archive>selectArchiveArchive(Long deptId,String important);

    void removeRecordFromArchive(Long archiveId, Long recordId);

    int modifyRecord(Archive archive);
}
