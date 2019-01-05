package build.dream.common.utils;

import build.dream.common.basic.BasicDomain;
import build.dream.common.constants.Constants;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.Map;

public class ElasticsearchUtils {
    private static TransportClient transportClient;

    public static TransportClient obtainTransportClient() {
        if (transportClient == null) {
            transportClient = ApplicationHandler.getBean(TransportClient.class);
        }
        return transportClient;
    }

    public static IndexResponse index(String index, String type, BasicDomain domain) throws IOException {
        return index(index, type, domain, Constants.DEFAULT_DATE_PATTERN);
    }

    public static IndexResponse index(String index, String type, BasicDomain domain, String datePattern) throws IOException {
        Map<String, Object> map = JacksonUtils.readValueAsMap(JacksonUtils.writeValueAsString(domain, datePattern), String.class, Object.class);
        XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            contentBuilder.field(entry.getKey(), entry.getValue());
        }
        contentBuilder.endObject();
        IndexResponse indexResponse = obtainTransportClient().prepareIndex(index, type, domain.getId().toString()).setSource(contentBuilder).get();
        return indexResponse;
    }

    public static DeleteResponse delete(String index, String type, String id) {
        return obtainTransportClient().prepareDelete(index, type, id).get();
    }
}
