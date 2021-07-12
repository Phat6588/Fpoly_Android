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
import com.example.myapplication.BackgroundTask.DatabaseBackgroundTask;
import com.example.myapplication.Model.Person;
import com.example.myapplication.MyVolley.VolleySingleton;
import com.example.myapplication.R;

import org.json.JSONObject;
import org.json.JSONArray;

public class PhepToanActivity extends AppCompatActivity {

    private TextView textView3;
    private Button button3;
    private EditText editText1, editText2;

    private String url = "http://10.0.2.2:8081/api/test.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phep_toan);

        textView3 = (TextView) findViewById(R.id.textView3);
        button3 = (Button) findViewById(R.id.button3);
        editText1 = (EditText) findViewById(R.id.editTextNumber1);
        editText2 = (EditText) findViewById(R.id.editTextNumber2);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editText1.getText().toString();
                String name = editText2.getText().toString();
                Person p = new Person(id, name);
                // postToServerByVolley(p);
                // getFromServerByVolley();
                getArrayFromServerByVolley();
            }
        });
    }


    // start using Volley
    private void postToServerByVolley(Person person){
        VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id");
                            String name = response.getString("name");
                            Person p = new Person(id, name);
                            textView3.setText(p.getId() + " " + p.getName());
                        }catch (Exception e){
                            Log.e(">>>Error: ", "Loi o day ne: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }

            @Override
            public byte[] getBody() {
                try {

                    JSONObject body = new JSONObject();
                    body.put("id", person.getId());
                    body.put("name", person.getName());

                    final String requestBody = body.toString();
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                }catch (Exception e){
                    Log.e(">>>Error: ", "Loi o day ne: " + e.getMessage());
                }
                return null;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }


    private void getFromServerByVolley(){
        VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id");
                            String name = response.getString("name");
                            Person p = new Person(id, name);
                            textView3.setText(p.getId() + " " + p.getName());
                        }catch (Exception e){
                            Log.e(">>>Error: ", "Loi o day ne: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }


    private void getArrayFromServerByVolley(){
        VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = response.getJSONObject(0);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            Person p = new Person(id, name);
                            textView3.setText(p.getId() + " " + p.getName());
                        }catch (Exception e){
                            Log.e(">>>Error: ", "Loi o day ne: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
    // end using Volley



    // start using Retrofit



    // end using Retrofit

}