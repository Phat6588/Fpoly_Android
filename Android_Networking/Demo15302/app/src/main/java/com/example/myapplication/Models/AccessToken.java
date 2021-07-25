package com.example.myapplication.Models;


public class AccessToken {
    private String access_token;
    private Boolean is_auth;

    public AccessToken() {
    }

    public Boolean getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(Boolean is_auth) {
        this.is_auth = is_auth;
    }

    public AccessToken(String access_token, Boolean is_auth) {
        this.access_token = access_token;
        this.is_auth = is_auth;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
