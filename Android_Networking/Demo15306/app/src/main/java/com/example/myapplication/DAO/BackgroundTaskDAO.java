package com.example.myapplication.DAO;

import com.example.myapplication.Database.APIManager;

public class BackgroundTaskDAO implements InterfaceBackgroundTask{


    @Override
    public String get(String url) {
        APIManager apiManager = new APIManager();
        String data = apiManager.get(url);
        return data;
    }

    @Override
    public boolean insert() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }


}
