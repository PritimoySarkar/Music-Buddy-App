package com.p2ms.musicbuddy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.p2ms.musicbuddy.keys.StaticData;
import com.p2ms.musicbuddy.local.LocalSession;

public class FlashActivity extends AppCompatActivity {
    private LocalSession session;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        session=new LocalSession(FlashActivity.this);

        new Handler(Looper.myLooper()).postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        String currentUser = session.getData(StaticData.ID);
                        if(currentUser.isEmpty()){
                            Log.d("FlashActivity","No user logged in");
                            startActivity(new Intent(FlashActivity.this,LoginActivity.class));

                        }else{
                            Log.d("FlashActivity","User already logged in");
                            //startActivity(new Intent(FlashActivity.this,HomeActivity.class));
                        }
                        FlashActivity.this.finish();
                    }
                },3500);

    }
}