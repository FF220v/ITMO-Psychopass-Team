package org.civilla.requests;
import com.nurkiewicz.asyncretry.RetryContext;
import org.asynchttpclient.Response;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncHttpRequestsWithRetries extends AsyncHttpRequests{

    protected static boolean IsBadStatus(CompletableFuture<Response> future) throws ExecutionException, InterruptedException {
        boolean isBadStatus = future.get().getStatusCode() >= 500;

        return future.get().getStatusCode() >= 500;
    }

    // This provides ~10 seconds of retries
    protected static int initialDelay = 200;
    protected static int multiplier = 2;
    protected static int retriesCount = 5;

    public static CompletableFuture<Response> get(String url, HashMap<String, String> headers) {
        RetryWrapper retriedRequest = new RetryWrapper(initialDelay, multiplier, retriesCount) {
            @Override
            public CompletableFuture<Response> action(RetryContext retryContext) {
                return AsyncHttpRequests.get(url, headers);
            }
            @Override
            public boolean retryOnResult(CompletableFuture<Response> future, RetryContext retryContext) throws ExecutionException, InterruptedException {
                return IsBadStatus(future);
            }
        };
        return retriedRequest.execute();
    }

    public static CompletableFuture<Response> post(String url, HashMap<String, String> headers, String body) {
        RetryWrapper retriedRequest = new RetryWrapper(initialDelay, multiplier, retriesCount) {
            @Override
            public CompletableFuture<Response> action(RetryContext retryContext) {
                return AsyncHttpRequests.post(url, headers, body);
            }
            @Override
            public boolean retryOnResult(CompletableFuture<Response> future, RetryContext retryContext) throws ExecutionException, InterruptedException {
                return IsBadStatus(future);
            }
        };
        return retriedRequest.execute();
    }

    public static CompletableFuture<Response> patch(String url, HashMap<String, String> headers, String body) {
        RetryWrapper retriedRequest = new RetryWrapper(initialDelay, multiplier, retriesCount) {
            @Override
            public CompletableFuture<Response> action(RetryContext retryContext) {
                return AsyncHttpRequests.patch(url, headers, body);
            }
            @Override
            public boolean retryOnResult(CompletableFuture<Response> future, RetryContext retryContext) throws ExecutionException, InterruptedException {
                return IsBadStatus(future);
            }
        };
        return retriedRequest.execute();
    }
}
