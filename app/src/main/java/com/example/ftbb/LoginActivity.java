package com.example.ftbb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ftbb.models.AppDataBase;
import com.example.ftbb.models.user;
import com.example.ftbb.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    Button Register, Login;
    SharedPreferences sharedPreferences ;
    public static String FILE_NAME = "com.exemple.ftbb.sp";
    SessionManager sessionManager;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());

        Login = findViewById(R.id.cnx);
        Register = findViewById(R.id.inscri);

        username = (EditText) findViewById(R.id.nomuser);
        password = (EditText) findViewById(R.id.mdpuser);
        sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);

        if(!sharedPreferences.getString("USERNAME","").isEmpty())
        {
            Intent intent;

            switch (sharedPreferences.getString("Role",""))
            {
                case "arbitre":
                    intent = new Intent(LoginActivity.this,Arbitre_DashboardActivity.class);
                    startActivity(intent);
                    break;
                case "user":
                    intent = new Intent(LoginActivity.this,user_DashboardActivity.class);
                    startActivity(intent);
                    break;
                case "admin":
                    intent = new Intent(LoginActivity.this,Admin_DashboardActivity.class);
                    startActivity(intent);
                    break;
            }
            finish();
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InscriActivity.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
                {
                    SharedPreferences.Editor editor;
                    editor = sharedPreferences.edit();
                    editor.putString("USERNAME","admin");
                    editor.putString("Role","admin");
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, Admin_DashboardActivity.class);
                    startActivity(intent);
                    finish();

                }
                else if(AppDataBase.getAppDatabase(getApplicationContext()).userDao().login(username.getText().toString(),password.getText().toString())==null)
                {
                    Toast.makeText(LoginActivity.this, "OUPS! user ou mot de passe incorrect", Toast.LENGTH_SHORT).show();

                }
                else
                {

                    SharedPreferences.Editor editor;
                    editor = sharedPreferences.edit();
                    user u = AppDataBase.getAppDatabase(getApplicationContext()).userDao().login(username.getText().toString(),password.getText().toString());
                    editor.putString("USERNAME",u.getUsername());
                    editor.putString("Role",u.getRole());
                    editor.apply();
                    Intent intent;
                    switch (u.getRole())
                    {
                        case "arbitre":
                            intent = new Intent(LoginActivity.this,Arbitre_DashboardActivity.class);
                            startActivity(intent);
                            break;
                        case "user":
                            intent = new Intent(LoginActivity.this,user_DashboardActivity.class);
                            startActivity(intent);
                            break;
                        case "admin":
                            intent = new Intent(LoginActivity.this,Admin_DashboardActivity.class);
                            startActivity(intent);
                            break;
                    }
                    finish();
                }

            }
        });

    }
}