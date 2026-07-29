package com.novachat.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novachat.app.R;
import com.novachat.app.models.CallLogItem;
import com.novachat.app.models.Contact;
import com.novachat.app.models.GroupItem;
import com.novachat.app.models.User;

import java.util.List;

public class HomeContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_CHAT = 0;
    public static final int TYPE_GROUP = 1;
    public static final int TYPE_CALL = 2;
    public static final int TYPE_CONTACT = 3;

    private int currentType = TYPE_CHAT;
    private List<?> itemList;
    private final OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int type, Object item);
        void onActionClick(int actionType, Object item);
    }

    public HomeContentAdapter(List<?> itemList, OnItemClickListener clickListener) {
        this.itemList = itemList;
        this.clickListener = clickListener;
    }

    public void setData(int type, List<?> newList) {
        this.currentType = type;
        this.itemList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return currentType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_GROUP:
                return new GroupViewHolder(inflater.inflate(R.layout.item_group, parent, false));
            case TYPE_CALL:
                return new CallViewHolder(inflater.inflate(R.layout.item_call, parent, false));
            case TYPE_CONTACT:
                return new ContactViewHolder(inflater.inflate(R.layout.item_contact, parent, false));
            case TYPE_CHAT:
            default:
                return new ChatViewHolder(inflater.inflate(R.layout.item_chat, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = itemList.get(position);

        if (holder instanceof ChatViewHolder && item instanceof Contact) {
            Contact contact = (Contact) item;
            ChatViewHolder chat = (ChatViewHolder) holder;
            chat.txtName.setText(contact.getName());
            chat.txtAvatar.setText(contact.getName().substring(0, 1).toUpperCase());
            chat.txtLastMessage.setText(contact.getStatus());
            chat.txtTime.setText("Just now");
            chat.txtUnread.setVisibility(View.VISIBLE);
            chat.txtUnread.setText("1");
            chat.itemView.setOnClickListener(v -> clickListener.onItemClick(TYPE_CHAT, contact));
        } else if (holder instanceof GroupViewHolder && item instanceof GroupItem) {
            GroupItem group = (GroupItem) item;
            GroupViewHolder g = (GroupViewHolder) holder;
            g.txtGroupName.setText(group.getName());
            g.txtGroupSub.setText(group.getLastMessage());
            g.txtGroupAvatar.setText(group.getName().substring(0, 1).toUpperCase());
            g.btnQuickGroupCall.setOnClickListener(v -> clickListener.onActionClick(100, group));
            g.itemView.setOnClickListener(v -> clickListener.onItemClick(TYPE_GROUP, group));
        } else if (holder instanceof CallViewHolder && item instanceof CallLogItem) {
            CallLogItem call = (CallLogItem) item;
            CallViewHolder c = (CallViewHolder) holder;
            c.txtCallName.setText(call.getName());
            c.txtCallMeta.setText(call.getDirection() + " • " + call.getTime());
            c.txtCallAvatar.setText(call.getName().substring(0, 1).toUpperCase());
            c.btnCallBack.setOnClickListener(v -> clickListener.onActionClick(101, call));
            c.itemView.setOnClickListener(v -> clickListener.onItemClick(TYPE_CALL, call));
        } else if (holder instanceof ContactViewHolder && item instanceof Contact) {
            Contact contact = (Contact) item;
            ContactViewHolder cnt = (ContactViewHolder) holder;
            cnt.txtContactName.setText(contact.getName());
            cnt.txtContactStatus.setText(contact.getPhone() + " • " + (contact.isOnline() ? "Online" : "Offline"));
            cnt.txtContactAvatar.setText(contact.getName().substring(0, 1).toUpperCase());
            cnt.btnContactVoice.setOnClickListener(v -> clickListener.onActionClick(102, contact));
            cnt.btnContactVideo.setOnClickListener(v -> clickListener.onActionClick(103, contact));
            cnt.itemView.setOnClickListener(v -> clickListener.onItemClick(TYPE_CONTACT, contact));
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView txtAvatar, txtName, txtLastMessage, txtTime, txtUnread;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAvatar = itemView.findViewById(R.id.txtAvatar);
            txtName = itemView.findViewById(R.id.txtName);
            txtLastMessage = itemView.findViewById(R.id.txtLastMessage);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtUnread = itemView.findViewById(R.id.txtUnreadBadge);
        }
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView txtGroupAvatar, txtGroupName, txtGroupSub;
        ImageView btnQuickGroupCall;

        GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGroupAvatar = itemView.findViewById(R.id.txtGroupAvatar);
            txtGroupName = itemView.findViewById(R.id.txtGroupName);
            txtGroupSub = itemView.findViewById(R.id.txtGroupSub);
            btnQuickGroupCall = itemView.findViewById(R.id.btnQuickGroupCall);
        }
    }

    static class CallViewHolder extends RecyclerView.ViewHolder {
        TextView txtCallAvatar, txtCallName, txtCallMeta;
        ImageView btnCallBack;

        CallViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCallAvatar = itemView.findViewById(R.id.txtCallAvatar);
            txtCallName = itemView.findViewById(R.id.txtCallName);
            txtCallMeta = itemView.findViewById(R.id.txtCallMeta);
            btnCallBack = itemView.findViewById(R.id.btnCallBack);
        }
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView txtContactAvatar, txtContactName, txtContactStatus;
        ImageView btnContactVoice, btnContactVideo;

        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContactAvatar = itemView.findViewById(R.id.txtContactAvatar);
            txtContactName = itemView.findViewById(R.id.txtContactName);
            txtContactStatus = itemView.findViewById(R.id.txtContactStatus);
            btnContactVoice = itemView.findViewById(R.id.btnContactVoice);
            btnContactVideo = itemView.findViewById(R.id.btnContactVideo);
        }
    }
}
