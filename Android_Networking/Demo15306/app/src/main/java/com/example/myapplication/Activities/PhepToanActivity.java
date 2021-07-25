package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;

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
import com.example.myapplication.Model.AccessToken;
import com.example.myapplication.Model.AccessTokenManager;
import com.example.myapplication.Model.Person;
import com.example.myapplication.Model.Student;
import com.example.myapplication.Model.ThaThinh;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.MyVolley.VolleySingleton;
import com.example.myapplication.R;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.List;
import java.util.Random;

public class PhepToanActivity extends AppCompatActivity {

    private TextView textView3;
    private Button button3, button4;
    private EditText editText1, editText2;

    private String url = "http://10.0.2.2:8081/api/test.php";



    private IRetrofitService service;
    private AccessTokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phep_toan);

//        service = RetrofitBuilder.createService(IRetrofitService.class);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        textView3 = (TextView) findViewById(R.id.textView3);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        editText1 = (EditText) findViewById(R.id.editTextNumber1);
        editText2 = (EditText) findViewById(R.id.editTextNumber2);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editText1.getText().toString();
                String password = editText2.getText().toString();

//                service.login(new Person("", "", username, password))
//                        .enqueue(loginCallback);



                // Person p = new Person(id, name);
                // postToServerByVolley(p);
                // getFromServerByVolley();
                // getArrayFromServerByVolley();


                // service.get().enqueue(getOneCallback);
                // service.getArray().enqueue(getArrayCallback);

                // service.postPerson(new Student(ten, Double.parseDouble(luong), 0.0));
                // Random r = new Random();
                // int low = 1;
                // int high = 7;
                // int number = r.nextInt(high-low) + low;
                // service.thaThinh(new ThaThinh(number, null)).enqueue(postThaThinhCallback);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                service.getProfile().enqueue(getProfileCallback);
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
//                            Person p = new Person(id, name);
//                            textView3.setText(p.getId() + " " + p.getName());
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

//                    JSONObject body = new JSONObject();
//                    body.put("id", person.getId());
//                    body.put("name", person.getName());

//                    final String requestBody = body.toString();
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
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
//                            Person p = new Person(id, name);
//                            textView3.setText(p.getId() + " " + p.getName());
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
//                            Person p = new Person(id, name);
//                            textView3.setText(p.getId() + " " + p.getName());
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

    Callback<Person> getProfileCallback = new Callback<Person>() {
        @Override
        public void onResponse(Call<Person> call, retrofit2.Response<Person> response) {
            if (response.isSuccessful()){
//                Person person = response.body();
//                textView3.setText(person.getName());
            }
        }

        @Override
        public void onFailure(Call<Person> call, Throwable t) {
        }
    };

    Callback<AccessToken> loginCallback = new Callback<AccessToken>() {
        @Override
        public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
            if (response.isSuccessful()){
//                AccessToken token = response.body();
//                tokenManager.saveToken(token);
            }
        }

        @Override
        public void onFailure(Call<AccessToken> call, Throwable t) {
        }
    };


    Callback<Person> getOneCallback = new Callback<Person>() {
        @Override
        public void onResponse(Call<Person> call, retrofit2.Response<Person> response) {
            if (response.isSuccessful()){
//                Person person = response.body();
//                textView3.setText(person.getName());
            }
        }

        @Override
        public void onFailure(Call<Person> call, Throwable t) {

        }
    };

    Callback<List<Person>> getArrayCallback = new Callback<List<Person>>() {
        @Override
        public void onResponse(Call<List<Person>> call, retrofit2.Response<List<Person>> response) {
            if (response.isSuccessful()){
//                List<Person> list = response.body();
//                textView3.setText(list.get(0).getName() + "  " + list.get(1).getName());
            }
        }

        @Override
        public void onFailure(Call<List<Person>> call, Throwable t) {

        }
    };

    Callback<Student> postPersonCallback = new Callback<Student>() {
        @Override
        public void onResponse(Call<Student> call, retrofit2.Response<Student> response) {
            if (response.isSuccessful()){
                Student st = response.body();
                textView3.setText(st.getTen() + "  " + st.getLuong() + " " + st.getThuNhap());
            }
        }

        @Override
        public void onFailure(Call<Student> call, Throwable t) {

        }
    };

    Callback<ThaThinh> postThaThinhCallback = new Callback<ThaThinh>() {
        @Override
        public void onResponse(Call<ThaThinh> call, retrofit2.Response<ThaThinh> response) {
            if (response.isSuccessful()){
                ThaThinh st = response.body();
                textView3.setText(st.getSentence());
            }
        }

        @Override
        public void onFailure(Call<ThaThinh> call, Throwable t) {

        }
    };


    // end using Retrofit





}