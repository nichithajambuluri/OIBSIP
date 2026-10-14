package com.oasis.reservation.api;

import com.google.gson.*;
import java.util.*;

public final class RailApiService {
    private final IndianRailApiClient client=new IndianRailApiClient();
    public boolean enabled(){return client.enabled();}
    public JsonObject raw(String endpoint,String... parts)throws Exception{return client.get(endpoint,parts);}
    public JsonObject trainInformation(String n)throws Exception{return client.get("TrainInformation",n);}
    public JsonObject trainSchedule(String n)throws Exception{return client.get("TrainSchedule",n);}
    public JsonObject trainFare(String n,String from,String to,String quota)throws Exception{return client.get("TrainFare",n,from,to,quota);}
    public JsonObject autocompleteStation(String q)throws Exception{return client.get("AutoCompleteStation",q);}
    public JsonObject stationNameToCode(String n)throws Exception{return client.get("StationNameToCode",n);}
    public JsonObject stationCodeToName(String c)throws Exception{return client.get("StationCodeToName",c);}
    public JsonObject stationLocation(String c)throws Exception{return client.get("StationLocationOnMap",c);}
    public JsonObject liveTrainStatus(String n,String date)throws Exception{return client.get("livetrainstatus",n,date);}
    public List<String> stationSuggestions(String q)throws Exception{
        JsonObject o=autocompleteStation(q); List<String> out=new ArrayList<>(); JsonArray a=o.getAsJsonArray("Station");
        if(a!=null) for(JsonElement e:a){JsonObject s=e.getAsJsonObject(); out.add(s.get("NameEn").getAsString()+"  ("+s.get("StationCode").getAsString()+")");} return out;
    }
}
