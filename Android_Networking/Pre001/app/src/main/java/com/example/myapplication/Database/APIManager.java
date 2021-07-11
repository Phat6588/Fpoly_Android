package com.example.myapplication.Database;

import android.util.Log;

import com.example.myapplication.Model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class APIManager {

    public String get(String _url) {
        String data = "";
        try {
            URL url = new URL(_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = "";
            while (s != null) {
                s = bufferedReader.readLine();
                if (s != null && data != null) data = data.concat(s);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String post(String path, String data) {
        try {
            URL obj = new URL(path);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();
            int responseCode = con.getResponseCode();
            Log.e("Result", ">>>>>>>>Post Response Code :: " + responseCode);


            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {

                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Log.e("Result", ">>>>>>>>Post Response Code :: " + response.toString());
            }


            con.disconnect();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void put(String path, String data) {
        try {
            URL obj = new URL(path);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();
            int responseCode = con.getResponseCode();
            Log.e("Result", "Put Response Code :: " + responseCode);
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String path, String data) {
        try {
            URL obj = new URL(path);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();
            int responseCode = con.getResponseCode();
            Log.e("Result", "Delete Response Code :: " + responseCode);
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // chuyen thanh json object roi post len server
    public static JSONObject itemToJSON(Object object) {
        JSONObject json = new JSONObject();
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for(int i = 0; i < fields.length; i++) {
                Field field = object.getClass().getDeclaredField(fields[i].getName());
                field.setAccessible(true);
                json.put(fields[i].getName(), field.get(object));
            }
        } catch(Exception e) {
            Log.e("Error", "Error :: " + e.getMessage());
        }
        return json;
    }

    // chuyen data tu server thanh list object
    public static List<Student> getTodoItemsList(String data) {
        List<Student> students = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                students.add(new Student(json.getString("id"), json.getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static List<Object>  getList(Object o, String data) {
        List<Object> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            Field[] fields = o.getClass().getDeclaredFields();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                o = new Object();
                for (Field f : fields) {
                    String name = f.getName();
                    f.setAccessible(true);
                    f.set(o, json.getString(name));
                }
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
