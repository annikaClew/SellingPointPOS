package com.example.sellingpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // initializing elements

        ImageButton goBack = findViewById(R.id.imageButtonBack2);
        Button signup = findViewById(R.id.LogIn_btn);
        EditText email = findViewById(R.id.editText_useremail);
        EditText firstname = findViewById(R.id.editText_emaillogin);
        EditText lastname = findViewById(R.id.editText_emaillogin);
        EditText position = findViewById(R.id.editText_empPosition);
        EditText pswrd = findViewById(R.id.edittext_pswrd);
        EditText rePswrd = findViewById(R.id.edittext_pswrdlogin);

        // when user selects back arrow
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // go to login activity
                Intent mainactivity = new Intent(view.getContext(), MainActivity.class);
                startActivity(mainactivity);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uEmail = email.getText().toString().trim();
                String fName = firstname.getText().toString().trim();
                String lName = lastname.getText().toString().trim();
                String pos = position.getText().toString().trim();
                String password = pswrd.getText().toString().trim();
                String repassword = pswrd.getText().toString().trim();

                // check that username, first name, lastname, position are not empty
                if(TextUtils.isEmpty(uEmail) || TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName) || TextUtils.isEmpty(pos) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check that password is long enough
                if(password.length() < 6){
                    Toast.makeText(SignUpActivity.this, "Password must be greater than 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check that passwords match
                if(!password.equals(repassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // creating new user with entered info
                User newUser = new User(uEmail, fName, lName, pos, password);
                registerUser(newUser);




            }
        });
    }

    public void registerUser(User newUser)
    {
        final boolean success;
        auth.createUserWithEmailAndPassword(newUser.getEmail(), newUser.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();

                            auth.signInWithEmailAndPassword(newUser.getEmail(), newUser.getPassword());
                            FirebaseUser fbUser = auth.getCurrentUser();

                            // add additional info into firebase
                            addUserInfo(newUser, fbUser);

                            //sending new user to profile page
                            Intent intent = new Intent(SignUpActivity.this, ProfileMenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            System.out.println(task.getException());
                        }
                    }
                });
    }

    public void addUserInfo(User newUser, FirebaseUser fbUser)
    {
        // preparing data to be stored in firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("email", newUser.getEmail());
        user.put("firstName", newUser.firstname);
        user.put("lastName", newUser.lastname);
        user.put("position", newUser.position);
        user.put("userID", fbUser.getUid());

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("dbfirebase", "save: " + user);
                        String text = "Data save complete!";
                        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dbfirebase Failed", user.toString());
                        String text = "You were unsuccessful at creating an account";
                        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

}