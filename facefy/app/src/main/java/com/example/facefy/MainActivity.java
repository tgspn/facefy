package com.example.facefy;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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

    private String customerId;
    private Gson gson;



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


            AbasAdapter adapter = new AbasAdapter( getSupportFragmentManager() );
            adapter.adicionar( new PrimeiroFragment() , "Primeira Aba");
            adapter.adicionar( new SegundoFragment(), "Segunda Aba");
            //adapter.adicionar( new TerceiroFragment(), "Terceira Aba");

            ViewPager viewPager = (ViewPager) findViewById(R.id.abas_view_pager);
            viewPager.setAdapter(adapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.abas);
            tabLayout.setupWithViewPager(viewPager);




        } else {
            Intent activityIntent = new Intent(this, LoginActivity.class);
            startActivity(activityIntent);
            finish();
        }


    }


}
