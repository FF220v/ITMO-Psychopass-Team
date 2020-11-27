package org.civilla.analysis;

import org.asynchttpclient.Response;
import org.civilla.dataclasses.communication.analysisserver.AnalysisRequest;
import org.civilla.dataclasses.communication.analysisserver.AnalysisResponse;
import org.civilla.kubernetes.KubeConfigLoader;
import org.civilla.requests.AsyncHttpRequestsWithRetries;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AnalysisConnector {
    protected static String url = KubeConfigLoader.servicesUrls().get(KubeConfigLoader.ANALYSIS_SERVER).toString();

    public Double analyse(String userId, String serviceName, String requestId) throws ExecutionException, InterruptedException {
        AnalysisRequest request = new AnalysisRequest();
        request.userId = userId;
        request.serviceName = serviceName;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-request-id", requestId);
        CompletableFuture<Response> resp = AsyncHttpRequestsWithRetries.post(
                String.join("", "http://", url, "/analyse"), headers, request.toJson());
        AnalysisResponse response = AnalysisResponse.fromJson(resp.get().getResponseBody());
        if(response.status.equals("ok"))
            return response.psychoPassValue;
        return null;
    }
}
