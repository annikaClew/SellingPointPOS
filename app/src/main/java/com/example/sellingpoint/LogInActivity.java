package com.example.sellingpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ImageButton goBack = findViewById(R.id.imageButtonBack);
        Button login = findViewById(R.id.SignUp_btn);

        goBack.setOnClickListener(new View.OnClickListener() { // when user selects back arrow
            @Override
            public void onClick(View view) { // go to login activity
                Intent mainactivity = new Intent(view.getContext(), MainActivity.class);
                startActivity(mainactivity);
                finish();
            }
        });


    }
}