package com.example.sh.androidregisterandlogin.TotalManage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh.androidregisterandlogin.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    private ArrayList<RecyclerItem> arrayList;
    View view;

    public RecyclerAdapter(Context context, ArrayList<RecyclerItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_two_rcv_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTitle.setText(arrayList.get(position).getTextView());
        holder.imageTitle.setImageResource((int) arrayList.get(position).getImageView());
    }


    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTitle;
        public ImageView imageTitle;
        public RecyclerItem item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTitle = itemView.findViewById(R.id.fragment_rcv_item_txt);
            imageTitle = itemView.findViewById(R.id.fragment_rcv_item_image);
        }
    }
}
