package com.novachat.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VoiceCallActivity extends AppCompatActivity {

    private TextView txtCallerName, txtAvatar, txtTimer;
    private ImageView btnMute, btnSpeaker, btnEndCall;

    private boolean isMuted = false;
    private boolean isSpeakerOn = false;
    private int secondsElapsed = 0;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_call);

        txtCallerName = findViewById(R.id.txtVoiceCallerName);
        txtAvatar = findViewById(R.id.txtVoiceAvatar);
        txtTimer = findViewById(R.id.txtVoiceTimer);

        btnMute = findViewById(R.id.btnMute);
        btnSpeaker = findViewById(R.id.btnSpeaker);
        btnEndCall = findViewById(R.id.btnEndVoiceCall);

        String caller = getIntent().getStringExtra("caller_name");
        if (caller != null && !caller.isEmpty()) {
            txtCallerName.setText(caller);
            txtAvatar.setText(caller.substring(0, 1).toUpperCase());
        }

        startTimer();

        btnMute.setOnClickListener(v -> {
            isMuted = !isMuted;
            btnMute.setImageResource(isMuted ? R.drawable.ic_mic_off : R.drawable.ic_mic);
            Toast.makeText(this, isMuted ? "Microphone Muted" : "Microphone Active", Toast.LENGTH_SHORT).show();
        });

        btnSpeaker.setOnClickListener(v -> {
            isSpeakerOn = !isSpeakerOn;
            Toast.makeText(this, isSpeakerOn ? "Speakerphone On" : "Ear speaker Active", Toast.LENGTH_SHORT).show();
        });

        btnEndCall.setOnClickListener(v -> {
            stopTimer();
            Toast.makeText(this, "Voice Call Ended", Toast.LENGTH_SHORT).show();
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
                txtTimer.setText(String.format("%02d:%02d", mins, secs));
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
