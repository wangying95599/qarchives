package org.quetzaco.archives.application.biz;

import com.github.pagehelper.PageInfo;

import org.quetzaco.archives.model.AcousticImage;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.Record;
import org.quetzaco.archives.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by deya on 2017/7/14.
 */
public interface RecordService {
    PageInfo selectRecords(Long deptId, int offset, int limit,User user);

    void createRecord(Record record);

    Record selectRecordDetial(Long recordId);

    List<Documents> selectDocumentFromRecord(Long recordId);

    List<String> insertDocumentToRecord(Long recordId, List<Documents> docIds, int type,User contextUser);

    PageInfo selectRecordFromArchive(Long archiveId,int offset, int limit);

    PageInfo searchRecordList(Record record,int offset,int limit);

    public Map searchGlobalRecordList(Record record, Documents documents, AcousticImage acousticImage, int offset, int limit);

    List<Record>selectArchiveRecords(Long deptId,String important);

    Record selectByFileCode(String reelNum);

    void removeDocumentFromRecord(Long recordId, Long docId);

    void modifyRecord(Record record);

    PageInfo sortSearchRecordList(Record record,User user);

    List<Record> getRecordsByIds(List<Long> ids);

    int turnOver(Long flowId,Long deptId, List<Record> recordList);
    
    PageInfo selectRoom(int offset, int limit,Long deptId,String title,String fileNum);
}
