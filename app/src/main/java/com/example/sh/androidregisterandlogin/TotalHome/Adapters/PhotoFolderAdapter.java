package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.PhotoFolderDataItem;
import com.example.sh.androidregisterandlogin.TotalPhoto.DetailFile.PhotosActivity;
import com.example.sh.androidregisterandlogin.databinding.ItemPhotofolderBinding;
import com.example.sh.androidregisterandlogin.util.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoFolderAdapter extends BaseRecyclerViewAdapter<PhotoFolderDataItem, PhotoFolderAdapter.ViewHolder> {

    Context context;

    public PhotoFolderAdapter(ArrayList<PhotoFolderDataItem> al_menu, Context context) {
        super(al_menu);
        this.context = context;
    }

    @Override
    public void setfileter(List<PhotoFolderDataItem> listitem) {
        super.setfileter(listitem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        ItemPhotofolderBinding binding = ItemPhotofolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding);
        binding.adapterPhotoLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, PhotosActivity.class);
            context.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {

        holder.binding.tvFolderName.setText(getItem(position).getStr_folder());
        holder.binding.tvFolderCount.setText(Integer.toString(getItem(position).getAl_imagepath().size()));
        RequestOptions circleOptions = new RequestOptions().circleCrop();
        Glide.with(context).load("file://" + getItem(position)
                .getAl_imagepath().get(0)).apply(circleOptions)
                .into(holder.binding.ivImage);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemPhotofolderBinding binding;

        public ViewHolder(ItemPhotofolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
