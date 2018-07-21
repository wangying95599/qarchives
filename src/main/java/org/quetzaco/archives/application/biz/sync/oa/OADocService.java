package org.quetzaco.archives.application.biz.sync.oa;

import org.quetzaco.archives.model.DocFiles;

import java.util.List;

public interface OADocService {
    int syncDoc();

    int syncFiles();

    void copyFiles(List<DocFiles> docFiles);

    void copyAndTranslateFile(Long fileId);
}
