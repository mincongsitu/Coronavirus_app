package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView attempts;
    private TextView textView;
    private Button login;
    private int counter = 5;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        attempts = findViewById(R.id.attempts);
        textView = findViewById(R.id.textView1);
        login = findViewById(R.id.btlogin);

    }

    public void login(View view){

        String passWord = password.getText().toString();
        String Email = email.getText().toString();

        Boolean loginCheck = databaseHelper.CheckLogin(passWord,Email);

        if(loginCheck == true){
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MainPage.class);
            startActivity(intent);
        }else{

            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
            counter--;
            textView.setText("Attempts left: ");
            attempts.setText(Integer.toString(counter));

            if(counter == 0){
                login.setEnabled(false);
            }
        }
    }


    public void register(View view){
        Intent intent = new Intent(MainActivity.this, Registration.class);
        startActivity(intent);
    }

}
