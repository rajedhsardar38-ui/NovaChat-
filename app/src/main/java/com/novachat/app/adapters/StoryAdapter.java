package com.novachat.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novachat.app.R;
import com.novachat.app.models.StoryItem;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
    private final List<StoryItem> stories;
    private final OnStoryClickListener listener;

    public interface OnStoryClickListener {
        void onStoryClick(StoryItem story);
    }

    public StoryAdapter(List<StoryItem> stories, OnStoryClickListener listener) {
        this.stories = stories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        StoryItem story = stories.get(position);
        holder.txtName.setText(story.getName());
        holder.txtAvatar.setText(story.getName().substring(0, 1).toUpperCase());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onStoryClick(story);
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    static class StoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtAvatar, txtName;

        StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAvatar = itemView.findViewById(R.id.txtStoryAvatar);
            txtName = itemView.findViewById(R.id.txtStoryName);
        }
    }
}
