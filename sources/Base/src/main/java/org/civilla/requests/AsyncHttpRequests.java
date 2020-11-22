package org.civilla.requests;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.civilla.common.Logging;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.concurrent.Future;


class asyncRequestsConfig{
    public static boolean configured;
    public static void configure() {
        if (!configured)
            Unirest.setConcurrency(200, 200);
            Unirest.setTimeouts(10000, 60000);
        asyncRequestsConfig.configured = true;
    }
}


public class AsyncHttpRequests implements AsyncHttpRequestsInterface {

    public AsyncHttpRequests(){
        asyncRequestsConfig.configure();
    }

    protected Callback<JsonNode> getCallback(String url, String body){
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
        for (Iterator<String> it = headers.keys(); it.hasNext(); ) {
            String key = it.next();
            request = request.header(key, headers.get(key).toString());
        }
        return request;
    }

    public static GetRequest prepareHeaders(GetRequest request, JSONObject headers){
        for (Iterator<String> it = headers.keys(); it.hasNext(); ) {
            String key = it.next();
            request = request.header(key, headers.get(key).toString());
        }
        return request;
    }
}

class DummyCallback implements Callback<JsonNode>{
    public void failed(UnirestException e) {}
    public void completed(HttpResponse response) {}
    public void cancelled() {}
}


