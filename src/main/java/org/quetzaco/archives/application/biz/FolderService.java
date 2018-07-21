package org.quetzaco.archives.application.biz;

import java.util.List;

import org.quetzaco.archives.model.Folder;

public interface FolderService {
	Folder createFolder(Folder folder);
	void deleteFolder(String id);
	Folder updateFolder(Folder folder);
	Folder getFolder(String id);
	List<Folder> getAllFolder();
	
  	List<Folder> getFoldersByUser(Long usrId,String folderId);
}
