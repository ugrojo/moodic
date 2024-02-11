package com.moodic.utils;

import java.util.Date;

public class SimpleCache {

    private String currentToken = null;
    private Date currentTokenDate = null;
    private final long cacheDurationSeconds;

    public SimpleCache() {
        this.cacheDurationSeconds = 3600;
    }

    public SimpleCache(long cacheDurationSeconds) {
        this.cacheDurationSeconds = cacheDurationSeconds;
    }
    public boolean isCacheStale() {
        return currentToken == null || currentTokenDate == null
                || (new Date().getTime() - currentTokenDate.getTime()) / 1000 >= cacheDurationSeconds;
    }

    public void updateCache(String newToken) {
        currentToken = newToken;
        currentTokenDate = new Date();
    }

    public String getToken() {
        return this.currentToken;
    }
}
