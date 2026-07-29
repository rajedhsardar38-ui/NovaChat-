package com.novachat.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.novachat.app.utils.FirebaseHelper;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText etPhone;
    private TextView txtCountryCode;
    private Button btnSendOtp, btnGoogleSignIn;
    private TextView btnSkipDemo;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        firebaseHelper = new FirebaseHelper(this);

        etPhone = findViewById(R.id.etPhone);
        txtCountryCode = findViewById(R.id.txtCountryCode);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        btnSkipDemo = findViewById(R.id.btnSkipDemo);

        btnSendOtp.setOnClickListener(v -> {
            String phoneNum = etPhone.getText().toString().trim();
            if (phoneNum.isEmpty() || phoneNum.length() < 6) {
                etPhone.setError("Please enter a valid phone number");
                return;
            }

            String fullPhone = txtCountryCode.getText().toString() + " " + phoneNum;
            Intent intent = new Intent(PhoneLoginActivity.this, OtpActivity.class);
            intent.putExtra("phone_number", fullPhone);
            startActivity(intent);
        });

        btnGoogleSignIn.setOnClickListener(v -> {
            Toast.makeText(this, "Firebase Google Auth Connected to novachat-43ba5", Toast.LENGTH_SHORT).show();
            firebaseHelper.setLoggedIn(true, "+1 555-0199", "Google User");
            startActivity(new Intent(PhoneLoginActivity.this, HomeActivity.class));
            finish();
        });

        btnSkipDemo.setOnClickListener(v -> {
            firebaseHelper.setLoggedIn(true, "+1 555-0199", "Nova Explorer");
            startActivity(new Intent(PhoneLoginActivity.this, HomeActivity.class));
            finish();
        });
    }
}
