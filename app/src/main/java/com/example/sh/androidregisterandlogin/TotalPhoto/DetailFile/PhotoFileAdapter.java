package com.example.sh.androidregisterandlogin.TotalPhoto.DetailFile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.util.BaseRecyclerViewAdapter;

import java.util.ArrayList;


public class PhotoFileAdapter extends BaseRecyclerViewAdapter<File_images, PhotoFileAdapter.ViewHolder> {

    Context context;

    public PhotoFileAdapter(ArrayList<File_images> al_menu, Context context) {
        super(al_menu);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photosfile, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {

        holder.tVFileName.setText(getItem(position).getPhotoName());

        RequestOptions circleOptions = new RequestOptions().circleCrop();
        Glide.with(context)
                .load("file://" + getItem(position)
                        .getAlFilePath())
                .apply(circleOptions)
                .into(holder.iVFileImage);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iVFileImage;
        TextView tVFileName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVFileName = itemView.findViewById(R.id.tv_file_name);
            iVFileImage = itemView.findViewById(R.id.iv_file_image);
        }
    }
}
