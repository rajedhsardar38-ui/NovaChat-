package com.novachat.app;

import android.content.Intent;
import android.os.Bundle;
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

public class GroupChatActivity extends AppCompatActivity {

    private ImageView btnBack, btnGroupCall, btnAttach, btnSend;
    private TextView txtGroupName;
    private EditText etInput;
    private RecyclerView rvMessages;

    private ChatAdapter adapter;
    private List<ChatMessage> messageList = new ArrayList<>();
    private String groupName = "Nova Elite Squad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        if (getIntent().hasExtra("group_name")) {
            groupName = getIntent().getStringExtra("group_name");
        }

        btnBack = findViewById(R.id.btnGroupBack);
        btnGroupCall = findViewById(R.id.btnGroupCall);
        btnAttach = findViewById(R.id.btnGroupAttach);
        btnSend = findViewById(R.id.btnGroupSend);
        txtGroupName = findViewById(R.id.txtGroupName);
        etInput = findViewById(R.id.etGroupInput);
        rvMessages = findViewById(R.id.rvGroupMessages);

        txtGroupName.setText(groupName);

        setupGroupMessages();

        btnBack.setOnClickListener(v -> finish());

        btnGroupCall.setOnClickListener(v -> {
            startActivity(new Intent(GroupChatActivity.this, GroupCallActivity.class));
        });

        btnAttach.setOnClickListener(v -> {
            Toast.makeText(this, "Share media with " + groupName, Toast.LENGTH_SHORT).show();
        });

        btnSend.setOnClickListener(v -> {
            String text = etInput.getText().toString().trim();
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
            adapter.notifyItemInserted(messageList.size() - 1);
            rvMessages.smoothScrollToPosition(messageList.size() - 1);
            etInput.setText("");
        });
    }

    private void setupGroupMessages() {
        messageList.add(new ChatMessage("1", "m1", "Sarah", "Group call starting in 5 minutes! 🚀", "10:15 AM", false, "TEXT", true));
        messageList.add(new ChatMessage("2", "m2", "Marcus", "I'm ready! Connecting with glassmorphism UI.", "10:16 AM", false, "TEXT", true));
        messageList.add(new ChatMessage("3", "me", "You", "Joining the group voice call now!", "10:18 AM", true, "TEXT", true));

        adapter = new ChatAdapter(messageList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rvMessages.setLayoutManager(lm);
        rvMessages.setAdapter(adapter);
    }
}
