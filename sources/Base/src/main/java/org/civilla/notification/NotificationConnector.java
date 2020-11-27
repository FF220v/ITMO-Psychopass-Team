package org.civilla.notification;

import org.asynchttpclient.Response;
import org.civilla.dataclasses.communication.notificationserver.NotificationRequest;
import org.civilla.kubernetes.KubeConfigLoader;
import org.civilla.requests.AsyncHttpRequestsWithRetries;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class NotificationConnector {
    protected static String url = KubeConfigLoader.servicesUrls().get(KubeConfigLoader.NOTIFICATION_SERVER).toString();

    public void notify(String message, String requestId) {
        NotificationRequest request = new NotificationRequest();
        request.message = message;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-request-id", requestId);
        CompletableFuture<Response> resp = AsyncHttpRequestsWithRetries.post(
                String.join("", "http://", url, "/notify"), headers, request.toJson());
    }
}
