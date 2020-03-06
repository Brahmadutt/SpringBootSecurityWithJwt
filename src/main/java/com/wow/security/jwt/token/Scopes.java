package com.wow.security.jwt.token;

public enum Scopes {
    REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
