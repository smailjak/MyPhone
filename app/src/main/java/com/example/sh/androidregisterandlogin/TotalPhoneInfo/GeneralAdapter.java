package com.example.sh.androidregisterandlogin.TotalPhoneInfo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalDataItem.PhoneInfoDataItem;
import com.example.sh.androidregisterandlogin.util.BaseRecyclerViewAdapter;

import java.util.ArrayList;

public class GeneralAdapter extends BaseRecyclerViewAdapter<PhoneInfoDataItem, GeneralAdapter.ViewHolder> {
    Context context;


    GeneralAdapter(ArrayList<PhoneInfoDataItem> items, Context context) {
        super(items);
        this.context = context;
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int position) {
        viewHolder.tvTitle.setText(getItem(position).getTitle());
        viewHolder.tvDesc.setText(getItem(position).getContent());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_phoneinfo, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
