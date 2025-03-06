package com.ap.ai;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Set click listener for login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Validate inputs
                if (email.isEmpty()) {
                    editTextEmail.setError("Email is required");
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required");
                    return;
                }

                // TODO: Implement your login logic here
                // This is where you would typically make an API call or check credentials
                performLogin(email, password);
            }
        });
    }

    private void performLogin(String email, String password) {
        // Add your authentication logic here
        // For example: calling an API, checking local database, etc.
        
        // Example success scenario:
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
        // Navigate to main activity or dashboard
        // startActivity(new Intent(com.ap.ai.LoginActivity.this, MainActivity.class));
        // finish();
    }
} 