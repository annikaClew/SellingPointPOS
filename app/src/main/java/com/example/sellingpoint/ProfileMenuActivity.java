package com.example.sellingpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);

        Button logout_btn = findViewById(R.id.logout_btn);
        Button neworder_btn = findViewById(R.id.neworder_btn);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = auth.getCurrentUser();
        CollectionReference usersRef = db.collection("Users");
        String userID = fbUser.getUid(); // Replace with the actual user ID you want to retrieve

        Query query = usersRef.whereEqualTo("userID", userID);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                    // Retrieve the string values from the document
                    String userName = documentSnapshot.getString("firstName"); // Replace with your field name
                    String userPos = documentSnapshot.getString("position"); // Replace with your field name

                    // Update the TextViews with the retrieved strings
                    TextView usernameView = findViewById(R.id.UserName); // Replace with the ID of your TextView
                    usernameView.setText(userName);

                    TextView positionView = findViewById(R.id.position_textview); // Replace with the ID of your TextView
                    positionView.setText(userPos);
                }
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
            }
        });

        neworder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                finish();
            }
        });

    }
}