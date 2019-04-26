package com.example.sh.androidregisterandlogin.TotalPhoto.DetailFile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.ArrayList;


public class PhotoFileAdapter extends BaseRecylcerViewAdapter<File_images, PhotoFileAdapter.ViewHolder> {

    Context context;


    public PhotoFileAdapter(ArrayList<File_images> al_menu, Context context) {
        super(al_menu);
        this.context = context;
    }

//    @Override
//    public int getCount() {
//
//        Log.e("ADAPTER LIST SIZE", String.valueOf(al_menu.get(int_position).getAl_imagepath().size()));
//        return al_menu.get(int_position).getAl_imagepath().size();
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_photosfile,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }



    @Override
    public void onBindView(ViewHolder viewHolder, int position) {

//        여기서 에러가 발생함..
        viewHolder.tVFileName.setText((getItem(position).getAlFilePath().get(position)));
        Glide.with(context).load("file://"+getItem(position).getAlFilePath()).into(viewHolder.iVFileImage);
    }


//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//
//            viewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_photosfolder, parent, false);
//            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
//
//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
////     76  여기서 에러가 발생  == > NullPointerException
//        viewHolder.tv_foldern.setVisibility(View.GONE);
//        viewHolder.tv_foldersize.setVisibility(View.GONE);
//        Glide.with(context).load("file://" + al_menu.get(int_position).getAl_imagepath().get(position))
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                .apply(RequestOptions.skipMemoryCacheOf(true))
//                .into(viewHolder.iv_image);
//        return convertView;
//    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVFileName;
        ImageView iVFileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tVFileName = itemView.findViewById(R.id.tv_file_name);
            iVFileImage = itemView.findViewById(R.id.iv_file_image);

        }
    }
}
