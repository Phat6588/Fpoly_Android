package com.example.myapplication.DAO;

public interface InterfaceBackgroundTask {
    public String get(String url);
    public String insert(String url, String data);
    public boolean update();
    public boolean delete();
}
