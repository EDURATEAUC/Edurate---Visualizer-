package com.example.edurate;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class submitReview extends AppCompatActivity{
    private Spinner spinnerTeachingClarity, spinnerCommunication, spinnerFairnessGrading, spinnerResourcesAvailable, spinnerFeedbackSupport;
    private Button submitButton;

    private FirebaseFirestore db;
    private String aucemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitreview);  // Set your layout here

        db = FirebaseFirestore.getInstance();
        SessionManager sessionManager = SessionManager.getInstance();
        aucemail = sessionManager.getUsername();

        // Initialize Spinners
        spinnerTeachingClarity = findViewById(R.id.spinnerTeachingClarity);
        spinnerCommunication = findViewById(R.id.spinnerCommunication);
        spinnerFairnessGrading = findViewById(R.id.spinnerFairnessGrading);
        spinnerResourcesAvailable = findViewById(R.id.spinnerResourcesAvailable);
        spinnerFeedbackSupport = findViewById(R.id.spinnerFeedbackSupport);

        // Create an ArrayAdapter with values 1-5
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"1", "2", "3", "4", "5"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to each spinner
        spinnerTeachingClarity.setAdapter(adapter);
        spinnerCommunication.setAdapter(adapter);
        spinnerFairnessGrading.setAdapter(adapter);
        spinnerResourcesAvailable.setAdapter(adapter);
        spinnerFeedbackSupport.setAdapter(adapter);

        // Initialize Submit Button
        submitButton = findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected values from each spinner
                String teachingClarityRating = spinnerTeachingClarity.getSelectedItem().toString();
                String communicationRating = spinnerCommunication.getSelectedItem().toString();
                String fairnessGradingRating = spinnerFairnessGrading.getSelectedItem().toString();
                String resourcesAvailableRating = spinnerResourcesAvailable.getSelectedItem().toString();
                String feedbackSupportRating = spinnerFeedbackSupport.getSelectedItem().toString();

                int teachingClarity = Integer.parseInt(teachingClarityRating);
                int communication = Integer.parseInt(communicationRating);
                int fairnessGrading = Integer.parseInt(fairnessGradingRating);
                int resourcesAvailable = Integer.parseInt(resourcesAvailableRating);
                int feedbackSupport = Integer.parseInt(feedbackSupportRating);

                double averageRating = (teachingClarity + communication + fairnessGrading + resourcesAvailable + feedbackSupport) / 5.0;

                Map<String, Object> reviewData = new HashMap<>();

                reviewData.put("userEmail", aucemail);
                reviewData.put("averageRating", averageRating);

                String documentId = aucemail + "_" + "3701";

                db.collection("professors")
                        .document("sgamal@aucegypt.edu") // Professor email or unique identifier
                        .collection("ratings")  // You could use reviews as a sub-collection
                        .document(documentId)
                        .set(reviewData)
                        .addOnSuccessListener(documentReference -> {
                            // Show a success message
                            Toast.makeText(submitReview.this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(submitReview.this, submitRating.class);
                            startActivity(intent);
                            finish();  // Optionally finish this activity
                        })
                        .addOnFailureListener(e -> {
                            // Show an error message
                            Toast.makeText(submitReview.this, "Error submitting review: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}
