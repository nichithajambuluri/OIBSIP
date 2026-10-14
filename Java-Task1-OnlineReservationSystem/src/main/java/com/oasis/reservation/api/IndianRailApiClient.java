package com.oasis.reservation.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public final class IndianRailApiClient {
    private final ApiConfig config=ApiConfig.load();
    public boolean enabled(){return config.enabled();}
    public JsonObject get(String endpoint,String... parts) throws Exception {
        if(!config.enabled()) throw new IllegalStateException("Indian Rail API is not configured. Add your API key to config/api.properties.");
        StringBuilder b=new StringBuilder(config.url(endpoint)); b.append("apikey/").append(java.net.URLEncoder.encode(config.key(),java.nio.charset.StandardCharsets.UTF_8)); for(String part:parts) b.append("/").append(java.net.URLEncoder.encode(part,java.nio.charset.StandardCharsets.UTF_8)); String url=b.toString();
        HttpClient client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(config.connectTimeoutSeconds())).build();
        HttpRequest req=HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(config.readTimeoutSeconds())).header("Accept","application/json").GET().build();
        HttpResponse<String> res=client.send(req,HttpResponse.BodyHandlers.ofString());
        if(res.statusCode()<200||res.statusCode()>=300) throw new IllegalStateException("Rail API returned HTTP "+res.statusCode()+".");
        JsonObject json=JsonParser.parseString(res.body()).getAsJsonObject();
        JsonElement status=json.get("Status");
        if(status!=null && !"SUCCESS".equalsIgnoreCase(status.getAsString())) throw new IllegalStateException(json.has("Message")&&!json.get("Message").isJsonNull()?json.get("Message").getAsString():"Rail API request was not successful.");
        return json;
    }
}
