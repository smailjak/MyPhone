package com.example.sh.androidregisterandlogin.TotalPhoto;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.ArrayList;

public class PhotosFolderAdapter extends BaseRecylcerViewAdapter<Model_images, PhotosFolderAdapter.ViewHolder> {
    Context context;

    public PhotosFolderAdapter(ArrayList<Model_images> al_menu, Context context) {
        super(al_menu);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_photosfolder, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.llAdapterPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotosActivity.class);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindView(ViewHolder viewHolder, int position) {
        viewHolder.tvFolderName.setText(String.valueOf(getItem(position).getAl_imagepath()));
        viewHolder.tvFolderCount.setText(getItem(position).getStr_folder());
        Glide.with(context).load("file://" + getItem(position)
                        .getAl_imagepath().get(0))
                        .into(viewHolder.ivImage);

    }
//sdfsdfasdf


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFolderName, tvFolderCount;
        LinearLayout llAdapterPhoto;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFolderName = itemView.findViewById(R.id.tv_folder_name);
            tvFolderCount = itemView.findViewById(R.id.tv_folder_count);
            ivImage = itemView.findViewById(R.id.iv_image);
            llAdapterPhoto = itemView.findViewById(R.id.adapter_photo_layout);
        }
    }
}
