package org.quetzaco.archives.application.search.elastic;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchScroll;
import io.searchbox.params.Parameters;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.mapper.DocumentMapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.quetzaco.archives.application.dao.DocumentsMapper;
import org.quetzaco.archives.application.dao.FileMapper;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.DocumentsExample;
import org.quetzaco.archives.model.Files;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult.Hit;

/**
 * @Description Created by dong on 2017/12/13.
 */
public class ElasticSearchDaoTest extends QarchivesApplicationTests {

  @Autowired
  ElasticSearchDao elasticSearchDao;
  @Test
  public void createIndex() throws Exception {
    Documents documents = new Documents();
    documents.setId(198l);
    documents.setFileName("明天");
    JestResult index = elasticSearchDao.createIndex(documents, "archive", "document");
    assertEquals(true,index.isSucceeded());
  }
  protected Gson gson = new GsonBuilder()
          .create();
  @Test
  public void updateIndex() throws Exception {
    Documents documents = new Documents();
    documents.setId(188l);
    documents.setFileName("你1好188 中国");
    Map<String,Documents> map = new HashMap();
    map.put("doc",documents);
    String t = gson.toJson(map);
    JestResult index = elasticSearchDao.updateDocument(t, "archive", "document", ""+188l);
    System.out.println("index                       "+index);
    assertEquals(true,index.isSucceeded());
  }
  
  @Test
  public void searchIndex() throws Exception {
    Documents documents = new Documents();
    documents.setId(2l);
    List<Hit<Documents, Void>> index = elasticSearchDao.searchAll("archive", documents);
    for(Hit<Documents,Void> hit:index) {
    	System.out.println("hit              "+hit.source.getId());
    	System.out.println("hit              "+hit.index);
    	System.out.println("hit              "+hit.type);
    	System.out.println("hit              "+hit.highlight);

    }
    index = elasticSearchDao.createSearch("你好 中国 明天", "archive", documents, "fileName");
    for(Hit<Documents,Void> hit:index) {
    	System.out.println("hit2              "+hit.source.getId());
    	System.out.println("hit              "+hit.index);
    	System.out.println("hit              "+hit.type);
    	System.out.println("hit              "+hit.source.getFileName());
    	System.out.println("hit              "+hit.score);
    	System.out.println("hit              "+hit.highlight);
    	
    }
  }
  
  @Test
  public void deleteIndex() throws Exception{
	  
	  JestResult js = elasticSearchDao.deleteDocument("archive", "document", "AWBPKSlzsdK0h10QBNyn");
	  System.out.println(js.getSourceAsString());
	  
	  elasticSearchDao.deleteIndex("document");
  }
  
  @Test
  public void deleteIndexByType() throws Exception{
	  elasticSearchDao.deleteIndex("document");
  }
    @Autowired
    private JestClient client;

  @Test
  public void deleteRepeatDocs() throws Exception{
      List<String> noExitFileIds = new ArrayList<>();
      SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
      searchSourceBuilder.query(QueryBuilders.matchAllQuery());
      Search search = new Search.Builder(searchSourceBuilder.toString())
              .addIndex("archive")
              .setParameter(Parameters.SCROLL, "1m")
              .setParameter(Parameters.SIZE, "50")
              .build();
      int sum =0;
      int count=0;
      SearchResult result = null;
      List<?> hits = null;
      try {
          result = client.execute(search);
          System.out.println("本次查询共查到：" + result.getTotal() + "个关键字！");
          System.out.println("本次查询共查到：" + result.getTotal() + "个关键字！");
          hits = result.getHits(Files.class);

          String scrollId = result.getJsonObject().get("_scroll_id").getAsString();
          int num = hits.size();
          sum = sum + num;
          System.out.println("num:"+num);
          System.out.println("scrollId"+scrollId);
          System.out.println(result.getSourceAsString());
          delRepeatDocs(hits,noExitFileIds);
          SearchScroll scroll;

          do {
              scroll = new SearchScroll.Builder(scrollId, "1m").build();
              JestResult result1 = null;
              try {
                  result1 = client.execute(scroll);
              } catch (IOException e) {
                  e.printStackTrace();
              }
              List<Files> filesList = result1.getSourceAsObjectList(Files.class);
              int innum = filesList.size();
              sum = sum+innum;
              ++count;
              if(innum==0)break;
              delRepeatDocs1(filesList, noExitFileIds);
          } while (true);
          for(String str :noExitFileIds){
              elasticSearchDao.deleteDocument("archive", "files", str);
          }
          System.out.println("count:"+count);
          System.out.println("sum:"+sum);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  @Autowired
  DocumentsMapper documentsMapper;

    public void delRepeatDocs(List<?> hits,List<String > noExitFileIds) {
        List<String> docIds = new ArrayList<>();
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        for(Object obj :hits){
            Hit<Files,Void> hit = (Hit<Files,Void>)obj;
            String docId = hit.source.getDocId();
            docIds.add(docId);
            if(map.get(docId)==null){
                List<String> fileIds  = new ArrayList<>();
                fileIds.add(hit.source.getFileId());
                map.put(docId,fileIds);
            }else {
                map.get(docId).add(hit.source.getFileId());
            }
        }
        System.out.println("docIds:"+docIds+"/n");
        System.out.println("length:"+docIds.size());
        getRepeatFileIds(noExitFileIds, docIds, map);

        /*for(String str :noExitFileIds){
            elasticSearchDao.deleteDocument("archive", "files", str);
        }*/
    }

    public void delRepeatDocs1(List<Files> hits,List<String > noExitFileIds) {
        List<String> docIds = new ArrayList<>();
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        for(Files files :hits){
            String docId = files.getDocId();
            docIds.add(docId);
            if(map.get(docId)==null){
                List<String> fileIds  = new ArrayList<>();
                fileIds.add(files.getFileId());
                map.put(docId,fileIds);
            }else {
                map.get(docId).add(files.getFileId());
            }
        }
        System.out.println("docIds:"+docIds+"/n");
        System.out.println("length:"+docIds.size());
        getRepeatFileIds(noExitFileIds, docIds, map);

        /*for(String str :noExitFileIds){
            elasticSearchDao.deleteDocument("archive", "files", str);
        }*/
    }

    public void getRepeatFileIds(List<String> noExitFileIds, List<String> docIds, Map<String, List<String>> map) {
        DocumentsExample documentsExample = new DocumentsExample();
        documentsExample.createCriteria().andDocumentLocalIdIn(docIds).andRecordFlagEqualTo("00");
        List<Documents> documentsList = documentsMapper.selectByExample(documentsExample);


        if(documentsList==null||documentsList.size()==0){
            for(List<String> fileIds :map.values()){
                noExitFileIds.addAll(fileIds);
            }
        }else {
            List<String> exitDocIds = new ArrayList<>();
            for(Documents doc :documentsList){
                exitDocIds.add(doc.getDocumentLocalId());
            }
            for(String str :docIds){
                if(!exitDocIds.contains(str)){
                    noExitFileIds.addAll(map.get(str));
                }
            }
        }
    }

    public static void main(String[] args) {
	  Documents documents = new Documents();
	    documents.setId(188l);
	    documents.setFileName("你1好188");
	    
	    Map<String,Documents> list = new HashMap();
	    list.put("doc",documents);
	    
	Gson son = new GsonBuilder().create();
	String str= son.toJson(documents);
	System.out.println(str);
	str= son.toJson(list);
	System.out.println(str);
}
}