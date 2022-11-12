package com.example.ftbb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ftbb.models.AppDataBase;
import com.example.ftbb.models.user;

public class InscriActivity extends AppCompatActivity {

    Button Register,Retour;
    EditText username, email, password,confirmepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscri);

        username = (EditText) findViewById(R.id.inscrinomuser);
        email = (EditText) findViewById(R.id.inscriemail);
        password = (EditText) findViewById(R.id.inscrimdp);
        confirmepassword = (EditText) findViewById(R.id.inscriconfirmemdp);

        Register = findViewById(R.id.inscription);
        Retour = findViewById(R.id.retour);

        Retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InscriActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmepassword.getText().toString().equals( password.getText().toString())){
                    if(AppDataBase.getAppDatabase(getApplicationContext()).userDao().findByEmail(email.getText().toString())==null)
                    {
                        user usr = new user(username.getText().toString(),password.getText().toString(),email.getText().toString(),"user");
                        AppDataBase.getAppDatabase(getApplicationContext()).userDao().insertOne(usr);

                        Intent intent = new Intent(InscriActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Success,Login please !",Toast.LENGTH_SHORT).show();

                        finish();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"EXISTE",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Mot de passe non conforme",Toast.LENGTH_SHORT).show();

                }
                /*
                Intent intent = new Intent(Register.this, HomePage.class);
                user loggedUser = register(intent);

                 */
            }
        });
    }
}