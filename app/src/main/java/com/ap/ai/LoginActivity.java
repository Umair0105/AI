package com.ap.ai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.ap.ai.models.ResponseModel;
import com.ap.ai.network.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Initialize progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (phone.isEmpty()) {
                    editTextEmail.setError("Phone is required");
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required");
                    return;
                }

                performLogin(phone, password);
            }
        });
    }

    private void performLogin(String phone, String password) {
        // Show progress dialog
        progressDialog.show();

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceInfo = String.format("Android|%s|%s|%s", 
            android.os.Build.VERSION.RELEASE,
            android.os.Build.MANUFACTURER,
            android.os.Build.MODEL);
        String token = "dIqutVsGSDC-_FNnBAKoxe:APA91bEHF9ch4QQWZ3CEADkH24ZHMYHB66re8I1zN2n2swbD_97IZKR6nUarrNc9Wz3XS_wL0Qezhnfh7AkKpw7DIacDPwacURCGNYKbO-1vyGanH63z8pA";

        ApiClient.login(phone, password, deviceId, deviceInfo, token, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // Dismiss progress dialog
                progressDialog.dismiss();
                
                try {
                    Gson gson = new Gson();
                    ResponseModel responseModel = gson.fromJson(response, ResponseModel.class);
                    
                    if (responseModel.getStatus() == 200) {
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Error processing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                // Dismiss progress dialog
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
} 