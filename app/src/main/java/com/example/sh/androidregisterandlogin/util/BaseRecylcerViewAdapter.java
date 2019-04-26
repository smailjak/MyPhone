package com.example.sh.androidregisterandlogin.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecylcerViewAdapter <T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> dataSet;

    public BaseRecylcerViewAdapter(List<T> dataSet) {this.dataSet = dataSet;}

    public T getItem(int position) {
        return dataSet == null ? null : dataSet.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        onBindView((VH) viewHolder, position);
    }

    abstract public void onBindView(VH viewHolder, int position);

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }
}
