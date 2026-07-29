package com.novachat.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.novachat.app.utils.FirebaseHelper;

public class OtpActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView txtOtpSub, txtResendOtp;
    private EditText etOtpCode;
    private Button btnVerifyOtp;
    private FirebaseHelper firebaseHelper;
    private String phoneNumber = "+1 555-0199";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        firebaseHelper = new FirebaseHelper(this);

        if (getIntent().hasExtra("phone_number")) {
            phoneNumber = getIntent().getStringExtra("phone_number");
        }

        btnBack = findViewById(R.id.btnBack);
        txtOtpSub = findViewById(R.id.txtOtpSub);
        txtResendOtp = findViewById(R.id.txtResendOtp);
        etOtpCode = findViewById(R.id.etOtpCode);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        txtOtpSub.setText("Code sent to " + phoneNumber);

        btnBack.setOnClickListener(v -> finish());

        startTimer();

        btnVerifyOtp.setOnClickListener(v -> {
            String otp = etOtpCode.getText().toString().trim();
            if (otp.length() < 4) {
                etOtpCode.setError("Enter verification code");
                return;
            }

            firebaseHelper.setLoggedIn(true, phoneNumber, "Nova Member");
            Toast.makeText(OtpActivity.this, "Phone Number Verified Successfully!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        txtResendOtp.setOnClickListener(v -> {
            Toast.makeText(OtpActivity.this, "Resending OTP code to " + phoneNumber, Toast.LENGTH_SHORT).show();
            startTimer();
        });
    }

    private void startTimer() {
        txtResendOtp.setEnabled(false);
        new CountDownTimer(45000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                txtResendOtp.setText("Resend Code in 00:" + (seconds < 10 ? "0" : "") + seconds);
            }

            @Override
            public void onFinish() {
                txtResendOtp.setText("Resend Verification OTP");
                txtResendOtp.setEnabled(true);
            }
        }.start();
    }
}
