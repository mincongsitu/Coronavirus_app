package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    DatabaseHelper databaseHelper;

    private EditText password, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        databaseHelper = new DatabaseHelper(this);
        password = findViewById(R.id.regPassword);
        email = findViewById(R.id.email);

    }

    public void register(View view){
        String eMail = email.getText().toString();
        String passWord = password.getText().toString();

        if(passWord.equals("") || eMail.equals("")) {
            Toast.makeText(getApplicationContext(), "All the fields are required", Toast.LENGTH_SHORT).show();
        }else{

            Boolean userCheck = databaseHelper.CheckEmail(eMail);

            if(userCheck == true){
                Boolean insert = databaseHelper.Insert(eMail,passWord);

                if(insert == true){
                    Toast.makeText(getApplicationContext(), "Registration successful, Welcome to the app", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                }else{
                Toast.makeText(getApplicationContext(), "Email is taken.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void backToLogin(View view){
        Intent intent = new Intent(Registration.this, MainActivity.class);
        startActivity(intent);
    }
}
