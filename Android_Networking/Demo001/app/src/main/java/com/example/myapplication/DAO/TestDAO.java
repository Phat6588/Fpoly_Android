package com.example.myapplication.DAO;

import com.example.myapplication.API.APIManager;

public class TestDAO implements ITest{
    @Override
    public String get(String url) {
        APIManager apiManager = new APIManager();
        return apiManager.get(url);
    }
}
