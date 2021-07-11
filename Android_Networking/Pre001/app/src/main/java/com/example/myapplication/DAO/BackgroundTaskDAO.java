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
    public String insert(String url, String data) {
        APIManager apiManager = new APIManager();
        String code = apiManager.post(url, data);
        return code;
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
