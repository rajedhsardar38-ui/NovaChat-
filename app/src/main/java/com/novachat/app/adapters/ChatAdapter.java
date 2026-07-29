package com.novachat.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novachat.app.R;
import com.novachat.app.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SENT = 1;
    private static final int TYPE_RECEIVED = 2;

    private final List<ChatMessage> messageList;

    public ChatAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).isSentByMe()) {
            return TYPE_SENT;
        } else {
            return TYPE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage msg = messageList.get(position);
        if (holder instanceof SentViewHolder) {
            SentViewHolder sent = (SentViewHolder) holder;
            sent.txtText.setText(msg.getText());
            sent.txtTime.setText(msg.getTime());
        } else if (holder instanceof ReceivedViewHolder) {
            ReceivedViewHolder rec = (ReceivedViewHolder) holder;
            rec.txtText.setText(msg.getText());
            rec.txtTime.setText(msg.getTime());
            if (msg.getSenderName() != null && !msg.getSenderName().isEmpty()) {
                rec.txtSender.setVisibility(View.VISIBLE);
                rec.txtSender.setText(msg.getSenderName());
            } else {
                rec.txtSender.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView txtText, txtTime;

        SentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtText = itemView.findViewById(R.id.txtMsgSentText);
            txtTime = itemView.findViewById(R.id.txtMsgSentTime);
        }
    }

    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView txtSender, txtText, txtTime;

        ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSender = itemView.findViewById(R.id.txtMsgRecSender);
            txtText = itemView.findViewById(R.id.txtMsgRecText);
            txtTime = itemView.findViewById(R.id.txtMsgRecTime);
        }
    }
}
