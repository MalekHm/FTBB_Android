package com.example.ftbb;

import static com.example.ftbb.LoginActivity.FILE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_DashboardActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_admin_dashboard);
        sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigon);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UsersFragment()).commit();
        }

    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId())
            {
                case R.id.nav_home:
                    selectedFragment = new UsersFragment();
                    break;
                case R.id.nav_team:
                    selectedFragment = new TeamFragment();
                    break;
                case R.id.nav_live:
                    selectedFragment = new LiveMatchFragment();
                    break;
                case R.id.nav_match:
                    selectedFragment = new MatchFragment();
                    break;
                case R.id.nav_stats:
                    selectedFragment = new StatFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
        return  true;
        }
    };

    public void Logout(MenuItem item) {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(Admin_DashboardActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();


    }

}