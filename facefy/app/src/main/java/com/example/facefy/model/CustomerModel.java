package com.example.facefy.model;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class CustomerModel {


    String customerId;

    String username;

    String password;

    String confirmationPassword;

    String firstName;

    String lastName;

    String base64Photo;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBase64Photo() {
        return base64Photo;
    }

    public void setBase64Photo(String base64Photo) {
        this.base64Photo = base64Photo;
    }
    public void setPhoto(Bitmap image) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 75, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        this.base64Photo = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public void setName(String name)
    {
        String[] split=name.split(" ");
        this.firstName=split[0];
        split[0]="";
        //for(String s:split)
        this.lastName= TextUtils.join(" ",split);
    }
}
