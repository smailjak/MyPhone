package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.TotalMessage.Chat.ChatActivity;
import com.example.sh.androidregisterandlogin.data.SmsMessage;
import com.example.sh.androidregisterandlogin.databinding.ItemMessageBinding;
import com.example.sh.androidregisterandlogin.util.BaseRecyclerViewAdapter;

import java.util.List;

public class MessageAdapter extends BaseRecyclerViewAdapter<SmsMessage, MessageAdapter.ViewHolder> {
    public MessageAdapter(List<SmsMessage> dataSet) {
        super(dataSet);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.tvUser.setText(getItem(position).getAddress());
        holder.binding.tvContent.setText(getItem(position).getBody());
        holder.binding.tvDate.setText(getItem(position).getTimestamp());

        String firstLetter = String.valueOf(getItem(position).getContactId_string().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(getItem(position));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        holder.binding.ivImage.setImageDrawable(drawable);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        ItemMessageBinding binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);

        viewHolder.binding.conversationListLayout.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(), ChatActivity.class);
            intent.putExtra("name", getItem(viewHolder.getAdapterPosition()).getContactId_string());
            intent.putExtra("address", getItem(viewHolder.getAdapterPosition()).getAddress());
            intent.putExtra("thread_id", getItem(viewHolder.getAdapterPosition()).getThreadId());
            parent.getContext().startActivity(intent);
        });
        return viewHolder;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemMessageBinding binding;

        public ViewHolder(@NonNull ItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

