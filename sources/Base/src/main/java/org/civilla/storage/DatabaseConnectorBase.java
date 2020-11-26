package org.civilla.storage;

import org.asynchttpclient.Response;
import org.civilla.dataclasses.communication.mongodbproxy.MongoDBProxyPostResponse;
import org.civilla.dataclasses.communication.mongodbproxy.MongoDBProxyQueryRequest;
import org.civilla.kubernetes.KubeConfigLoader;
import org.civilla.requests.AsyncHttpRequestsWithRetries;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DatabaseConnectorBase {
    protected static String databaseUrl = KubeConfigLoader.servicesUrls().get(KubeConfigLoader.DATABASE_PROXY_SERVER).toString();

    public String get(String objectId, String requestId, String endpoint) throws ExecutionException, InterruptedException {
        return getByField("objectId", objectId, requestId, endpoint);
    }

    public String getByField(String field, String value, String requestId, String endpoint) throws ExecutionException, InterruptedException {
        MongoDBProxyQueryRequest request = new MongoDBProxyQueryRequest();
        request.field = field;
        request.value = value;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-request-id", requestId);
        CompletableFuture<Response> resp = AsyncHttpRequestsWithRetries.put(
                String.join("", "http://", databaseUrl, "/", endpoint), headers, request.toJson());
        return resp.get().getResponseBody();
    }


    public MongoDBProxyPostResponse update(String requestBody, String requestId, String endpoint) throws ExecutionException, InterruptedException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-request-id", requestId);
        CompletableFuture<Response> resp = AsyncHttpRequestsWithRetries.post(
                String.join("", "http://", databaseUrl, "/", endpoint), headers, requestBody);
        return MongoDBProxyPostResponse.fromJson(resp.get().getResponseBody());
    }
}