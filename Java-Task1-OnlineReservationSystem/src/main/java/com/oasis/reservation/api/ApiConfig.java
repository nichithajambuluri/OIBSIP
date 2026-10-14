package com.oasis.reservation.api;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ApiConfig {
    private final String baseUrl, key;
    private final boolean enabled;
    private final int connectTimeoutSeconds, readTimeoutSeconds;
    private ApiConfig(String baseUrl,String key,boolean enabled,int connect,int read){this.baseUrl=baseUrl;this.key=key;this.enabled=enabled;this.connectTimeoutSeconds=connect;this.readTimeoutSeconds=read;}
    public static ApiConfig load(){
        Properties p=new Properties();
        Path file=Path.of("config","api.properties");
        if(Files.exists(file)) try(InputStream in=Files.newInputStream(file)){p.load(in);}catch(IOException ignored){}
        String key=p.getProperty("api.key","").trim();
        boolean enabled=Boolean.parseBoolean(p.getProperty("api.enabled","false")) && !key.isBlank() && !key.equals("YOUR_API_KEY_HERE");
        return new ApiConfig(p.getProperty("api.baseUrl","http://indianrailapi.com/api/v2").replaceAll("/$",""),key,enabled,
                Integer.parseInt(p.getProperty("api.connectTimeoutSeconds","10")),Integer.parseInt(p.getProperty("api.readTimeoutSeconds","15")));
    }
    public String url(String path,String... parts){
        StringBuilder b=new StringBuilder(baseUrl).append('/').append(path);
        for(String part:parts)b.append('/').append(java.net.URLEncoder.encode(part,java.nio.charset.StandardCharsets.UTF_8));
        return b.append('/').toString();
    }
    public String key(){return key;} public boolean enabled(){return enabled;} public int connectTimeoutSeconds(){return connectTimeoutSeconds;} public int readTimeoutSeconds(){return readTimeoutSeconds;}
}
