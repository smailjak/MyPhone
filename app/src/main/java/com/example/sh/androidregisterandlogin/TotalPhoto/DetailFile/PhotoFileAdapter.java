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
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.ArrayList;


public class PhotoFileAdapter extends BaseRecylcerViewAdapter<File_images, PhotoFileAdapter.ViewHolder> {

    Context context;


    public PhotoFileAdapter(ArrayList<File_images> al_menu, Context context) {
        super(al_menu);
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_photosfile,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }



    @Override
    public void onBindView(ViewHolder viewHolder, int position) {

//        TODO 여기서 에러가 발생 만약에 NAVER 라는 폴더를 클릭했을때는 ,
//           NAVER 내부에 있는 사진들만 보여줘야 하는데 전체 이미지가 출력됨.

         viewHolder.tVFileName.setText(getItem(position).getPhotoName());
        Glide.with(context).load("file://"+getItem(position).getAlFilePath()).into(viewHolder.iVFileImage);
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
