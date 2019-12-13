import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * 官网aip             https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-delete-by-query.html
 * 版本 7.2.0
 */
public class Estest {
    public static void main(String[] args) {
        TransportClient client = null;
        try {
            //定义客户端
            Settings settings = Settings.builder()
                    .put("client.transport.sniff", true)
                    .put("cluster.name","my-application").build();
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("172.168.0.100"), 9300));

            Estest test = new Estest();
            //创建IndexRequest
            test.add_index(client);

            //查询
            //test.select_document(client);

            //删除
            //test.del_document(client);

            //query api 删除
            //test.query_del_document(client);
            //test.query_del_document2(client);

            //更新
            //test.update_document(client);

            //批量select
            //test.multiget_document(client);

            //批量插入  bulk api
            //test.bulk_document(client);

            //Using Bulk Processor
            //test.processor_document(client);

            //search
            //test.search_doc(client);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //创建twitter并插入一条数据
    public void add_index(TransportClient client){
        //定义json格式
        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .field("user2", "lisi2")
                    .field("title2", "4442")
                    .field("desc2", "4442")
                    .endObject();
            String json = Strings.toString(builder);
            System.out.println(json);

            IndexResponse response = client.prepareIndex("accounts", "person", "2")
                    .setSource(builder)
                    .get();

            String _index = response.getIndex();
            String _type = response.getType();
            String _id = response.getId();
            long _version = response.getVersion();
            RestStatus status = response.status();
            System.out.println(_index+"--"+_type+"--"+_id+"--"+_version+"--"+status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询单条
    public void select_document(TransportClient client){
        GetResponse response = client.prepareGet("accounts", "person", "1").get();
        String _index = response.getIndex();
        String _type = response.getType();
        String _id = response.getId();
        long _version = response.getVersion();
        System.out.println(_index+"--"+_type+"--"+_id+"--"+_version+"--");
    }

    //删除单条
    public void del_document(TransportClient client){
        DeleteResponse response = client.prepareDelete("twitter", "_doc", "1").get();
        String _index = response.getIndex();
        String _type = response.getType();
        String _id = response.getId();
        long _version = response.getVersion();
        System.out.println(_index+"--"+_type+"--"+_id+"--"+_version+"--");
    }

    //删除 query api (按条件删除)
    public void query_del_document(TransportClient client){
        BulkByScrollResponse response =
                new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
                        .filter(QueryBuilders.matchQuery("user", "lisi"))
                        .source("accounts")
                        .get();
        long deleted = response.getDeleted();
        System.out.println(deleted);
    }

    //因为它可以是一个长时间运行的操作，如果你想异步执行它，你可以调用execute而不是get并提供一个监听器
    public void query_del_document2(TransportClient client){
        new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
                .filter(QueryBuilders.matchQuery("user", "zhangsan"))
                .source("accounts")
                .execute(new ActionListener<BulkByScrollResponse>() {
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted();
                    }
                    public void onFailure(Exception e) {
                        // Handle the exception
                    }
                });
    }

    //更新
    public void update_document(TransportClient client){
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index("accounts");
            updateRequest.type("person");
            updateRequest.id("1");
            updateRequest.doc(jsonBuilder()
                    .startObject()
                    .field("user2", "zhangsan")
                    .field("title2", "333")
                    .field("desc2", "333")
                    .endObject());
            client.update(updateRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update_document2(TransportClient client){
        try {
           /* client.prepareUpdate("ttl", "doc", "1")
                    .setScript(new Script(
                            "ctx._source.gender = \"male\"",
                            ScriptService.ScriptType.INLINE, null, null))
                    .get();*/

            client.prepareUpdate("ttl", "doc", "1")
                    .setDoc(jsonBuilder()
                            .startObject()
                            .field("gender", "male")
                            .endObject())
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //批量 select
    public void multiget_document(TransportClient client){
        try {
            MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                    .add("accounts", "person", "1","2")
                    .get();

            for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
                GetResponse response = itemResponse.getResponse();
                if (response.isExists()) {
                    String json = response.getSourceAsString();
                    System.out.println(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //批量 删除     bulk api
    public void bulk_document(TransportClient client){
        try {
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            // either use client#prepare, or use Requests# to directly build index/delete requests
            bulkRequest.add(client.prepareIndex("twitter", "_doc", "1")
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("user", "aaa")
                            .field("postDate", new Date())
                            .field("message", "trying out Elasticsearch")
                            .endObject()
                    )
            );

            bulkRequest.add(client.prepareIndex("twitter", "_doc", "2")
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("user", "bbb")
                            .field("postDate", new Date())
                            .field("message", "another post")
                            .endObject()
                    )
            );

            BulkResponse bulkResponse = bulkRequest.get();
            if (bulkResponse.hasFailures()) {
                // process failures by iterating through each bulk response item
                System.out.println("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //BulkProcessor
    public void processor_document(TransportClient client){
        try {
            BulkProcessor bulkProcessor = BulkProcessor.builder(
                    client,
                    new BulkProcessor.Listener() {
                        public void beforeBulk(long executionId,
                                               BulkRequest request) {
                            System.out.println("beforebulk"+"----"+request.numberOfActions());
                        }

                        public void afterBulk(long executionId,
                                              BulkRequest request,
                                              BulkResponse response) {
                            System.out.println("afterbulk"+"----"+response.hasFailures());
                        }

                        public void afterBulk(long executionId,
                                              BulkRequest request,
                                              Throwable failure) {
                            System.out.println("afterbulk");
                        }
                    })
                    .setBulkActions(10000)
                    .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                    .setFlushInterval(TimeValue.timeValueSeconds(5))
                    .setConcurrentRequests(1)
                    .setBackoffPolicy(
                            BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                    .build();

            //bulkProcessor.add(new IndexRequest("twitter", "_doc", "3").source(""));
            bulkProcessor.add(new DeleteRequest("twitter", "_doc", "2"));
            bulkProcessor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //未完成
    public void search_doc(TransportClient client){
        QueryBuilder qb = termQuery("multi", "twitter");

        SearchResponse scrollResp = client.prepareSearch("twitter")
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(qb)
                .setSize(100).get(); //max of 100 hits will be returned for each scroll
//Scroll until no hits are returned
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                System.out.println(hit.getIndex());
            }

            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.
    }
}
