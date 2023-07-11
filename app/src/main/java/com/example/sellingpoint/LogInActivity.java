package com.example.sellingpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        EditText userEmail = findViewById(R.id.editText_emaillogin);
        EditText userpswrd = findViewById(R.id.edittext_pswrdlogin);

        ImageButton goBack = findViewById(R.id.imageButtonBack);
        Button login = findViewById(R.id.LogIn_btn);


        goBack.setOnClickListener(new View.OnClickListener() { // when user selects back arrow
            @Override
            public void onClick(View view) { // go to login activity
                Intent mainactivity = new Intent(view.getContext(), MainActivity.class);
                startActivity(mainactivity);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                String password = userpswrd.getText().toString().trim();

                if(email == "" || password == ""){
                    Toast.makeText(LogInActivity.this, "Please complete all fields. ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LogInActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(view.getContext(), ProfileMenuActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(LogInActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}