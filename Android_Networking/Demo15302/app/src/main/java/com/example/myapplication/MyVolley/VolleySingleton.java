package com.example.myapplication.MyVolley;


// design pattern
// singleton


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private VolleySingleton(Context _ctx){
        ctx = _ctx;
    }

    public static synchronized VolleySingleton getInstance(Context _ctx){
        if (instance == null) instance = new VolleySingleton(_ctx);
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    // generic method
    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
