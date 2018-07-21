package org.quetzaco.archives.util.config;

import io.searchbox.client.JestClient;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import java.io.IOException;
import org.junit.Test;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description Created by dong on 2017/12/13.
 */
public class ElasticSearchTest extends QarchivesApplicationTests {

  @Autowired
  JestClient client;
  @Test
  public void test(){
    try {
      boolean indexExists = client.execute(new IndicesExists.Builder("jug").build()).isSucceeded();
      if (indexExists) {
        client.execute(new DeleteIndex.Builder("jug").build());
      }
      client.execute(new CreateIndex.Builder("jug").build());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
