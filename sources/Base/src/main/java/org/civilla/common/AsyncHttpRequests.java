package org.civilla.common;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.concurrent.Future;

public class AsyncHttpRequests {

    boolean log = false;

    public AsyncHttpRequests(){
        asyncRequestsConfig.configure();
    }

    public AsyncHttpRequests(boolean log){
        asyncRequestsConfig.configure();
        this.log = log;
    }

    public Callback<JsonNode> getCallback(String url, String body){
        if (log)
             return new LoggingCallback(url, body);
        return new DummyCallback();
    }

    public Future<HttpResponse<JsonNode>> get(String url, JSONObject headers){
        GetRequest request = Unirest.get(url);
        request = RequestHelper.prepareHeaders(request, headers);
        return request.asJsonAsync(getCallback(url, ""));
    }

    public Future<HttpResponse<JsonNode>> post(String url, JSONObject headers, String body){
        HttpRequestWithBody request = Unirest.post(url);
        request = RequestHelper.prepareHeaders(request, headers);
        return request.body(body).asJsonAsync(getCallback(url, body));
    }

    public Future<HttpResponse<JsonNode>> patch(String url, JSONObject headers, String body){
        HttpRequestWithBody request = Unirest.patch(url);
        request = RequestHelper.prepareHeaders(request, headers);
        return request.body(body).asJsonAsync(getCallback(url, body));
    }
}

class RequestHelper {
    public static HttpRequestWithBody prepareHeaders(HttpRequestWithBody request, JSONObject headers){
        Iterator<String> keys = headers.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            request = request.header(key, headers.get(key).toString());
        }
        return request;
    }

    public static GetRequest prepareHeaders(GetRequest request, JSONObject headers){
        Iterator<String> keys = headers.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            request = request.header(key, headers.get(key).toString());
        }
        return request;
    }
}

class asyncRequestsConfig{
    public static boolean configured;
    public static void configure() {
        if (!configured)
            Unirest.setConcurrency(100, 20);
        asyncRequestsConfig.configured = true;
    }
}

class LoggingCallback implements Callback<JsonNode>{
    String url;
    String body;
    LoggingCallback(String url, String body){
        this.url = url;
        this.body = body;
    }
    public void failed(UnirestException e) {
        Logging.log.severe(String.join(" ","Request failed: ", url, body));
        e.printStackTrace();
    }
    public void completed(HttpResponse response) {
        Logging.log.info(String.join(" ", "Request completed: ", url, body,
                "status:", Integer.toString(response.getStatus()),
                "response:", response.getBody().toString()));
    }
    public void cancelled() {
        Logging.log.severe(String.join(" ", "Request was cancelled", url, body));
    }
}

class DummyCallback implements Callback<JsonNode>{
    public void failed(UnirestException e) {}
    public void completed(HttpResponse response) {}
    public void cancelled() {}
}

