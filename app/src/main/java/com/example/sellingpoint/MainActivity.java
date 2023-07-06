package com.example.sellingpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.InitLogIn_btn);
        Button signupBtn = findViewById(R.id.signup_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() { // when user selects login from menu
            @Override
            public void onClick(View view) { // go to login activity
                Intent login = new Intent(view.getContext(), LogInActivity.class);
                startActivity(login);
                finish();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(view.getContext(), SignUpActivity.class);
                startActivity(signup);
                finish();
            }
        });
    }
}