package com.p2ms.musicbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.p2ms.musicbuddy.keys.StaticData;
import com.p2ms.musicbuddy.local.LocalSession;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText emailID,passID;
    Button loginButtonID;
    TextView noAcc;

    private String email,password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailID=findViewById(R.id.loginEdID);
        passID=findViewById(R.id.passEdID);
        loginButtonID=findViewById(R.id.loginButtonID);
        noAcc=findViewById(R.id.noAccId);

        noAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity","Clicked on have no Account");
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        loginButtonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity","Clicked on Login");
                email=String.valueOf(emailID.getText()).trim();
                password=String.valueOf(passID.getText()).trim();
                Log.d("LoginActivity","ID "+email+"password "+password);
                firebaseAuth=FirebaseAuth.getInstance();
                if(email.isEmpty()) emailID.setError("Didn't Enter any email ID");
                else if(password.isEmpty()) passID.setError("Didn't Enter any password");
                else{
                    firebaseAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser existingUser = firebaseAuth.getCurrentUser();
                                        Log.d("LoginActivity","Email and password matched");
                                        boolean verify = existingUser.isEmailVerified();
                                        if(verify){
                                            Log.d("LoginActivity","Email is verified");
                                            Toast.makeText(LoginActivity.this,"Logged in  Successfully",Toast.LENGTH_LONG).show();
                                            LocalSession local= new LocalSession(LoginActivity.this);
                                            local.setData(StaticData.ID,existingUser.getUid());
                                            //startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                            LoginActivity.this.finish();
                                        }else{
                                            Log.d("LoginActivity","Email not verified");
                                            Toast.makeText(LoginActivity.this,"Email address isn't verified",Toast.LENGTH_LONG).show();
                                        }

                                    }else{
                                        Log.d("LoginActivity","Login Failed because Email doesn't exist");
                                        Toast.makeText(LoginActivity.this,"Email address isn't registered please Sign Up",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}