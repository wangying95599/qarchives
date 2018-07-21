package org.quetzaco.archives.application.biz;

import com.github.pagehelper.Page;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.Files;

import java.util.List;
import java.util.Map;

public interface ElasticsearchService {
    boolean flagToFalse(List<String> fileIds);

    boolean flagToTrue(List<String> fileIds);

    boolean updateDepId(List<String> fileIds,Long  deptId);

    boolean bulkDel(List<String> fileIds);

    Map createSearch(Documents documents,Page<Files> list);
}
