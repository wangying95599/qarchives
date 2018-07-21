package org.quetzaco.archives.application.biz;

import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.model.Archive;
import org.quetzaco.archives.model.Box;

import java.util.List;

/**
 * Created by deya on 2017/7/15.
 */
@Deprecated
public interface BoxService {
    //PageInfo selectArchives(Long deptId, int offset, int limit);
    PageInfo selectBoxs (Long deptId, int offset, int limit);

    void createBox(Box box);

    Box selectBoxDetial(Long boxId);

    List<Archive>selectArchiveFromBox(Long boxId);

    List<String> insertArchiveToBox(Long boxId, List<Archive> archives);

    PageInfo searchBoxList(Box box,int offset,int limit);

    PageInfo searchGlobalBoxList(Box box, int offset, int limit);

    Box selectByFileCode(String reelNum);

    void removeArchiveFromBox(Long boxId, Long archiveId);

    int modifyRecord(Box box);
}
