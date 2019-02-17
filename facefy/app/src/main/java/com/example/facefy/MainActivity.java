package com.example.facefy;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.facefy.model.CardsTokensResponse;
import com.example.facefy.model.CustomerModel;
import com.example.facefy.model.EventsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btn_add;
    private static final String URL_CUSTOMER_EVENTS_ALL = "http://10.10.0.186:8081/event/";
    private static final String URL_CUSTOMER_EVENTS_BUY = "http://10.10.0.186:8081/event/%s";
    private static final String URL_CUSTOMER_EVENTS = "http://10.10.0.186:8081/event/customer/%s";
    private String customerId;
    private Gson gson;
    private RecyclerView rv;
    private MyItemRecyclerViewAdapter adpter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean Islogin = prefs.getBoolean("Islogin", false);
        customerId = prefs.getString("customerId", "");
        if (Islogin) {
            setContentView(R.layout.activity_main);
            rv = findViewById(R.id.list);
            rv.setHasFixedSize(true);
            loadImage();


        } else {
            Intent activityIntent = new Intent(this, LoginActivity.class);
            startActivity(activityIntent);
            finish();
        }


    }

    private void loadImage() {

        try {
            Context context=this;
            Call call = get(URL_CUSTOMER_EVENTS_ALL);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful())
                            throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                        Log.d("customer", "onResponse: " + customerId);
                        String content = responseBody.string();
                        System.out.println(content);
                        Type listType = new TypeToken<ArrayList<EventsModel>>() {
                        }.getType();
                        List<EventsModel> events = new Gson().fromJson(content, listType);
                        EventsModel item = new EventsModel();
                        item.name = "Teste";
                        item.value = "10.00";
                        events.add(item);
                        Log.d("é o back?", "onResponse: " + events.size());
                        adpter = new MyItemRecyclerViewAdapter(events, new ItemFragment.OnListFragmentInteractionListener() {
                            @Override
                            public void onListFragmentInteraction(EventsModel item) {
                                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(String.format(URL_CUSTOMER_EVENTS_BUY,item.eventId))
                                        .post(body )
                                        .addHeader("customer-id", customerId)
                                        .build();

                                Call call = client.newCall(request);
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        Log.d("eventoId", "onResponse: "+item.eventId);
                                      try {
                                          Toast toast = Toast.makeText(context, "Evento comprado", Toast.LENGTH_SHORT);
                                          toast.show();
                                      }catch (Exception ex) {

                                      }
                                    }
                                });
                            }
                        });

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                rv.setAdapter(adpter);

                            }
                        });


                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Call get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        return call;
    }
}
