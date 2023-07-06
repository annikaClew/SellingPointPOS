package com.example.sellingpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageButton goBack = findViewById(R.id.imageButtonBack2);

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