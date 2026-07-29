package com.novachat.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GroupCallActivity extends AppCompatActivity {

    private TextView txtTimer;
    private ImageView btnMute, btnSpeaker, btnEndCall;

    private boolean isMuted = false;
    private int secondsElapsed = 0;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_call);

        txtTimer = findViewById(R.id.txtGroupCallTimer);
        btnMute = findViewById(R.id.btnGroupMute);
        btnSpeaker = findViewById(R.id.btnGroupSpeaker);
        btnEndCall = findViewById(R.id.btnEndGroupCall);

        startTimer();

        btnMute.setOnClickListener(v -> {
            isMuted = !isMuted;
            btnMute.setImageResource(isMuted ? R.drawable.ic_mic_off : R.drawable.ic_mic);
            Toast.makeText(this, isMuted ? "Muted in Group Call" : "Microphone Active", Toast.LENGTH_SHORT).show();
        });

        btnSpeaker.setOnClickListener(v -> Toast.makeText(this, "Speakerphone Active", Toast.LENGTH_SHORT).show());

        btnEndCall.setOnClickListener(v -> {
            stopTimer();
            Toast.makeText(this, "Left Group Call", Toast.LENGTH_SHORT).show();
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
                txtTimer.setText(String.format("%02d:%02d • 4 Participants Active", mins, secs));
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
