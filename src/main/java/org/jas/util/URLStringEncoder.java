package org.jas.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLStringEncoder {

    public static String encode(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }
}
