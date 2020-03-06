package com.wow.security.jwt.extracter;

public interface TokenExtractor {
    public String extract(String payload);
}
