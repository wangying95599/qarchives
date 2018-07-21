package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import org.quetzaco.archives.application.biz.ElasticsearchService;
import org.quetzaco.archives.application.biz.UserService;
import org.quetzaco.archives.application.search.elastic.ElasticSearchDao;
import org.quetzaco.archives.model.Dept;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.Files;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.util.boot.RoleType;
import org.quetzaco.archives.util.config.ElasticsearchProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService
{
    @Autowired
    JestClient client;
    @Autowired
    ElasticSearchDao elasticSearchDao;
    @Autowired
    ElasticsearchProperties elasticsearchProperties;
    @Autowired
    UserService userService;
    @Value("${kmhk.deptId}")
    Long KMHK_DEPTID;
    @Override
    public boolean flagToFalse(List<String> fileIds) {
        Files files = new Files();
        files.setRecordFlag(false);
        return bulkUpdateDocument(fileIds, files);
    }

    @Override
    public boolean flagToTrue(List<String> fileIds) {
        Files files = new Files();
        files.setRecordFlag(true);
        return bulkUpdateDocument(fileIds, files);
    }

    @Override
    public boolean updateDepId(List<String> fileIds, Long deptId) {
        Files files = new Files();
        files.setDeptId(deptId);
        return bulkUpdateDocument(fileIds,files);
    }

    @Override
    public boolean bulkDel(List<String> fileIds) {
        if(fileIds==null||fileIds.isEmpty()) return false;
        Bulk.Builder bulkBuilder = new Bulk.Builder();
        fileIds.forEach(x->{
            Delete delete = new Delete.Builder(x).index(elasticsearchProperties.getIndex()).type(elasticsearchProperties.getType()).build();
            bulkBuilder.addAction(delete);
        });
        try {
            JestResult result = client.execute(bulkBuilder.build());
            return result.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map createSearch(Documents documents, Page<Files> list) {
        Page thisPage = elasticSearchDao.createSearch(documents, list, true);
        Long deptId = documents.getDeptId();
        long otherHave = 0;
        boolean isManager = true;
        //如果是整理员的话不去搜是否其他部门存在
        Long roleId = userService.getRoleByUsr();

        //如果是昆明航空有限公司部门档案管理员，也可以搜索到全部的（为了实现协查借阅时对所有文档的搜索）。
        User contextUser = userService.getContextUser();
        Dept dept = contextUser.getDept();
        if(!deptId.equals(88888l)&& !RoleType.DEPT_ARRANGE.equals(roleId)&& !KMHK_DEPTID.equals(dept.getId())){
            Page otherPage = elasticSearchDao.createSearch(documents, list, false);
            otherHave = otherPage.getTotal();
            isManager = false;
        }
        Map map = new HashMap();
        map.put("thisPage", thisPage);
        map.put("otherHave", otherHave);
        map.put("isManager", isManager);
        return map;
    }

    public boolean bulkUpdateDocument(List<String> fileIds, Files files) {
        Map map = new HashMap<>();
        map.put("doc",files);
        Gson gson = new GsonBuilder().create();
        String ss = gson.toJson(map);
        return elasticSearchDao.bulkUpdateDocument(fileIds,ss);
    }


}
