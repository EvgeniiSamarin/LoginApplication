package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainActivity extends AppCompatActivity{

    Button bSignIn;
    EditText etEmail, etPassword;
    TextView signUpLink;
    DataBaseUser db;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";


    private FileInputStream fileInputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.edittext_login);
        etPassword = (EditText) findViewById(R.id.edittext_password);
        bSignIn = (Button) findViewById(R.id.button_signin);
        signUpLink = (TextView) findViewById(R.id.textview_sign_up);

        db = new DataBaseUser(this);
        loadInformation();

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user;
                String user_email = etEmail.getText().toString().trim();
                String user_password = etPassword.getText().toString().trim();
                user = new User(user_email, user_password);
                Boolean res = db.checkUser(user);
                if (res.equals(true)) {
                    User user1 =  db.getUser(user_email);
                    Toast.makeText(MainActivity.this, "You are successfuly logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                    intent.putExtra("fio", user1.getFio());
                    intent.putExtra("email", user1.getEmail());
                    intent.putExtra("telephone", user1.getTelephone());
                    saveInformation();
                    startActivity(intent);
                } else {

                    Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_SHORT).show();

                }
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveInformation();
    }
    void saveInformation() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("email", etEmail.getText().toString());
        ed.putString("password", etPassword.getText().toString());
        ed.commit();
    }
    void loadInformation() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedEmail= sPref.getString("email", "");
        String savedPassword = sPref.getString("password", "");
        etEmail.setText(savedEmail);
        etPassword.setText(savedPassword);



    }



}
