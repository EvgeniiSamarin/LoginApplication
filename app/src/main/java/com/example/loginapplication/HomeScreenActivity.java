package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener{

    Button bLogOut;
    TextView etName, etTelephone, etLogin;
    DataBaseUser dataBaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        etName = (TextView) findViewById(R.id.textview_fio);
        etTelephone = (TextView) findViewById(R.id.textview_telephone);
        etLogin = (TextView) findViewById(R.id.textview_login);
        bLogOut = (Button) findViewById(R.id.button_log_out);
        bLogOut.setOnClickListener(this);

        Intent intent = getIntent();
        String fio = intent.getStringExtra("fio");
        String email = intent.getStringExtra("email");
        String telephone = intent.getStringExtra("telephone");


        etLogin.setText(email);
        etName.setText(fio);
        etTelephone.setText(telephone);


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_log_out:

                startActivity(new Intent(this, MainActivity.class));

                break;
        }
    }
}
