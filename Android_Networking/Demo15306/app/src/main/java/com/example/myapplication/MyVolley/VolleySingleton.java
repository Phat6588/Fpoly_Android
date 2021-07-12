package com.example.myapplication.MyVolley;

// design pattern

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context _context){
        context = _context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context _context){
        if (instance == null) instance = new VolleySingleton(_context);
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    // generic method java
    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
