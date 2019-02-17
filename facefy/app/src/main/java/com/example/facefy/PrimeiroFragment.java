package com.example.facefy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facefy.model.EventsModel;
import com.google.gson.Gson;
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

public class PrimeiroFragment extends Fragment {
    private RecyclerView rv;
    private static final String URL_CUSTOMER_EVENTS_ALL = "http://10.10.0.186:8081/event/";
    private static final String URL_CUSTOMER_EVENTS_BUY = "http://10.10.0.186:8081/event/%s";
    private static final String URL_CUSTOMER_EVENTS = "http://10.10.0.186:8081/event/customer/%s";
    private String customerId;
    private MyItemRecyclerViewAdapter adpter;
    AlertDialog.Builder alertBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.primeiro_fragment, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        boolean Islogin = prefs.getBoolean("Islogin", false);
        customerId = prefs.getString("customerId", "");

        rv =(RecyclerView) view.findViewById(R.id.list);
        rv.setHasFixedSize(true);
        loadImage();

        return view;
    }

    private void loadImage() {

        try {
            Context context=this.getContext();
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

                        String content = responseBody.string();
                        System.out.println(content);
                        Type listType = new TypeToken<ArrayList<EventsModel>>() {
                        }.getType();
                        List<EventsModel> events = new Gson().fromJson(content, listType);


                        adpter = new MyItemRecyclerViewAdapter(events, new ItemFragment.OnListFragmentInteractionListener() {
                            @Override
                            public void onListFragmentInteraction(EventsModel item) {

                                alertBuilder = new AlertDialog.Builder(getActivity());
                                alertBuilder.setTitle("Confirmar a compra do ingresso");
                                alertBuilder.setMessage("Você está finalizando a compra para o evento "+item.name
                                        +"\r\nno valor de "+item.value
                                        +"\r\n\r\nConcluir a compra?");


                                alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
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
                                                getActivity().runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(getActivity(), "Thank you for purchase", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        });

                                    }
                                });
                                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = alertBuilder.create();
                                alertDialog.show();


                            }
                        });

                        getActivity().runOnUiThread(new Runnable() {

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
