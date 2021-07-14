package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.BackgroundTask.DatabaseBackgroundTaskLoader;
import com.example.myapplication.DAO.InterfaceRetrofitAPI;
import com.example.myapplication.Database.APIManager;
import com.example.myapplication.Model.AccessToken;
import com.example.myapplication.Model.RetrofitBuilder;
import com.example.myapplication.Model.Student;
import com.example.myapplication.Model.TokenManager;
import com.example.myapplication.R;
import com.example.myapplication.Singleton.MySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

public class PhepToanActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>,
        Loader.OnLoadCompleteListener<String> {

    private TextView textView3;
    private Button button3;
    private EditText number1, number2, number3;

    private String url = "http://10.0.2.2:8081/api/test.php";

    private final int GET_API = 1;
    private final int POST_API = 2;

    private LoaderManager loaderManager;


    private InterfaceRetrofitAPI interfaceRetrofitAPI;

    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phep_toan);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));



        textView3 = (TextView) findViewById(R.id.textView3);
        button3 = (Button) findViewById(R.id.button3);
        number1 = (EditText) findViewById(R.id.editTextNumber1);
        number2 = (EditText) findViewById(R.id.editTextNumber2);
        number3 = (EditText) findViewById(R.id.editTextNumber3);

        this.loaderManager = LoaderManager.getInstance(this);

        interfaceRetrofitAPI = RetrofitBuilder.createService(InterfaceRetrofitAPI.class);
//        createRetrofitAPI();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dung asynctask
//                int a = Integer.parseInt(number1.getText().toString());
//                int b = Integer.parseInt(number2.getText().toString());
//                int c = Integer.parseInt(number3.getText().toString());
//                url += "a=" + a + "&b=" + b + "&c=" + c;
//                DatabaseBackgroundTask backgroundTask = new DatabaseBackgroundTask(PhepToanActivity.this);
//                backgroundTask.execute("GET",url);
//                Student student = new Student("123", "Nguyễn Hoàng");
//                backgroundTask.execute("POST",url, APIManager.itemToJSON(student).toString());
//                try {
//                    String data = backgroundTask.get();
//                    textView3.setText(data);
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


                // dung async task loader
                // clickButtonLoad();

                // dung volley
                // addDataToDatabase("hehe", "kaka");
                // getVolley();


                // dung retrofit
                // RetrofitAPI api = new RetrofitAPI();
                // api.start();

                // retrofit dung enqueue
                // interfaceRetrofitAPI.get().enqueue(getStudentCB);
                // interfaceRetrofitAPI.post(new Student("1", "nguyễn nam")).enqueue(postCB);
                interfaceRetrofitAPI.getOne().enqueue(getOneStudentCB);
                interfaceRetrofitAPI.login(new Student(null, null, "123", "123")).enqueue(loginCB);
            }
        });
    }

    private void clickButtonLoad() {
        LoaderManager.LoaderCallbacks<String> loaderCallbacks = this;
        // Arguments:
        Bundle args = new Bundle();
//        args.putString(KEY_PARAM1, "Some value1");
//        args.putString(KEY_PARAM2, "Some value2");
        Loader<String> loader = this.loaderManager.initLoader(POST_API, args, loaderCallbacks);
        loader.forceLoad(); // Start Loading..
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
//        switch (id) {
//            case GET_API:
//                return new DatabaseBackgroundTaskLoader(PhepToanActivity.this, "param1", "param2");
//            case POST_API:
//                Student student = new Student("123", "Nguyễn Hoàng");
//                String param2 = APIManager.itemToJSON(student).toString();
//                return new DatabaseBackgroundTaskLoader(PhepToanActivity.this, "POST", url, param2);
//            default:
//                break;
//        }
        throw new RuntimeException("TODO..");
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        int id = loader.getId();
        this.loaderManager.destroyLoader(id);
        switch (id) {
            case GET_API:
                break;
            case POST_API:
                // parse data to object here
                List<Student> list = APIManager.getTodoItemsList(data);
                Log.e("Result", "Put Response Code :: >>>>" + list.toString());
                this.textView3.setText(list.get(0).getName());
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        this.textView3.setText("");
    }

    @Override
    public void onLoadComplete(Loader<String> loader, String data) {

    }


    // volley

    private void addDataToDatabase(String id, String name) {

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PhepToanActivity.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("id", id);
                    jsonBody.put("name", name);
                    final String mRequestBody = jsonBody.toString();
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (Exception uee) {
                    return null;
                }
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getVolley() {
        String url = "http://10.0.2.2:8081/api/single_read.php?id=1";
//        RequestQueue queue = Volley.newRequestQueue(PhepToanActivity.this);
        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
// Formulate the request and handle the response.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with the response
                        Log.e("Result", "Put Response Code :: >>>>" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

// Add the request to the RequestQueue.
//        queue.add(stringRequest);
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }












    // retrofit

    private void createRetrofitAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceRetrofitAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        interfaceRetrofitAPI = retrofit.create(InterfaceRetrofitAPI.class);
    }


    Callback<Student> getOneStudentCB = new Callback<Student>() {
        @Override
        public void onResponse(Call<Student> call, retrofit2.Response<Student> response) {
            if (response.isSuccessful()) {
                Student list = response.body();
                Log.e("Result", "Paaaaaaaaaaaa :: >>>>" + list.getName());
            } else {
                Log.e("Result", "Put Response Code :: >>>>" + response.message());
            }
        }
        @Override
        public void onFailure(Call<Student> call, Throwable t) {t.printStackTrace(); }
    };



    Callback<List<Student>> getStudentCB = new Callback<List<Student>>() {
        @Override
        public void onResponse(Call<List<Student>> call, retrofit2.Response<List<Student>> response) {
            if (response.isSuccessful()) {
                List<Student> list = response.body();
                list.forEach(s -> Log.e(">>>>>>>>>>>",s.getName()));
            } else {
                Log.e("Result", "Put Response Code :: >>>>" + response.message());
            }
        }
        @Override
        public void onFailure(Call<List<Student>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    Callback<AccessToken> loginCB = new Callback<AccessToken>() {
        @Override
        public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
            if (response.isSuccessful()) {
                AccessToken token = response.body();
                tokenManager.saveToken(response.body());
            } else {
                Log.e("Result", "Put Response Code :: >>>>" + response.message());
            }
        }
        @Override
        public void onFailure(Call<AccessToken> call, Throwable t) {}
    };








}