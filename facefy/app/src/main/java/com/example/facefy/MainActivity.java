package com.example.facefy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btn_add;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean Islogin = prefs.getBoolean("Islogin", false);

        if(Islogin)
        {
            setContentView(R.layout.activity_main);
            btn_add = findViewById(R.id.fbtn_add);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Payment.class);
                    startActivity(intent);

                }
            });

        }else{
            Intent activityIntent = new Intent(this, LoginActivity.class);
            startActivity(activityIntent);
            finish();
        }


    }
}
