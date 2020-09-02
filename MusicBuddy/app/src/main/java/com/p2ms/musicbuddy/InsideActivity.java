package com.p2ms.musicbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.p2ms.musicbuddy.local.LocalSession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class InsideActivity extends AppCompatActivity {
    LocalSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("InsideActivity","Got inside");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_Search, R.id.navigation_now_playing, R.id.navigation_notifications,R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.logout_buttonID):
                Log.d("InsideActivity","Logout Button Clicked");
                performLogout();
                break;
            case (R.id.Help_buttonID):
                Log.d("InsideActivity","Search Button Clicked");
                Toast.makeText(InsideActivity.this,"Opening App documentation",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performLogout() {
        session = new LocalSession(InsideActivity.this);

        final AlertDialog.Builder logoutAlert= new AlertDialog.Builder(InsideActivity.this);
        logoutAlert.setMessage("Do you really want to logout");
        logoutAlert.setCancelable(false);
        logoutAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                session.cleanData();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(InsideActivity.this,"Logged Out",Toast.LENGTH_LONG).show();
                startActivity(new Intent(InsideActivity.this,LoginActivity.class));
            }
        });
        logoutAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(InsideActivity.this,"Log Out canceled",Toast.LENGTH_LONG).show();
            }
        });
        logoutAlert.show();
        return;
    }
}