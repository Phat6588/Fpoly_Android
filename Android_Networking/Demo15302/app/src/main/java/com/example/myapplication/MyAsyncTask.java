package com.example.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.TextView;

public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

    private Activity context;
    private Integer FLAG;

    public MyAsyncTask(Activity context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Integer... integers) {
        FLAG = integers[0];
        for (int i = 0; i < 10; i++) {
            publishProgress(i);
            SystemClock.sleep(1000);
        }
        return "Done";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        switch (FLAG){
            case 1:
                TextView textView = (TextView) context.findViewById(R.id.textView);
                textView.setText(values[0] + "");
                break;
            case 2:
                TextView textView2 = (TextView) context.findViewById(R.id.textView2);
                textView2.setText(values[0] + "");
                break;
            default: break;
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        switch (FLAG){
            case 1:
                TextView textView = (TextView) context.findViewById(R.id.textView);
                textView.setText(s);
                break;
            case 2:
                TextView textView2 = (TextView) context.findViewById(R.id.textView2);
                textView2.setText(s);
                break;
            default: break;
        }
    }
}
