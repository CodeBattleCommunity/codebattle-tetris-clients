package com.codenjoy.dojo.tetris.client;

public class UrlWrapper {
    public String removeTrailingParameters(String url) {
        return url.substring(0, url.indexOf("&"));
    }
}
