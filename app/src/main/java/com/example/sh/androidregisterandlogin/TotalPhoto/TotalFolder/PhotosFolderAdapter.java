package com.example.sh.androidregisterandlogin.TotalPhoto.TotalFolder;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalDataItem.PhotoFolderDataItem;
import com.example.sh.androidregisterandlogin.TotalPhoto.DetailFile.PhotosActivity;
import com.example.sh.androidregisterandlogin.databinding.LayoutPhonelistBinding;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.ArrayList;

public class PhotosFolderAdapter extends BaseRecylcerViewAdapter<PhotoFolderDataItem, PhotosFolderAdapter.ViewHolder> {
    Context context;

    public PhotosFolderAdapter(ArrayList<PhotoFolderDataItem> al_menu, Context context) {
        super(al_menu);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photosfolder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
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


        viewHolder.tvFolderName.setText(getItem(position).getStr_folder());
        viewHolder.tvFolderCount.setText(Integer.toString(getItem(position).getAl_imagepath().size()));
        RequestOptions circleOptions = new RequestOptions().circleCrop();
        Glide.with(context).load("file://" + getItem(position)
                        .getAl_imagepath().get(0)).apply(circleOptions)
                        .into(viewHolder.ivImage);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFolderName, tvFolderCount;
        ConstraintLayout constraintLayout;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFolderName = itemView.findViewById(R.id.tv_folder_name);
            tvFolderCount = itemView.findViewById(R.id.tv_folder_count);
            ivImage = itemView.findViewById(R.id.iv_image);
            constraintLayout = itemView.findViewById(R.id.adapter_photo_layout);
        }
    }
}
