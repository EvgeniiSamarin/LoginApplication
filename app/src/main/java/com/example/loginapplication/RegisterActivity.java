package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button bRegister;
    EditText etName, etTelephone, etLogin, etPassword, etCnfPassword;
    private Matcher matcher;
    DataBaseUser db;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.edittext_fio);
        etTelephone = (EditText) findViewById(R.id.edittext_telephone);
        etLogin = (EditText) findViewById(R.id.edittext_login);
        etPassword = (EditText) findViewById(R.id.edittext_password);
        etCnfPassword = (EditText) findViewById(R.id.edittext_cnf_password);
        bRegister = (Button) findViewById(R.id.button_register);
        db = new DataBaseUser(this);
        login = (TextView) findViewById(R.id.textview_login);

        bRegister.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_register:
                User user;

                String fio = etName.getText().toString();
                String email = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                String telephone = etTelephone.getText().toString();
                String confirmPassword = etCnfPassword.getText().toString();

                if(password.equals(confirmPassword)) {
                    if(checkEmail(email) && checkPassword(password)) {
                        long val = db.addUser(new User(fio, email, password, telephone));
                        if (val > 0) {
                            Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "This user have already registered", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "wrong format email or password", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.textview_login:
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));


            break;
        }
    }
    private boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
