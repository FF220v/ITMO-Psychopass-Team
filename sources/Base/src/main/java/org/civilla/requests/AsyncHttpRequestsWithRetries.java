package org.civilla.requests;
import com.nurkiewicz.asyncretry.RetryContext;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;
import org.civilla.common.Logging;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncHttpRequestsWithRetries extends AsyncHttpRequests{

    protected static boolean IsBadStatus(CompletableFuture<Response> future) {
        boolean isBadStatus = true;
        try {
            isBadStatus = future.get().getStatusCode() >= 500;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return isBadStatus;
    }

    // This provides ~10 seconds of retries
    protected static int initialDelay = 200;
    protected static int multiplier = 2;
    protected static int retriesCount = 6;


    public static CompletableFuture<Response> get(String url, HashMap<String, String> headers) {
        return makeRequestFuture(url, headers, "", HttpConstants.Methods.GET);
    }

    public static CompletableFuture<Response> post(String url, HashMap<String, String> headers, String body) {
        return makeRequestFuture(url, headers, body, HttpConstants.Methods.POST);
    }

    public static CompletableFuture<Response> patch(String url, HashMap<String, String> headers, String body) {
        return makeRequestFuture(url, headers, body, HttpConstants.Methods.PATCH);
    }

    public static CompletableFuture<Response> put(String url, HashMap<String, String> headers, String body) {
        return makeRequestFuture(url, headers, body, HttpConstants.Methods.PUT);
    }

    public static CompletableFuture<Response> get(String url, HashMap<String, String> headers, int initialDelay, int multiplier, int retriesCount) {
        return makeRequestFuture(url, headers, "", HttpConstants.Methods.GET, initialDelay, multiplier, retriesCount);
    }

    public static CompletableFuture<Response> post(String url, HashMap<String, String> headers, String body, int initialDelay, int multiplier, int retriesCount) {
        return makeRequestFuture(url, headers, body, HttpConstants.Methods.POST, initialDelay, multiplier, retriesCount);
    }

    public static CompletableFuture<Response> patch(String url, HashMap<String, String> headers, String body, int initialDelay, int multiplier, int retriesCount) {
        return makeRequestFuture(url, headers, body, HttpConstants.Methods.PATCH, initialDelay, multiplier, retriesCount);
    }

    public static CompletableFuture<Response> put(String url, HashMap<String, String> headers, String body, int initialDelay, int multiplier, int retriesCount) {
        return makeRequestFuture(url, headers, body, HttpConstants.Methods.PUT, initialDelay, multiplier, retriesCount);
    }

    public static CompletableFuture<Response> makeRequestFuture(String url, HashMap<String, String> headers, String body, String method) {
        return makeRequestFuture(url, headers, body, method, initialDelay, multiplier, retriesCount);
    }

    public static CompletableFuture<Response> makeRequestFuture(String url, HashMap<String, String> headers,
                                                                String body, String method,
                                                                int initialDelay, int multiplier, int retriesCount) {
        RetryWrapper retriedRequest = new RetryWrapper(initialDelay, multiplier, retriesCount) {
            @Override
            public CompletableFuture<Response> action(RetryContext retryContext) {
                return AsyncHttpRequests.makeRequestFuture(url, headers, body, method);
            }
            @Override
            public boolean retryOnResult(CompletableFuture<Response> future, RetryContext retryContext) {
                return IsBadStatus(future);
            }
        };
        return retriedRequest.execute();
    }

}
