package org.quetzaco.archives.application.biz.Impl;

import java.util.Date;
import java.util.List;

import org.quetzaco.archives.application.biz.FolderService;
import org.quetzaco.archives.application.dao.FolderMapper;
import org.quetzaco.archives.model.Folder;
import org.quetzaco.archives.model.FolderExample;
import org.quetzaco.archives.util.doc.DocConst;
import org.quetzaco.archives.util.doc.DocUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("folderService")
public class FolderServiceImpl implements FolderService {

	final static Logger logger = LoggerFactory.getLogger(FolderServiceImpl.class);
	@Autowired
	FolderMapper folderMapper;

	@Override
	public Folder createFolder(Folder folder) {
		folder.setCreatedDt(new Date());
		folder.setUpdatedDt(new Date());
		folder.setRecordFlag("01");
		folder.setId(DocUtil.getGUID());
		folder.setOrderNum(9999);
		folder.setAncestorType(DocConst.ANCESTOR_ORG);
		folderMapper.insertSelective(folder);
		return folder;
	}

	@Override
	public void deleteFolder(String id) {
		FolderExample folderExample = new FolderExample();
        folderExample.createCriteria().andIdEqualTo(id);
        folderMapper.deleteByExample(folderExample);
	}

	@Override
	public Folder updateFolder(Folder folder) {
		FolderExample folderExample = new FolderExample();
        folderExample.createCriteria().andIdEqualTo(folder.getId());
        
        folder.setUpdatedDt(new Date());
        int flag = folderMapper.updateByExampleSelective(folder, folderExample);
        logger.debug("updateFolder.folder  "+folder);
        logger.debug("updateFolder.id  "+flag);
        return folder;
	}

	@Override
	public Folder getFolder(String id) {
		FolderExample e = new FolderExample();
		FolderExample.Criteria c = e.createCriteria();
		c.andIdEqualTo(id);
		List<Folder> result = folderMapper.selectByExample(e);
		return result.get(0);
	}

	@Override
	public List<Folder> getAllFolder() {
		FolderExample e = new FolderExample();
		e.setOrderByClause("updated_dt desc");
		return folderMapper.selectByExample(e);
	}
	
	public static void main(String[] args) {
		FolderServiceImpl s = new FolderServiceImpl();
		s.getFolder("76");
	}

	
    @Override
    public List<Folder> getFoldersByUser(Long usrId,String folderId) {
    	//不用部门的权限判断，用dms的权限判断。这里先不检查权限
		FolderExample e = new FolderExample();
		FolderExample.Criteria c = e.createCriteria();
		c.andParentIdEqualTo(folderId);
		List<Folder> foldersList = folderMapper.selectByExample(e);
		
        addHasChilds(foldersList);
        return foldersList;
    }
    
    public void addHasChilds(List<Folder> folders) {
        List<String> ids = folderMapper.getHaveChildDept();
        for (Folder folder : folders) {
            if (ids.contains(folder.getId())) {
                folder.setHasChilds(true);
            } else {
                folder.setHasChilds(false);
            }
        }
    }
}
