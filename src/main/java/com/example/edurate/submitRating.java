package com.example.edurate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class submitRating extends AppCompatActivity{

    private Button submitButton;

    private FirebaseFirestore db;
    private String aucemail;
    private TextView yourRatingTextView;
    private EditText reviewInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitrating);  // Set your layout here

        db = FirebaseFirestore.getInstance();
        SessionManager sessionManager = SessionManager.getInstance();
        aucemail = sessionManager.getUsername();
        yourRatingTextView = findViewById(R.id.yourRating);
        reviewInput = findViewById(R.id.reviewInput);

        fetchUserRating();

        submitButton = findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(v -> {
            String review = reviewInput.getText().toString().trim();
            if (review.isEmpty()) {
                Toast.makeText(this, "Please write a review before submitting.", Toast.LENGTH_SHORT).show();
                return;
            }
            submitUserReview(review);
            Intent intent = new Intent(submitRating.this, Home.class);
            startActivity(intent);  // Start the Home activity
            finish();
        });



    }
    private void fetchUserRating() {
        // Reference the professor's document in the "professors" collection
        DocumentReference professorRef = db.collection("professors").document("sgamal@aucegypt.edu");

        // Query the "ratings" collection under the professor
        professorRef.collection("ratings")
                .whereEqualTo("userEmail", aucemail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            // Extract the rating
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Double rating = document.getDouble("averageRating");
                                if (rating != null) {
                                    // Display the rating in the TextView
                                    yourRatingTextView.setText(String.valueOf(rating));
                                }
                            }
                        } else {
                            // No matching rating found
                            Toast.makeText(this, "No rating found for the user.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle errors
                        Toast.makeText(this, "Failed to fetch rating. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void submitUserReview(String review) {
        // Reference the professor's document in the "professors" collection
        DocumentReference professorRef = db.collection("professors").document("sgamal@aucegypt.edu");

        // Query the "ratings" collection under the professor to find the user's rating
        professorRef.collection("ratings")
                .whereEqualTo("userEmail", aucemail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentId = document.getId(); // Get the document ID
                                professorRef.collection("ratings").document(documentId)
                                        .update("review", review)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(this, "Review submitted successfully.", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(this, "Failed to submit review. Please try again.", Toast.LENGTH_SHORT).show();
                                        });
                                return; // Exit loop after updating
                            }
                        } else {
                            Toast.makeText(this, "No rating found for the user.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to fetch rating. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

