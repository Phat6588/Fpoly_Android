package com.example.myapplication.BackgroundTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.myapplication.DAO.BackgroundTaskDAO;
import com.example.myapplication.R;

public class DatabaseBackgroundTask extends AsyncTask<String, Integer, String> {

    private Activity contextParent;

    public DatabaseBackgroundTask(Activity contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected String doInBackground(String... strings) {
        BackgroundTaskDAO dao = new BackgroundTaskDAO();
        return dao.get(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // hien thi du lieu len TextView
        TextView textView3 = (TextView) contextParent.findViewById(R.id.textView3);
        textView3.setText(s);
    }
}
