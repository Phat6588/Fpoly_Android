package com.example.myapplication.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {

    private Activity contextParent;
    private String data;

    public MyAsyncTask(Activity contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected String doInBackground(String... strings) {
        data ="";
//        for (int i = 0; i <= 10; i++) {
//            publishProgress(i);
//            // giá trị truyền vào publishProgress là đầu vào của onProgressUpdate
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return download("https://cdn.tuoitre.vn/thumb_w/980/2021/6/27/logo-img2658-1624776452935827368745.jpg");
        GETTodoItems(strings[0]);
        return data;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        TextView textView = (TextView) contextParent.findViewById(R.id.textView2);
        textView.setText(values[0] + "");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        TextView textView = (TextView) contextParent.findViewById(R.id.textView2);
//        ImageView imageView = (ImageView) contextParent.findViewById(R.id.imageView);
//        textView.setText(s);
//        imageView.setImageBitmap(s);
        Log.i("MyTag", s);
        List<test1> list = getTodoItemsList(s);
        Log.i("MyTag", "??????"+list.toString());
    }

    private Bitmap download(String s){
        try {
            return BitmapFactory.decodeStream(new URL(s).openConnection().getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void GETTodoItems(String u) {
        try {
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = "";
            while (s != null) {
                s = bufferedReader.readLine();
                if (s != null && data != null) data = data.concat(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<test1> getTodoItemsList(String data) {
        ArrayList<test1> todoItems = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                todoItems.add(new test1 (json.getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return todoItems;
    }

    class test1{
        private String name;

        public test1(String test) {
            this.name = test;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
