package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalApp.UserAppsActivity;

import com.example.sh.androidregisterandlogin.TotalBattery.BatteryActivity;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.Model;
import com.example.sh.androidregisterandlogin.TotalMusic.TotalMusicActivity;
import com.example.sh.androidregisterandlogin.TotalPhoneInfo.PhoneInfoActivity;

import java.util.ArrayList;

// 여기서 implements 을 추가해줘야합니다. == > Filterable 을 추가했음
public class FragmentMainAdapter extends RecyclerView.Adapter<FragmentMainAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Model> modelArrayList, filterList;
    MainFilter mainFilter;
    Model model;
    View view;

    public FragmentMainAdapter(Context context, ArrayList<Model> models) {

        this.context = context;
        this.modelArrayList = models;
        this.filterList = models;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
//        Convert XML to OBJ
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);
        ViewHolder holder = new ViewHolder(view);
        holder.constraintLayout.setOnClickListener(v -> {
            if (holder.getAdapterPosition() == 0) {
                Intent intent = new Intent(context, TotalMusicActivity.class);
                context.startActivity(intent);
            } else if (holder.getAdapterPosition() == 1) {
                Intent intent = new Intent(context, BatteryActivity.class);
                context.startActivity(intent);
            } else if (holder.getAdapterPosition() == 2) {
                Intent intent = new Intent(context, UserAppsActivity.class);
                context.startActivity(intent);
            } else if (holder.getAdapterPosition() == 3) {
                Intent intent = new Intent(context, PhoneInfoActivity.class);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTxt.setText(modelArrayList.get(position).getName());
        RequestOptions circle = new RequestOptions().circleCrop();
        Glide.with(context)
                .load(modelArrayList.get(position).getImg())
                .apply(circle)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nameTxt;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_model);
            nameTxt = itemView.findViewById(R.id.tv_model);
            constraintLayout = itemView.findViewById(R.id.constraint_main_model);
        }
    }

    //    return filter obj
//    검색기능을 구현했을때 추가한 함수
    @Override
    public Filter getFilter() {
        if (mainFilter == null) {
            mainFilter = new MainFilter(filterList, this);
        }
        return mainFilter;
    }
}
