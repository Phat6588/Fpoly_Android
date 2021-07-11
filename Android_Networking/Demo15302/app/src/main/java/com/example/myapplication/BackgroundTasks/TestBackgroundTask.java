package com.example.myapplication.BackgroundTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.DAO.TestDAO;
import com.example.myapplication.R;

public class TestBackgroundTask extends AsyncTask<String, Void, String> {

    private Context context;

    public TestBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        TestDAO dao = new TestDAO();
        return dao.get(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // hien thi du lieu len TextView
        TextView textView8 = (TextView)((Activity) context)
                            .findViewById(R.id.textView8);
        textView8.setText(s);
    }
}
