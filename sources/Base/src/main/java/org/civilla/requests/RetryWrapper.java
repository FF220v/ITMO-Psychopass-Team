package org.civilla.requests;
import com.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.nurkiewicz.asyncretry.RetryExecutor;
import org.asynchttpclient.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public abstract class RetryWrapper {
    int initialDelay;
    int multiplier;
    int retriesCount;

    public abstract CompletableFuture<Response> action();
    public abstract boolean retryOnResult(CompletableFuture<Response> future) throws ExecutionException, InterruptedException;

    public RetryWrapper(int initialDelay, int multiplier, int retriesCount){
        this.initialDelay = initialDelay;
        this.multiplier = multiplier;
        this.retriesCount = retriesCount;
    }

    protected static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);

    public CompletableFuture<Response> execute() {
        RetryExecutor retryExecutor = new AsyncRetryExecutor(executorService).
                retryOn(RetryIsNeeded.class).
                withExponentialBackoff(initialDelay, multiplier).
                withMaxDelay(10000).
                withUniformJitter();

        return retryExecutor.getFutureWithRetry(retryContext -> {
            CompletableFuture<Response> future = action();
            if (retryContext.getRetryCount() <= retriesCount && retryOnResult(future)) {
                throw new RetryIsNeeded();
            }
            return future;
        });
    }
}

class RetryIsNeeded extends Exception{}
