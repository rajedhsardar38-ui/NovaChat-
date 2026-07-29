package com.novachat.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.novachat.app.adapters.ChatAdapter;
import com.novachat.app.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatDetailActivity extends AppCompatActivity {

    private ImageView btnBack, btnVoiceCall, btnVideoCall, btnAttach, btnSend;
    private TextView txtName, txtAvatar, txtStatus;
    private EditText etMessageInput;
    private RecyclerView rvMessages;

    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList = new ArrayList<>();
    private String contactName = "Alex Rivera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        if (getIntent().hasExtra("contact_name")) {
            contactName = getIntent().getStringExtra("contact_name");
        }

        btnBack = findViewById(R.id.btnChatBack);
        btnVoiceCall = findViewById(R.id.btnChatVoiceCall);
        btnVideoCall = findViewById(R.id.btnChatVideoCall);
        btnAttach = findViewById(R.id.btnAttach);
        btnSend = findViewById(R.id.btnSend);

        txtName = findViewById(R.id.txtChatName);
        txtAvatar = findViewById(R.id.txtChatAvatar);
        txtStatus = findViewById(R.id.txtChatSubStatus);

        etMessageInput = findViewById(R.id.etMessageInput);
        rvMessages = findViewById(R.id.rvMessages);

        txtName.setText(contactName);
        txtAvatar.setText(contactName.substring(0, 1).toUpperCase());

        setupChatData();

        btnBack.setOnClickListener(v -> finish());

        btnVoiceCall.setOnClickListener(v -> {
            Intent intent = new Intent(ChatDetailActivity.this, VoiceCallActivity.class);
            intent.putExtra("caller_name", contactName);
            startActivity(intent);
        });

        btnVideoCall.setOnClickListener(v -> {
            Intent intent = new Intent(ChatDetailActivity.this, VideoCallActivity.class);
            intent.putExtra("caller_name", contactName);
            startActivity(intent);
        });

        btnAttach.setOnClickListener(v -> {
            Toast.makeText(this, "Select photo, file or audio attachment", Toast.LENGTH_SHORT).show();
        });

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void setupChatData() {
        messageList.add(new ChatMessage("1", "c1", contactName, "Hey! Check out this new NovaChat AMOLED glassmorphism design!", "10:30 AM", false, "TEXT", true));
        messageList.add(new ChatMessage("2", "me", "You", "Looks super sleek! Love the glass cards and glowing cyan gradients.", "10:32 AM", true, "TEXT", true));
        messageList.add(new ChatMessage("3", "c1", contactName, "And it's 100% written in pure Java & XML!", "10:33 AM", false, "TEXT", true));

        chatAdapter = new ChatAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(chatAdapter);
    }

    private void sendMessage() {
        String text = etMessageInput.getText().toString().trim();
        if (text.isEmpty()) return;

        ChatMessage msg = new ChatMessage(
                String.valueOf(System.currentTimeMillis()),
                "me",
                "You",
                text,
                "Just now",
                true,
                "TEXT",
                false
        );

        messageList.add(msg);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        rvMessages.smoothScrollToPosition(messageList.size() - 1);
        etMessageInput.setText("");

        // Simulated auto response after 1.5 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ChatMessage reply = new ChatMessage(
                    String.valueOf(System.currentTimeMillis() + 1),
                    "c1",
                    contactName,
                    "Received in NovaChat: " + text,
                    "Just now",
                    false,
                    "TEXT",
                    true
            );
            messageList.add(reply);
            chatAdapter.notifyItemInserted(messageList.size() - 1);
            rvMessages.smoothScrollToPosition(messageList.size() - 1);
        }, 1500);
    }
}
