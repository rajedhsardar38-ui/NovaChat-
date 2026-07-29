package com.novachat.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.novachat.app.utils.FirebaseHelper;

public class ProfileActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView txtAvatar, txtName, txtPhone;
    private Button btnLogout;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseHelper = new FirebaseHelper(this);

        btnBack = findViewById(R.id.btnProfileBack);
        txtAvatar = findViewById(R.id.txtProfileAvatar);
        txtName = findViewById(R.id.txtProfileName);
        txtPhone = findViewById(R.id.txtProfilePhone);
        btnLogout = findViewById(R.id.btnProfileLogout);

        btnBack.setOnClickListener(v -> finish());

        String name = firebaseHelper.getUserName();
        String phone = firebaseHelper.getUserPhone();

        txtName.setText(name);
        txtPhone.setText(phone);
        if (name != null && !name.isEmpty()) {
            txtAvatar.setText(name.substring(0, 1).toUpperCase());
        }

        btnLogout.setOnClickListener(v -> {
            firebaseHelper.logout();
            Toast.makeText(this, "Signed out of NovaChat", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, PhoneLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
