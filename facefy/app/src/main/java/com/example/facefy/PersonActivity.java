package com.example.facefy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facefy.model.CustomerModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class PersonActivity extends AppCompatActivity {

    private TextView mUsername;
    private TextView mPassword;
    private FloatingActionButton btnSave;
    private TextView mConfirmPassword;
    private TextView mName;
    private ImageView mPicture;
    private static final String URL_CUSTOMER_CREATE = "http://10.10.0.186:8081/customer/create";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private CustomerModel cm;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        cm = new CustomerModel();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        mUsername = findViewById(R.id.txtUsername);
        mPassword = findViewById(R.id.txtPassword);
        mConfirmPassword = findViewById(R.id.txtConfirmPassword);
        mName = findViewById(R.id.txtNome);
        mPicture = findViewById(R.id.imgPerson);

        Bundle b = getIntent().getExtras();
        mUsername.setText(b.get("username").toString());
        mPassword.setText(b.get("senha").toString());

        btnSave = findViewById(R.id.btnPersonSalvar);
        btnSave.setOnClickListener(btnSave_click);

        mPicture.setOnClickListener(picture_click);
    }

    private View.OnClickListener picture_click=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dispatchTakePictureIntent();
        }
    };
    private View.OnClickListener btnSave_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            cm.setConfirmationPassword( mConfirmPassword.getText().toString());
            cm.setPassword( mPassword.getText().toString());
            cm.setName( mName.getText().toString());
            cm.setUsername( mUsername.getText().toString());

            String content=gson.toJson(cm);

            try {
                Log.d("PersonActivity", "onClick: "+content);
                Call call=post(URL_CUSTOMER_CREATE,content);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();

                        Toast toast=Toast.makeText(v.getContext(),"Erro ao criar o usuário",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                            Headers responseHeaders = response.headers();
                            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                            }
                            String content=responseBody.string();
                            System.out.println(content);

                            CustomerModel customer=gson.fromJson(content,CustomerModel.class);
                            Intent activityIntent = new Intent(v.getContext(), Payment.class);

                            activityIntent.putExtra("customerId",customer.getCustomerId());
                            startActivity(activityIntent);
                            finish();
                        }
                    }
                });


            } catch (Exception e) {
               e.printStackTrace();
                Toast toast=Toast.makeText(v.getContext(),"Erro ao criar o usuário falha na rede ",Toast.LENGTH_SHORT);
                toast.show();
            }


        }
    };

    private Call post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        return call;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPicture.setImageBitmap(imageBitmap);

           cm.setPhoto(imageBitmap);
        }
    }
}
