package com.example.edurate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SessionManager sessionManager;
        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("lookinadoaglo", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_in_@@%", false);

        if (isLoggedIn) {
            // User is logged in, proceed to main screen
            String username = sharedPreferences.getString("re_65_@#", "");
            sessionManager = SessionManager.getInstance();
            sessionManager.setUsername(username);
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }

        Button login = findViewById(R.id.loginbtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        Button signUpButton = findViewById(R.id.signupbtn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, SignUp1.class);
                startActivity(signup);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        username.setText("");
        password.setText("");
    }

    private void login() {
        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        String enteredEmail = username.getText().toString();
        String enteredPassword = password.getText().toString();

        if (enteredEmail.isEmpty() && enteredPassword.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        } else if (enteredEmail.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
        } else if (enteredPassword.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            // Query the "students" collection
            db.collection("students").document(enteredEmail).get().addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    DocumentSnapshot document = task1.getResult();
                    if (document.exists()) {
                        // User found in students
                        String storedPassword = document.getString("password");
                        if (enteredPassword.equals(storedPassword)) {
                            Toast.makeText(MainActivity.this, "Student Login Successful", Toast.LENGTH_SHORT).show();
                            SessionManager sessionManager = SessionManager.getInstance();
                            sessionManager.setUsername(enteredEmail); // Set the username
                            Intent hom = new Intent(MainActivity.this, Home.class);
                            startActivity(hom);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // User not found in students, check professors
                        db.collection("professors").document(enteredEmail).get().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                DocumentSnapshot document2 = task2.getResult();
                                if (document2.exists()) {
                                    // User found in professors
                                    String storedPassword = document2.getString("password");
                                    if (enteredPassword.equals(storedPassword)) {
                                        Toast.makeText(MainActivity.this, "Professor Login Successful", Toast.LENGTH_SHORT).show();
                                        SessionManager sessionManager = SessionManager.getInstance();
                                        sessionManager.setUsername(enteredEmail); // Set the username
                                        Intent hom = new Intent(MainActivity.this, HomeProfessor.class);
                                        startActivity(hom);
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // User not found in both collections
                                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Error checking professors database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error checking students database", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




}





