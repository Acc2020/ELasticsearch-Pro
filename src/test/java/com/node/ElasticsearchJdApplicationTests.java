package com.node;

import com.node.utils.ESconst;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ElasticsearchJdApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Test
    void contextLoads() throws IOException {
         String  indexName = ESconst.ES_INDEX_JD;
        System.out.println(indexName);
        GetIndexRequest request = new GetIndexRequest(indexName);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);

    }
}
