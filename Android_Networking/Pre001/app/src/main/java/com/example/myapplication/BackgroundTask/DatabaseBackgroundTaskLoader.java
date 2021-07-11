package com.example.myapplication.BackgroundTask;

import android.content.Context;

import com.example.myapplication.DAO.BackgroundTaskDAO;

import androidx.loader.content.AsyncTaskLoader;

public class DatabaseBackgroundTaskLoader extends AsyncTaskLoader<String> {
    private String[] params;

    public DatabaseBackgroundTaskLoader(Context context, String... _params) {
        super(context);
        this.params = _params;
    }

    @Override
    public String loadInBackground() {
        BackgroundTaskDAO dao = new BackgroundTaskDAO();
        String method = params[0];
        String data = "";
        switch (method){
            case "GET":
                data = dao.get(params[1]);
                break;
            case "POST":
                data = String.valueOf(dao.insert(params[1], params[2]));
                break;
            default:
                break;
        }
        return data;
    }
}
