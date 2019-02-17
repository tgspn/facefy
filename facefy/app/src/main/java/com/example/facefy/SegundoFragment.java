package com.example.facefy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.facefy.model.EventsModel;
import com.example.facefy.model.TransactionModel;
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

public class SegundoFragment extends Fragment {

    private RecyclerView rv;
    private static final String URL_CUSTOMER_TRANSACTION = "http://10.10.0.186:8081/transaction/";
    private String customerId;
    private TransactionRecyclerViewAdapter adpter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.segundo_fragment, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        customerId = prefs.getString("customerId", "");

        rv =(RecyclerView) view.findViewById(R.id.list_transaction);
        rv.setHasFixedSize(true);
        loadTransaction();
        closeActivity.start();
        return view;
    }

    private void loadTransaction() {

        try {

            Call call = get(URL_CUSTOMER_TRANSACTION);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("ERRO", "onFailure: "+e);
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
                        Log.d("transaction", "onResponse: " + customerId);
                        String content = responseBody.string();
                        System.out.println(content);
                        Type listType = new TypeToken<ArrayList<TransactionModel>>() {
                        }.getType();
                        List<TransactionModel> events = new Gson().fromJson(content, listType);

                        adpter = new TransactionRecyclerViewAdapter(events, new ItemFragment.OnListFragmentInteractionListener() {
                            @Override
                            public void onListFragmentInteraction(EventsModel item) {

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
    Thread closeActivity = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (!isDetached()) {
                    Thread.sleep(3000);
                    loadTransaction();
                }
                // Do some stuff
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        }
    });


    private Call get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Log.d("url", "get: "+url);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("customer-id",customerId)
                .build();

        Call call = client.newCall(request);
        return call;
    }
}
