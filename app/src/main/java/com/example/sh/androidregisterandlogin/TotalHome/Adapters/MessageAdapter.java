package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageModel;
import com.example.sh.androidregisterandlogin.databinding.ItemMessageBinding;
import com.example.sh.androidregisterandlogin.util.BaseRecyclerViewAdapter;

import java.util.HashMap;
import java.util.List;

public class MessageAdapter extends BaseRecyclerViewAdapter<HashMap<String, String>, MessageAdapter.ViewHolder> {
    public MessageAdapter(List<HashMap<String, String>> dataSet) {
        super(dataSet);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.tvUser.setText(getItem(position).get(MessageModel.KEY_NAME));
        holder.binding.tvContent.setText(getItem(position).get(MessageModel.KEY_MSG));
        holder.binding.tvDate.setText(getItem(position).get(MessageModel.KEY_TIME));

        String firstLetter = String.valueOf(getItem(position).get(MessageModel.KEY_NAME).charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(getItem(position));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        holder.binding.ivImage.setImageDrawable(drawable);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);

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
