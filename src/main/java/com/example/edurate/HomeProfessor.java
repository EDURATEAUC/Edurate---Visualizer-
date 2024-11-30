package com.example.edurate;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.LinearLayout;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;


import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeProfessor extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    private FirebaseFirestore db;
    private String aucemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeprofessor);

        db = FirebaseFirestore.getInstance();
        SessionManager sessionManager = SessionManager.getInstance();
        aucemail = sessionManager.getUsername();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set the default selected item in the bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Set the listener for the bottom navigation view
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        fetchUserName();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        // Handle navigation item clicks
        if (id == R.id.home) {
            // Navigate to HomeActivity (adjust as per your project)
            //intent = new Intent(HomeStu.this, Home.class);
        } else if (id == R.id.search) {
            // Navigate to SearchActivity (adjust as per your project)
            intent = new Intent(HomeProfessor.this, SearchProfessor.class);
        } else if (id == R.id.library) {
            // Navigate to LibraryActivity (adjust as per your project)
//            intent = new Intent(HomeStudent.this, LibraryActivity.class);
        } else if (id == R.id.profile) {
            // Navigate to MyProfileActivity (adjust as per your project)
//            intent = new Intent(HomeStudent.this, MyProfile.class);
        }

        // Start the selected activity
        if (intent != null) {
            startActivity(intent);
            return true;
        }

        return false;
    }

    private void fetchUserName() {
        // Reference the professor's document in the "professors" collection
        DocumentReference studentRef = db.collection("professors").document(aucemail);

        studentRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    // Check if the document exists
                    if (documentSnapshot.exists()) {
                        // Get the fullname field from the document
                        String fullname = documentSnapshot.getString("fullname");

                        // You can now use the fullname value, for example, display it in a TextView:
                        if (fullname != null) {
                            // Do something with the fullname, like showing it in a UI element
                            // For example:
                            TextView welcomeTextView = findViewById(R.id.welcome_text);
                            welcomeTextView.setText("Welcome, " + fullname);
                        }
                    } else {
                        // Document does not exist, handle accordingly
                        Toast.makeText(HomeProfessor.this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
