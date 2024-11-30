package com.example.edurate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchProfessor extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homestudent);

        Button computer = findViewById(R.id.computerscience);
        Button biology = findViewById(R.id.biology);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set the default selected item in the bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.search);

        // Set the listener for the bottom navigation view
        bottomNavigationView.setOnNavigationItemSelectedListener(this); // 'this' refers to HomeStudent which implements OnNavigationItemSelectedListener

        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the department activity when the button is clicked
                Intent intent = new Intent(SearchProfessor.this, department.class);
                startActivity(intent);
            }
        });

        biology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the department activity when the button is clicked
                Intent intent = new Intent(SearchProfessor.this, biology.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        // Handle navigation item clicks
        if (id == R.id.home) {
            // Navigate to HomeActivity (adjust as per your project)
            intent = new Intent(SearchProfessor.this, Home.class);
        } else if (id == R.id.search) {
            // Navigate to SearchActivity (adjust as per your project)
//            intent = new Intent(HomeStudent.this, Search.class);
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
}