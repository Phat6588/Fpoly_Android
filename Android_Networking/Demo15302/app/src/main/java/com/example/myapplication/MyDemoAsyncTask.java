package com.example.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.TextView;

public class MyDemoAsyncTask
        extends AsyncTask<Void, Integer, String> {

    private Activity context;
    public MyDemoAsyncTask(Activity context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        for (int i = 0; i < 10; i++) {
            publishProgress(i);
            SystemClock.sleep(2000);
        }
        return "End";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        TextView textView = (TextView) context.findViewById(R.id.textView6);
        textView.setText(values[0] + "");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        TextView textView = (TextView) context.findViewById(R.id.textView6);
        textView.setText(s);
    }
}
