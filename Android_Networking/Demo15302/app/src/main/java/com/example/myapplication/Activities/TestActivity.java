package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.BackgroundTasks.TestBackgroundTask;
import com.example.myapplication.MyVolley.VolleySingleton;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestActivity extends AppCompatActivity {

    private TextView textView8;
    private Button button10;
    private EditText number1, number2;

    private String url = "http://10.0.2.2:8081/api/test.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView8 = (TextView) findViewById(R.id.textView8);
        button10 = (Button) findViewById(R.id.button10);
        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // getPersonFromServerUsingVolley();
                // getArrayPersonFromServerUsingVolley();
                postToServerVolley(number1.getText().toString(), number2.getText().toString());
            }
        });
    }


    // start using Volley

    private void postToServerVolley(String name1, String name2){
        VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s = "";
                            for (int i = 0; i < response.length(); i++){
                                JSONObject object = response.getJSONObject(i);
                                s += object.getString("name");
                                s+= " ";
                            }
                            textView8.setText(s);
                        }catch (Exception e){
                            Log.e("Error:>>>>>", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { }
                }){
            @Override
            public byte[] getBody() {
                try {
                    JSONObject body = new JSONObject();
                    body.put("name1", name1);
                    body.put("name2", name2);

                    final String requestBody = body.toString();
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                }catch (Exception e){}
                return null;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF8";
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void getArrayPersonFromServerUsingVolley() {
        VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s = "";
                            for (int i = 0; i < response.length(); i++){
                                JSONObject object = response.getJSONObject(i);
                                s += object.getString("name");
                                s+= " ";
                            }
                            textView8.setText(s);
                        }catch (Exception e){
                            Log.e("Error:>>>>>", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void getPersonFromServerUsingVolley() {
        VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String _id = response.getString("id");
                            String _name = response.getString("name");
                            textView8.setText(_name);
                        }catch (Exception e){
                            Log.e("Error:>>>>>", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    // end using Volley


    // start using Retrofit


    // end using Retrofit

}