package com.example.ftbb;

import static com.example.ftbb.LoginActivity.FILE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Arbitre_DashboardActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_navigation, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arbitre_dashboard);
        sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);

    }

    public void Logout(MenuItem item) {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(Arbitre_DashboardActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();


    }

}