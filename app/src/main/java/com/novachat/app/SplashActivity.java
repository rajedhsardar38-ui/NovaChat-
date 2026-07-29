package com.novachat.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.novachat.app.utils.FirebaseHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        android.view.View container = findViewById(R.id.containerLogo);
        if (container != null) {
            ScaleAnimation scaleAnim = new ScaleAnimation(
                    0.8f, 1.0f, 0.8f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            scaleAnim.setDuration(1200);

            AlphaAnimation alphaAnim = new AlphaAnimation(0.2f, 1.0f);
            alphaAnim.setDuration(1200);

            container.startAnimation(scaleAnim);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            FirebaseHelper helper = new FirebaseHelper(SplashActivity.this);
            Intent intent;
            if (helper.isLoggedIn()) {
                intent = new Intent(SplashActivity.this, HomeActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, PhoneLoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000);
    }
}
