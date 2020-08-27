package com.p2ms.musicbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText name,email,contact,gender,pass,passConf;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.nameEditId);
        email=findViewById(R.id.emailEditID);
        contact=findViewById(R.id.contactEditID);
        //gender=findViewById(R.id.nameEditId);
        pass=findViewById(R.id.passwordEditID);
        passConf=findViewById(R.id.passwordConfEditID);
    }
}