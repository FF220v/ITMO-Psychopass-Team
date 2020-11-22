package org.civilla.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;

import java.util.concurrent.Future;

interface AsyncHttpRequestsInterface {
    Future<HttpResponse<JsonNode>> get(String url, JSONObject headers);

    Future<HttpResponse<JsonNode>> post(String url, JSONObject headers, String body);

    Future<HttpResponse<JsonNode>> patch(String url, JSONObject headers, String body);
}
