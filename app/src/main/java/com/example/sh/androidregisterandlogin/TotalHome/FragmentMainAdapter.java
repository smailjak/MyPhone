package com.example.sh.androidregisterandlogin.TotalHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh.androidregisterandlogin.R;

import java.util.ArrayList;

// 여기서 implements 을 추가해줘야합니다. == > Filterable 을 추가했음
public class FragmentMainAdapter extends RecyclerView.Adapter<FragmentMainAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Model> modelArrayList, filterList;
    CustomFilter filter;
    View view;

    public FragmentMainAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.modelArrayList = models;
        this.filterList = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Convert XML to OBJ
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTxt.setText(modelArrayList.get(position).getName());
        holder.img.setImageResource(modelArrayList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nameTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_model);
            nameTxt = itemView.findViewById(R.id.modelTxt);
        }
    }

    //    return filter obj
//    검색기능을 구현했을때 추가한 함수
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }
        return filter;
    }
}
