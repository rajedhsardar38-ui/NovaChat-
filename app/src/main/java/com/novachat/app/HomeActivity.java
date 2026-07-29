package com.novachat.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.novachat.app.adapters.HomeContentAdapter;
import com.novachat.app.adapters.StoryAdapter;
import com.novachat.app.models.CallLogItem;
import com.novachat.app.models.Contact;
import com.novachat.app.models.GroupItem;
import com.novachat.app.models.StoryItem;
import com.novachat.app.utils.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView txtHeaderTitle, txtAvatarHeader, txtHeaderStatus;
    private TextView tabChats, tabGroups, tabCalls, tabContacts;
    private RecyclerView rvStories, rvHomeContent;
    private FloatingActionButton fabAction;
    private ImageView btnSearch, btnSettings;
    private View btnProfileHeader;

    private HomeContentAdapter contentAdapter;
    private FirebaseHelper firebaseHelper;

    private List<Contact> chatList = new ArrayList<>();
    private List<GroupItem> groupList = new ArrayList<>();
    private List<CallLogItem> callList = new ArrayList<>();
    private List<Contact> contactList = new ArrayList<>();
    private List<StoryItem> storyList = new ArrayList<>();

    private int activeTab = HomeContentAdapter.TYPE_CHAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseHelper = new FirebaseHelper(this);

        txtHeaderTitle = findViewById(R.id.txtHeaderTitle);
        txtAvatarHeader = findViewById(R.id.txtAvatarHeader);
        txtHeaderStatus = findViewById(R.id.txtHeaderStatus);

        tabChats = findViewById(R.id.tabChats);
        tabGroups = findViewById(R.id.tabGroups);
        tabCalls = findViewById(R.id.tabCalls);
        tabContacts = findViewById(R.id.tabContacts);

        rvStories = findViewById(R.id.rvStories);
        rvHomeContent = findViewById(R.id.rvHomeContent);
        fabAction = findViewById(R.id.fabAction);

        btnSearch = findViewById(R.id.btnSearch);
        btnSettings = findViewById(R.id.btnSettings);
        btnProfileHeader = findViewById(R.id.btnProfileHeader);

        setupUserInfo();
        loadDummyData();
        setupAdapters();
        setupListeners();
    }

    private void setupUserInfo() {
        String name = firebaseHelper.getUserName();
        if (name != null && !name.isEmpty()) {
            txtAvatarHeader.setText(name.substring(0, 1).toUpperCase());
        }
    }

    private void loadDummyData() {
        // Stories
        storyList.add(new StoryItem("1", "Alex", "", true));
        storyList.add(new StoryItem("2", "Sophia", "", true));
        storyList.add(new StoryItem("3", "Marcus", "", false));
        storyList.add(new StoryItem("4", "Elena", "", true));
        storyList.add(new StoryItem("5", "David", "", false));

        // Chats & Contacts
        Contact c1 = new Contact("101", "Alex Rivera", "+1 555-0142", "", "Hey! Did you see the new NovaChat UI?", true);
        Contact c2 = new Contact("102", "Sophia Chen", "+1 555-0188", "", "Let's start the video call now 🎥", true);
        Contact c3 = new Contact("103", "Marcus Vance", "+1 555-0192", "", "Sending the encrypted files over...", false);
        Contact c4 = new Contact("104", "Elena Rostova", "+1 555-0205", "", "Awesome glassmorphism dark theme!", true);
        Contact c5 = new Contact("105", "David Miller", "+1 555-0219", "", "Talk to you later today.", false);

        chatList.add(c1);
        chatList.add(c2);
        chatList.add(c3);
        chatList.add(c4);

        contactList.add(c1);
        contactList.add(c2);
        contactList.add(c3);
        contactList.add(c4);
        contactList.add(c5);

        // Groups
        groupList.add(new GroupItem("g1", "Nova Elite Squad", 12, "Sarah: Group call scheduled at 8 PM!", "10:30 AM", 3));
        groupList.add(new GroupItem("g2", "Android Developers", 48, "Dev: AIDE Pro build fully verified", "Yesterday", 0));
        groupList.add(new GroupItem("g3", "Design Innovators", 24, "Elena: Glassmorphism mockups approved", "Jul 28", 1));

        // Calls
        callList.add(new CallLogItem("cl1", "Sophia Chen", "", "VIDEO", "INCOMING", "Today 09:15 AM"));
        callList.add(new CallLogItem("cl2", "Alex Rivera", "", "VOICE", "OUTGOING", "Today 08:30 AM"));
        callList.add(new CallLogItem("cl3", "Marcus Vance", "", "VOICE", "MISSED", "Yesterday 11:20 PM"));
    }

    private void setupAdapters() {
        // Story Adapter
        StoryAdapter storyAdapter = new StoryAdapter(storyList, story -> {
            Toast.makeText(this, "Viewing " + story.getName() + "'s Nova Story", Toast.LENGTH_SHORT).show();
        });
        rvStories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvStories.setAdapter(storyAdapter);

        // Home Content Adapter
        contentAdapter = new HomeContentAdapter(chatList, new HomeContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int type, Object item) {
                if (type == HomeContentAdapter.TYPE_CHAT && item instanceof Contact) {
                    Contact c = (Contact) item;
                    Intent intent = new Intent(HomeActivity.this, ChatDetailActivity.class);
                    intent.putExtra("contact_name", c.getName());
                    startActivity(intent);
                } else if (type == HomeContentAdapter.TYPE_GROUP && item instanceof GroupItem) {
                    GroupItem g = (GroupItem) item;
                    Intent intent = new Intent(HomeActivity.this, GroupChatActivity.class);
                    intent.putExtra("group_name", g.getName());
                    startActivity(intent);
                } else if (type == HomeContentAdapter.TYPE_CALL && item instanceof CallLogItem) {
                    CallLogItem cl = (CallLogItem) item;
                    Intent intent = new Intent(HomeActivity.this, VoiceCallActivity.class);
                    intent.putExtra("caller_name", cl.getName());
                    startActivity(intent);
                } else if (type == HomeContentAdapter.TYPE_CONTACT && item instanceof Contact) {
                    Contact c = (Contact) item;
                    Intent intent = new Intent(HomeActivity.this, ChatDetailActivity.class);
                    intent.putExtra("contact_name", c.getName());
                    startActivity(intent);
                }
            }

            @Override
            public void onActionClick(int actionType, Object item) {
                if (actionType == 100 && item instanceof GroupItem) {
                    // Group Call
                    Intent intent = new Intent(HomeActivity.this, GroupCallActivity.class);
                    startActivity(intent);
                } else if (actionType == 101 && item instanceof CallLogItem) {
                    // Call back
                    Intent intent = new Intent(HomeActivity.this, VoiceCallActivity.class);
                    intent.putExtra("caller_name", ((CallLogItem) item).getName());
                    startActivity(intent);
                } else if (actionType == 102 && item instanceof Contact) {
                    // Voice call contact
                    Intent intent = new Intent(HomeActivity.this, VoiceCallActivity.class);
                    intent.putExtra("caller_name", ((Contact) item).getName());
                    startActivity(intent);
                } else if (actionType == 103 && item instanceof Contact) {
                    // Video call contact
                    Intent intent = new Intent(HomeActivity.this, VideoCallActivity.class);
                    intent.putExtra("caller_name", ((Contact) item).getName());
                    startActivity(intent);
                }
            }
        });

        rvHomeContent.setLayoutManager(new LinearLayoutManager(this));
        rvHomeContent.setAdapter(contentAdapter);
    }

    private void setupListeners() {
        tabChats.setOnClickListener(v -> selectTab(HomeContentAdapter.TYPE_CHAT));
        tabGroups.setOnClickListener(v -> selectTab(HomeContentAdapter.TYPE_GROUP));
        tabCalls.setOnClickListener(v -> selectTab(HomeContentAdapter.TYPE_CALL));
        tabContacts.setOnClickListener(v -> selectTab(HomeContentAdapter.TYPE_CONTACT));

        btnProfileHeader.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SettingsActivity.class)));

        btnSearch.setOnClickListener(v -> Toast.makeText(this, "Search Nova Messages & Contacts", Toast.LENGTH_SHORT).show());

        fabAction.setOnClickListener(v -> {
            if (activeTab == HomeContentAdapter.TYPE_GROUP) {
                startActivity(new Intent(HomeActivity.this, GroupCallActivity.class));
            } else if (activeTab == HomeContentAdapter.TYPE_CALL) {
                Intent intent = new Intent(HomeActivity.this, VideoCallActivity.class);
                intent.putExtra("caller_name", "Alex Rivera");
                startActivity(intent);
            } else {
                Intent intent = new Intent(HomeActivity.this, ChatDetailActivity.class);
                intent.putExtra("contact_name", "Sophia Chen");
                startActivity(intent);
            }
        });
    }

    private void selectTab(int tabType) {
        activeTab = tabType;
        resetTabStyles();

        switch (tabType) {
            case HomeContentAdapter.TYPE_CHAT:
                tabChats.setBackgroundResource(R.drawable.bg_btn_primary_gradient);
                tabChats.setTextColor(getColor(R.color.text_primary));
                rvStories.setVisibility(View.VISIBLE);
                contentAdapter.setData(HomeContentAdapter.TYPE_CHAT, chatList);
                break;
            case HomeContentAdapter.TYPE_GROUP:
                tabGroups.setBackgroundResource(R.drawable.bg_btn_primary_gradient);
                tabGroups.setTextColor(getColor(R.color.text_primary));
                rvStories.setVisibility(View.GONE);
                contentAdapter.setData(HomeContentAdapter.TYPE_GROUP, groupList);
                break;
            case HomeContentAdapter.TYPE_CALL:
                tabCalls.setBackgroundResource(R.drawable.bg_btn_primary_gradient);
                tabCalls.setTextColor(getColor(R.color.text_primary));
                rvStories.setVisibility(View.GONE);
                contentAdapter.setData(HomeContentAdapter.TYPE_CALL, callList);
                break;
            case HomeContentAdapter.TYPE_CONTACT:
                tabContacts.setBackgroundResource(R.drawable.bg_btn_primary_gradient);
                tabContacts.setTextColor(getColor(R.color.text_primary));
                rvStories.setVisibility(View.GONE);
                contentAdapter.setData(HomeContentAdapter.TYPE_CONTACT, contactList);
                break;
        }
    }

    private void resetTabStyles() {
        tabChats.setBackground(null);
        tabChats.setTextColor(getColor(R.color.text_secondary));

        tabGroups.setBackground(null);
        tabGroups.setTextColor(getColor(R.color.text_secondary));

        tabCalls.setBackground(null);
        tabCalls.setTextColor(getColor(R.color.text_secondary));

        tabContacts.setBackground(null);
        tabContacts.setTextColor(getColor(R.color.text_secondary));
    }
}
