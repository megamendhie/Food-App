package com.swiftqube.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnOne, btnTwo;
    final int amount_1 = 5000;
    final int amount_2 = 2500;
    String email = "example@email.com";
    String fName = "First_Name";
    String lName = "Last_Name";
    String narration = "payment for food";
    String txRef;
    String country = "NG";
    String currency = "NGN";

    final String publicKey = "[INSERT YOUR PUBLIC KEY]"; //Get your public key from your account
    final String encryptionKey = "[INSERT YOUR ENCRYPTION KEY]"; //Get your encryption key from your account


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOne = findViewById(R.id.btn_one);
        btnOne.setOnClickListener(this);
        btnTwo = findViewById(R.id.btn_two);
        btnTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_one:
                makePayment(amount_1); //calls payment method with amount 1
                break;
            case R.id.btn_two:
                makePayment(amount_2); //calls payment method with amount 2
                break;
        }
    }

    public void makePayment(int amount){
        txRef = email +" "+  UUID.randomUUID().toString();

        /*
        Create instance of RavePayManager
         */
        new RavePayManager(this).setAmount(amount)
                .setCountry(country)
                .setCurrency(currency)
                .setEmail(email)
                .setfName(fName)
                .setlName(lName)
                .setNarration(narration)
                .setPublicKey(publicKey)
                .setEncryptionKey(encryptionKey)
                .setTxRef(txRef)
                .acceptAccountPayments(true)
                .acceptCardPayments(
                        true)
                .acceptMpesaPayments(false)
                .acceptGHMobileMoneyPayments(false)
                .onStagingEnv(false).
                allowSaveCardFeature(true)
                .withTheme(R.style.DefaultPayTheme)
                .initialize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
