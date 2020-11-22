package org.civilla.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.civilla.common.Logging;

public class LoggingAsyncHttpRequests extends AsyncHttpRequests {
    protected Callback<JsonNode> getCallback(String url, String body){
        return new LoggingCallback(url, body);
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
