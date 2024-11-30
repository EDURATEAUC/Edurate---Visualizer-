package com.example.edurate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Objects;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp3 extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, fullnameEditText, password2EditText;
    private Button signupButton, backButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.clearSession();
        db = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        fullnameEditText = findViewById(R.id.fullname);
        password2EditText = findViewById(R.id.password2);

        signupButton = findViewById(R.id.signupbutton);
        backButton = findViewById(R.id.backbutton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp3.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean isValidEmail(String email) {
        // Regular expression pattern for email validation
        String emailPattern = "[a-zA-Z0-9._-]+@aucegypt.edu";

        return email.matches(emailPattern);
    }

    private void signUp() {
        final String fullname = fullnameEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();
        final String password2 = password2EditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || fullname.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (!isValidEmail(email)) {
            Toast.makeText(this, "Please enter an AUC email address", Toast.LENGTH_SHORT).show();
            return;
        } else if (Objects.equals(password, password2) != true) {
            Toast.makeText(this, "Passwords do not match. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> passwordErrors = validatePassword(password);
        if (!passwordErrors.isEmpty()) {
            for (String error : passwordErrors) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
            return;
        }

        // Check if email already exists in the database
        db.collection("professors")
                .whereEqualTo("aucemail", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            // Email already exists, ask the user to login instead of signup
                            Toast.makeText(SignUp3.this, "An account with this email already exists. Please login.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Set the document ID as the username
                            Map<String, Object> student = new HashMap<>();
                            student.put("aucemail", email);
                            student.put("password", password);
                            student.put("fullname", fullname);
                            // Add the user data to Firestore with the document ID as the username
                            db.collection("professors")
                                    .document(email)
                                    .set(student)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignUp3.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUp3.this, preferences.class);
                                        intent.putExtra("aucemail", email);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SignUp3.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }
                });
    }

    private List<String> validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password.length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }

        if (!password.matches(".*[a-z].*")) {
            errors.add("Password must contain at least one lowercase letter");
        }

        if (!password.matches(".*[A-Z].*")) {
            errors.add("Password must contain at least one uppercase letter");
        }

        if (!password.matches(".*\\d.*")) {
            errors.add("Password must contain at least one digit");
        }

        if (!password.matches(".*[@#$%^&+=/!?.\\-_~].*")) {
            errors.add("Password must contain at least one special character");
        }

        return errors;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.clearSession();
    }

    @Override
    protected void onStop() {

        super.onStop();
        emailEditText = findViewById(R.id.email);
        fullnameEditText = findViewById(R.id.fullname);
        passwordEditText = findViewById(R.id.password);
        password2EditText = findViewById(R.id.password2);
        emailEditText.setText("");
        fullnameEditText.setText("");
        passwordEditText.setText("");
        password2EditText.setText("");
    }
}