package com.example.edurate;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
public class SignUp1 extends AppCompatActivity {

    private Button studentButton, professorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.clearSession();

        studentButton = findViewById(R.id.studentbutton);
        professorButton = findViewById(R.id.professorbutton);

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp1.this, SignUp2.class);
                startActivity(intent);
                finish();
            }
        });

        professorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prof = new Intent(SignUp1.this, SignUp3.class);
                startActivity(prof);
                finish();
            }
        });


    }
}
