package com.purple.hello.room;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;

@Component
public class RoomCode {
    private static final String packageName = "com.example.example";
    private static final String screenId = "screen1";

    public String makeUrl(long roomId, Instant time){
        String url = "https://example.com/myapp://" + screenId + "?roomId=" + roomId + "&creationTime=" + time.toString();
        String deferredDeepLink = "https://example.com/deferred-deep-link?packageName=" + packageName + "&url="
                + URLEncoder.encode(url, StandardCharsets.UTF_8);
        return deferredDeepLink;
    }

    public String getTime(String priUrl){
        URI uri = URI.create(priUrl);
        String[] queryParts = uri.getQuery().split("&");
        String expiry = Arrays.stream(queryParts)
                .filter(s -> s.startsWith("creationTime="))
                .findFirst()
                .orElse(null);
        return expiry.split("=")[1];
    }

}
