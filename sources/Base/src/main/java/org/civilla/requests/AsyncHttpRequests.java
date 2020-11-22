package org.civilla.requests;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

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
        requestBuilder = RequestHelper.prepareHeaders(requestBuilder, headers);
        Request request = requestBuilder.build();
        return client.executeRequest(request).toCompletableFuture();
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