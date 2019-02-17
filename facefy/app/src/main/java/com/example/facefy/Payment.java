package com.example.facefy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.facefy.model.CardsTokens;
import com.example.facefy.model.CardsTokensResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

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

public class Payment extends AppCompatActivity {

    CardForm cardForm;
    Button buy;
    private Preview mPreview;
    private FloatingActionButton btnPaymentSend;
    private Gson gson;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String URL_ZOOP_CARDS_TOKEN = "https://api.zoop.ws/v1/marketplaces/";
    private static final String URL_CUSTOMER_CARD = "http://10.10.0.186:8081/customer/%s/card/%s";
    private static final String API_PUBLISHED_KEY = "Basic enBrX3Rlc3Rfb2dtaTNUSm5WMzNVRGxqZE40bjhhUml0Og==";
    private static final String MARKETPLACE_ID = "3249465a7753536b62545a6a684b0000";
    private CardsTokens cardsToken;
    private String customerId;
    AlertDialog.Builder alertBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);

        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        Bundle b = getIntent().getExtras();
        customerId = (String) b.get("customerId");
        cardsToken = new CardsTokens();

        btnPaymentSend = findViewById(R.id.btnPaymentSend);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(false)
                .mobileNumberRequired(false)
                .setup(this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(view.getContext());
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());


                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            cardsToken.setCard_number(cardForm.getCardNumber());
                            cardsToken.setExpiration_month(cardForm.getExpirationMonth());
                            cardsToken.setExpiration_year(cardForm.getExpirationYear());
                            cardsToken.setHolder_name(((TextView) findViewById(R.id.txtPersonName)).getText().toString());
                            cardsToken.setSecurity_code(cardForm.getCvv());

                            submit(view);
                            Toast.makeText(view.getContext(), "Thank you for purchase", Toast.LENGTH_LONG).show();
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

                } else {
                    Toast.makeText(view.getContext(), "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void submit(View v) {
        try {
            Call call = post(URL_ZOOP_CARDS_TOKEN + MARKETPLACE_ID + "/cards/tokens", gson.toJson(cardsToken));
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
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
                        CardsTokensResponse token = gson.fromJson(content, CardsTokensResponse.class);

                        Call callServer = post(String.format(URL_CUSTOMER_CARD, customerId, token.getId()), "");
                        callServer.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                                prefs.edit().putBoolean("Islogin", true).commit();
                                prefs.edit().putString("customerId", customerId).commit();

                                Intent activityIntent = new Intent(v.getContext(), MainActivity.class);
                                startActivity(activityIntent);
                                finish();
                            }

                        });

                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Call post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", API_PUBLISHED_KEY)
                .post(body)
                .build();

        Call call = client.newCall(request);
        return call;
    }


}
