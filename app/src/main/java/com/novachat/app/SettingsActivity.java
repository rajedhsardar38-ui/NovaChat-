package com.novachat.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.novachat.app.utils.FirebaseHelper;

public class SettingsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private CardView btnProfile;
    private Button btnLogout;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firebaseHelper = new FirebaseHelper(this);

        btnBack = findViewById(R.id.btnSettingsBack);
        btnProfile = findViewById(R.id.btnSettingProfile);
        btnLogout = findViewById(R.id.btnSettingsLogout);

        btnBack.setOnClickListener(v -> finish());

        btnProfile.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, ProfileActivity.class)));

        btnLogout.setOnClickListener(v -> {
            firebaseHelper.logout();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingsActivity.this, PhoneLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
