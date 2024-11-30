package com.example.edurate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Map;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class profView extends AppCompatActivity {

    private String aucemail;
    private FirebaseFirestore db;
    private TextView profAverageView;

    private TextView courseAverageView;

    private Button submitRatingButton, backButton, viewAnalyticsButton;

    String profEmail = "sgamal@aucegypt.edu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profview);

        profAverageView = findViewById(R.id.profaverage);
        courseAverageView = findViewById(R.id.courseaverage);


        SessionManager sessionManager = SessionManager.getInstance();
        aucemail = sessionManager.getUsername();
        db = FirebaseFirestore.getInstance();

        loadProfAverageFromDatabase();
        loadCourseAverageFromDatabase();

        backButton = findViewById(R.id.backbutton);
        submitRatingButton = findViewById(R.id.submitrating);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profView.this, course.class);
                startActivity(intent);
                finish();
            }
        });

        submitRatingButton.setOnClickListener(v -> checkPermissions());


    }



    private void loadProfAverageFromDatabase() {
        db.collection("professors").document(profEmail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String profaverage = document.getString("profaverage");
                    if (profaverage != null) {
                        profAverageView.setText(profaverage);
                    } else {
                        profAverageView.setText("Not Available");
                    }
                } else {
                        profAverageView.setText("Document not found.");
                    }
                }
        });
    }

    private void loadCourseAverageFromDatabase() {
        db.collection("professors").document(profEmail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> courseaverageMap = (Map<String, Object>) document.get("courseaverage");

                    if (courseaverageMap != null) {
                        String courseKey = "csce3701";
                        Object courseAverageValue = courseaverageMap.get(courseKey);

                        if (courseAverageValue != null) {
                            // Display the course average if it exists
                            courseAverageView.setText(courseAverageValue.toString());
                        } else {
                            // Handle the case where the course key does not exist
                            courseAverageView.setText("Not Available");
                        }
                    } else {
                        // Handle the case where the "courseaverage" map is null
                        courseAverageView.setText("Data Not Available");
                    }
                } else {
                    // Handle the case where the document does not exist
                    courseAverageView.setText("Document not found.");
                }
            }
        });
    }

    private void checkPermissions() {
        db.collection("students").document(aucemail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    List<String> courses = (List<String>) document.get("courses");

                    if (courses != null && courses.contains("csce3701")) {
                        // User is permitted, navigate to the submitReview page
                        Intent intent = new Intent(profView.this, submitReview.class);
                        startActivity(intent);
                    } else {
                        // User is not permitted
                        Toast.makeText(
                                profView.this,
                                "You are not permitted to review this professor",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                } else {
                    Toast.makeText(
                            profView.this,
                            "User data not found in the database.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            } else {
                Toast.makeText(
                        profView.this,
                        "Failed to access the database. Please try again.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }


}
