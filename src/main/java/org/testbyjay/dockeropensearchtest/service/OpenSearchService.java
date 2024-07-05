package org.testbyjay.dockeropensearchtest.service;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenSearchService {
    private final OpenSearchClient client;

    public OpenSearchService(OpenSearchClient client) {
        this.client = client;
    }

    public void createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest.Builder().index(indexName).build();
        client.indices().create(request);
    }

    public <T> void indexDocument(String indexName, String id, T document) throws IOException {
        IndexRequest<T> request = new IndexRequest.Builder<T>()
                .index(indexName)
                .id(id)
                .document(document)
                .build();
        client.index(request);
    }

    public <T> SearchResponse<T> searchDocuments(String indexName, Class<T> tClass) throws IOException {
        return client.search(s -> s.index(indexName), tClass);
    }
}
