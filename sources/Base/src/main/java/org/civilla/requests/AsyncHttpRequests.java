package org.civilla.requests;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.civilla.common.Logging;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncHttpRequests{

    protected static AsyncHttpClient client = Dsl.asyncHttpClient();

    public static CompletableFuture<Response> get(String url, HashMap<String, String> headers) {
        return makeRequestFuture(url, headers, "", HttpConstants.Methods.GET);
    }

    public static CompletableFuture<Response> post(String url, HashMap<String, String> headers, String body) {
        return makeRequestFuture(url, headers, body, HttpConstants.Methods.POST);
    }

    public static CompletableFuture<Response> patch(String url, HashMap<String, String> headers, String body) {
        return makeRequestFuture(url, headers, body, HttpConstants.Methods.PATCH);
    }

    protected static CompletableFuture<Response> makeRequestFuture(String url, HashMap<String, String> headers,
                                                                   String body, String method){
        RequestBuilder requestBuilder = new RequestBuilder(method).setUrl(url).setBody(body);
        String requestId = UUID.randomUUID().toString();
        headers.put("X-request-id", requestId);
        requestBuilder = RequestHelper.prepareHeaders(requestBuilder, headers);
        Request request = requestBuilder.build();
        Logging.log.info(String.join(" ", "Request", requestId, method, url, body));
        ListenableFuture<Response> listenableFuture = client.executeRequest(request);
        return listenableFuture.addListener(() -> {
            try {
                Logging.log.info(String.join(" ", "Response", requestId, method, url,
                        Integer.toString(listenableFuture.get().getStatusCode()),
                        listenableFuture.get().getResponseBody()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Executors.newCachedThreadPool()).toCompletableFuture();
    }
}

class RequestHelper {
    public static RequestBuilder prepareHeaders(RequestBuilder requestBuilder, HashMap<String, String> headers) {
        for (String key : headers.keySet()) {
            requestBuilder = requestBuilder.setHeader(key, headers.get(key));
        }
        return requestBuilder;
    }
}