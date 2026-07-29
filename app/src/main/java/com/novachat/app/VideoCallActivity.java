package com.novachat.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VideoCallActivity extends AppCompatActivity {

    private TextView txtCallerName, txtTimer;
    private ImageView btnFlip, btnMute, btnToggleCam, btnEndCall;

    private boolean isMuted = false;
    private boolean isCamOn = true;
    private int secondsElapsed = 0;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        txtCallerName = findViewById(R.id.txtVideoCallerName);
        txtTimer = findViewById(R.id.txtVideoTimer);

        btnFlip = findViewById(R.id.btnFlipCamera);
        btnMute = findViewById(R.id.btnVideoMute);
        btnToggleCam = findViewById(R.id.btnToggleCam);
        btnEndCall = findViewById(R.id.btnEndVideoCall);

        String caller = getIntent().getStringExtra("caller_name");
        if (caller != null && !caller.isEmpty()) {
            txtCallerName.setText(caller);
        }

        startTimer();

        btnFlip.setOnClickListener(v -> Toast.makeText(this, "Switched Camera", Toast.LENGTH_SHORT).show());

        btnMute.setOnClickListener(v -> {
            isMuted = !isMuted;
            btnMute.setImageResource(isMuted ? R.drawable.ic_mic_off : R.drawable.ic_mic);
            Toast.makeText(this, isMuted ? "Microphone Muted" : "Microphone Active", Toast.LENGTH_SHORT).show();
        });

        btnToggleCam.setOnClickListener(v -> {
            isCamOn = !isCamOn;
            Toast.makeText(this, isCamOn ? "Camera Enabled" : "Camera Disabled", Toast.LENGTH_SHORT).show();
        });

        btnEndCall.setOnClickListener(v -> {
            stopTimer();
            Toast.makeText(this, "Video Call Ended", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                secondsElapsed++;
                int mins = secondsElapsed / 60;
                int secs = secondsElapsed % 60;
                txtTimer.setText(String.format("%02d:%02d • 1080p 60fps Encrypted", mins, secs));
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.postDelayed(timerRunnable, 1000);
    }

    private void stopTimer() {
        if (timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
