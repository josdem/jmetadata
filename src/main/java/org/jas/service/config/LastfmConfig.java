package org.jas.service.config;

public class LastFMConfig {
    public static String getLastFMKey() {
        String key = System.getenv("LASTFM_API_KEY"); 
        if (key == null || key.isEmpty()) {
            throw new IllegalStateException("Environment variable 'LASTFM_API_KEY' is not set");
        }
        return key;
    }

    public static String getLastFMSecret() {
        String secret = System.getenv("LASTFM_API_SECRET");  
        if (secret == null || secret.isEmpty()) {
            throw new IllegalStateException("Environment variable 'LASTFM_API_SECRET' is not set");
        }
        return secret;
    }
}
