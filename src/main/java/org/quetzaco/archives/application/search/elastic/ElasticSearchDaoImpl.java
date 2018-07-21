package org.quetzaco.archives.application.search.elastic;

import com.github.pagehelper.Page;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.cluster.Health;
import io.searchbox.cluster.NodesInfo;
import io.searchbox.cluster.NodesStats;
import io.searchbox.core.*;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.core.SuggestResult.Suggestion;
import io.searchbox.indices.*;
import io.searchbox.params.Parameters;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.quetzaco.archives.application.biz.UserService;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.Files;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.util.boot.RoleType;
import org.quetzaco.archives.util.config.ElasticsearchProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * es操作实现类
 */

@Repository
public class ElasticSearchDaoImpl implements ElasticSearchDao {

  final static Logger logger = LoggerFactory.getLogger(ElasticSearchDaoImpl.class);

  private String indexKey = "document";
  @Autowired
  private JestClient client;
  @Autowired
  private ElasticsearchProperties elasticsearchProperties;
  @Autowired
  private UserService userService;
    @Value("${kmhk.deptId}")
    Long KMHK_DEPTID;

  @Override
  public JestResult deleteIndex(String type) {
    DeleteIndex deleteIndex = new DeleteIndex.Builder(type).build();
    JestResult result = null;
    try {
      result = client.execute(deleteIndex);
      logger.info("deleteIndex == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult clearCache() {
    ClearCache closeIndex = new ClearCache.Builder().build();
    JestResult result = null;
    try {
      result = client.execute(closeIndex);
      logger.info("clearCache == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult closeIndex(String type) {
    CloseIndex closeIndex = new CloseIndex.Builder(type).build();
    JestResult result = null;
    try {
      result = client.execute(closeIndex);
      logger.info("closeIndex == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult optimizeIndex() {
    Optimize optimize = new Optimize.Builder().build();
    JestResult result = null;
    try {
      result = client.execute(optimize);
      logger.info("optimizeIndex == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult flushIndex() {
    Flush flush = new Flush.Builder().build();
    JestResult result = null;
    try {
      result = client.execute(flush);
      logger.info("flushIndex == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult indicesExists() {
    IndicesExists indicesExists = new IndicesExists.Builder(indexKey).build();
    JestResult result = null;
    try {
      result = client.execute(indicesExists);
      logger.info("indicesExists == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult nodesInfo() {
    NodesInfo nodesInfo = new NodesInfo.Builder().build();
    JestResult result = null;
    try {
      result = client.execute(nodesInfo);
      logger.info("nodesInfo == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult health() {
    Health health = new Health.Builder().build();
    JestResult result = null;
    try {
      result = client.execute(health);
      logger.info("health == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult nodesStats() {
    NodesStats nodesStats = new NodesStats.Builder().build();
    JestResult result = null;
    try {
      result = client.execute(nodesStats);
      logger.info("nodesStats == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult updateDocument(String script1, String index, String type, String id) {
	  
        String script = "{" +
                "    \"doc\" : {" +
                "        \"id\" : \""+id+"\"," +
                "        \"fileName\" : \"188你好"+"\"" +
                "    }" +
                "}";
    Update update = new Update.Builder(script1).index(index).type(type).id(id).build();
    JestResult result = null;
    try {
      result = client.execute(update);
    
      logger.info("updateDocument == " + result.getJsonString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public JestResult deleteDocument(String index, String type, String id) {
	 
    Delete delete = new Delete.Builder(id).index(index).type(type).build();
    JestResult result = null;
    try {
      result = client.execute(delete);
      logger.info("deleteDocument == " + result.getJsonString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public <T> JestResult getDocument(T object, String index, String type, String id) {
    Get get = new Get.Builder(index, id).type(type).build();
    JestResult result = null;
    try {
      result = client.execute(get);
      T o = (T) result.getSourceAsObject(object.getClass());
      for (Method method : o.getClass().getMethods()) {
        logger.info("getDocument == " + method.getName());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;

  }

  @Override
  public List<Suggestion> suggest() {
    String suggestionName = "my-suggestion";
    Suggest suggest = new Suggest.Builder("{" +
        "  \"" + suggestionName + "\" : {" +
        "    \"text\" : \"the amsterdma meetpu\"," +
        "    \"term\" : {" +
        "      \"field\" : \"body\"" +
        "    }" +
        "  }" +
        "}").build();
    SuggestResult suggestResult = null;
    List<SuggestResult.Suggestion> suggestionList = null;
    try {
      suggestResult = client.execute(suggest);
      logger.info("suggestResult.isSucceeded() == " + suggestResult.isSucceeded());
      suggestionList = suggestResult.getSuggestions(suggestionName);
      logger.info("suggestionList.size() == " + suggestionList.size());
      for (SuggestResult.Suggestion suggestion : suggestionList) {
        System.out.println(suggestion.text);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return suggestionList;
  }

  @Override
  public <T> List<Hit<T, Void>> searchAll(String index, T o) {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    Search search = new Search.Builder(searchSourceBuilder.toString())
        .addIndex(index)
        .build();
    SearchResult result = null;
    List<?> hits = null;
    try {
      result = client.execute(search);
      System.out.println("本次查询共查到：" + result.getTotal() + "个关键字！");
      logger.info("本次查询共查到：" + result.getTotal() + "个关键字！");
      hits = result.getHits(o.getClass());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (List<Hit<T, Void>>) hits;
  }

  @Override
  public <T> List<Hit<T, Void>> createSearch(String keyWord, String type, T o, String... fields) {
	   SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	    searchSourceBuilder.query(QueryBuilders.queryStringQuery(keyWord));
	    HighlightBuilder highlightBuilder = new HighlightBuilder();
	    highlightBuilder.requireFieldMatch(false);
	    for (String field : fields) {
	      highlightBuilder.field(field).requireFieldMatch(false);//高亮field
	    }
	    highlightBuilder.preTags("<span style='color:red'>").postTags("</span>");//高亮标签
	    highlightBuilder.fragmentSize(200);//高亮内容长度
	    searchSourceBuilder.highlighter(highlightBuilder);
	    Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(type).build();
	    SearchResult result = null;
	    List<?> hits = null;
	    try {
	      result = client.execute(search);
	      System.out.println("本次查询共查到：" + result.getTotal() + "个结果！");

	      hits = result.getHits(o.getClass());

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return (List<Hit<T, Void>>) hits;
  }

    @Override
    public <T> Page createSearch(Documents document, Page list,boolean isThisDept) {
        Page<Files> page = new Page<>();
        page.setStartRow(list.getStartRow());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        mustQuery.must(QueryBuilders.matchAllQuery());
        Long deptId = document.getDeptId();
        mustQuery.must(QueryBuilders.termsQuery("recordFlag", true));
        if (!deptId.equals(88888l)&&!KMHK_DEPTID.equals(deptId)) {//代表不是查找所有部门
            if(isThisDept)
                mustQuery.must(QueryBuilders.termsQuery("deptId", String.valueOf(deptId)));
            else
                mustQuery.mustNot(QueryBuilders.termsQuery("deptId", String.valueOf(deptId)));
        }
        if (document.getArchiveType() != null && !"".equals(document.getArchiveType())
                && !"ALL".equalsIgnoreCase(document.getArchiveType())) {
            String[] strs = document.getArchiveType().split("_");

            mustQuery.must(QueryBuilders.termsQuery("archiveType", strs)); // 添加第3条must的条件
        }
        if (document.getArchiveYear() != null && !"".equals(document.getArchiveYear())) {
            mustQuery.must(QueryBuilders.termQuery("archiveYear", document.getArchiveYear())); // 添加第3条must的条件
        }
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(document.getTitle(),"content","fileName");//.escape(true)//escape 转义 设为true，避免搜索[]、结尾为!的关键词时异常 但无法搜索*
                // .defaultOperator(org.elasticsearch.index.query.Operator.AND);//不同关键词之间使用and关系
        if ("option2".equals(document.getOption())) {
            queryBuilder = QueryBuilders.multiMatchQuery(document.getTitle(),"fileName");
        }
        mustQuery.must(queryBuilder);//添加第4条must的条件 关键词全文搜索筛选条件

        Long roleId = userService.getRoleByUsr();
        if(RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId)){
            User contextUser = userService.getContextUser();
            Long usrId = contextUser.getId();
            mustQuery.must(QueryBuilders.termQuery("arrangedBy",usrId));
        }


        searchSourceBuilder.query(mustQuery);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.field("fileName").requireFieldMatch(false);//高亮field
        if (!"option2".equals(document.getOption())) {
            highlightBuilder.field("content").requireFieldMatch(false);//高亮field
        }
        highlightBuilder.preTags("<span style='color:red'>").postTags("</span>");//高亮标签
        highlightBuilder.fragmentSize(200);//高亮内容长度
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.from((page.getStartRow() - 1) * 10);
        searchSourceBuilder.size(10);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(elasticsearchProperties.getIndex()).build();
        System.out.println(searchSourceBuilder.toString());
        SearchResult result = null;

        List<?> hits = null;
        try {
            result = client.execute(search);
            System.out.println("开始行：" + ((page.getStartRow() - 1) * 10));
            System.out.println("本次查询共查到：" + result.getTotal() + "个结果！");
            page.setTotal(result.getTotal());

            hits = result.getHits((new Files()).getClass());
            boolean iscontent = false;
            for (Object obj : hits) {
                Hit<Files, Void> hit = (Hit<Files, Void>) obj;
//           	System.out.println("hit2              "+hit.source.getId());
                System.out.println("hit2              " + hit.source.getFileName());

                iscontent = false;
                if (hit.highlight != null) {
                    if (hit.highlight.get("content") != null) {
                        hit.source.setContent(hit.highlight.get("content") + "");
                        iscontent = true;
                    }
                    if (hit.highlight.get("fileName") != null)
                        hit.source.setFileName(hit.highlight.get("fileName") + "");
                }
                if (!iscontent && hit.source.getContent() != null && hit.source.getContent().length() > 300) {
                    hit.source.setContent(hit.source.getContent().substring(0, 200));
                }

                page.add(hit.source);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

  @Override
  public <T> void bulkIndex(String index, String type, T o) {
    Bulk bulk = new Bulk.Builder()
        .defaultIndex(index)
        .defaultType(type)
        .addAction(Arrays.asList(
            new Index.Builder(o).build()
        )).build();
    try {
      client.execute(bulk);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public <T> JestResult createIndex(T o, String index, String type) {
	String id = Index.getIdFromSource(o);
	System.out.println("id               "+id);  
    Index index1 = new Index.Builder(o).index(index).type(type).build();
    JestResult jestResult = null;
    try {
      jestResult = client.execute(index1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return jestResult;
  }

  @Override
  public JsonObject searchEvent(String param) {
    JsonObject returnData = new JsonParser().parse(param).getAsJsonObject();
    Search search = new Search.Builder(returnData.toString()).addType("event").addIndex("pi")
        .build();

//      Gson gs = new Gson();
//      System.out.println("输入参数为：" + "\n" + gs.toJson(search));
    SearchResult result = null;
    try {
      result = client.execute(search);
//          System.out.println("\n" + gs.toJson(result.getJsonObject()) + "\n" );
//          System.out.println("本次查询共查到：" + "\n" +result.getTotal()+"个结果！");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result.getJsonObject();
  }

  @Override
  public JsonObject searchEventHistogramByScroll(String scrollId) {
    SearchScroll scroll = new SearchScroll.Builder(scrollId, "1m").build();
    JestResult result = null;
    try {
      result = client.execute(scroll);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result.getJsonObject();
  }

  @Override
  public JsonObject searchInitEventHistogram(String param) {
    JsonObject returnData = new JsonParser().parse(param).getAsJsonObject();
    Search search = new Search.Builder(returnData.toString())
        .addIndex("pi")
        .addType("event")
        .setParameter(Parameters.SCROLL, "1m")
        .build();

    JestResult result = null;

    try {
      result = client.execute(search);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result.getJsonObject();
  }

    @Override
    public boolean bulkUpdateDocument(List<String> fileIds,String ss ) {
        if(fileIds==null||fileIds.isEmpty())return false;
        Bulk.Builder bulkBuilder = new Bulk.Builder();
        fileIds.forEach(x->{
            Update update = new Update.Builder(ss).index(elasticsearchProperties.getIndex()).type(elasticsearchProperties.getType()).id(x).build();
            bulkBuilder.addAction(update);
        });
        try {
            JestResult result = client.execute(bulkBuilder.build());
            client.shutdownClient();
            return result.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}