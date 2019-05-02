package com.example.sh.androidregisterandlogin.TotalManage.FragTwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.ArrayList;

public class ManageAdapter extends BaseRecylcerViewAdapter<RecyclerItem, ManageAdapter.ViewHolder>  {

    private ArrayList<RecyclerItem> arrayList;
    View view;
    Context context;

    //여기서 context 를 변수선언
    public ManageAdapter(Context context, ArrayList<RecyclerItem> dataSet) {
        super(dataSet);
        this.arrayList = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ManageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_two_rcv_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerItem recyclerItem = getItem(viewHolder.getAdapterPosition());
                Toast.makeText(context, recyclerItem.getTextView()+"입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int position) {
        viewHolder.titleTitle.setText(arrayList.get(position).getTextView());
        viewHolder.imageTitle.setImageResource((int) arrayList.get(position).getImageView());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTitle;
        ImageView imageTitle;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTitle = itemView.findViewById(R.id.fragment_rcv_item_txt);
            imageTitle = itemView.findViewById(R.id.fragment_rcv_item_image);
            linearLayout = itemView.findViewById(R.id.fragment_ll);
        }
    }
}