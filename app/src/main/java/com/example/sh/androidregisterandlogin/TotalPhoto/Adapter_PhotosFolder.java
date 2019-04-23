package com.example.sh.androidregisterandlogin.TotalPhoto;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sh.androidregisterandlogin.R;
import java.util.ArrayList;

public class Adapter_PhotosFolder extends ArrayAdapter<Model_images> {

    Context adapterContext;
    ViewHolder viewHolder;
    ArrayList<Model_images> al_menu ;

    public Adapter_PhotosFolder(Context context, ArrayList<Model_images> al_menu) {
        super(context, R.layout.adapter_photosfolder, al_menu);
        this.al_menu = al_menu;
        this.adapterContext = context;

    }

    @Override
    public int getCount() {
        Log.e("ADAPTER LIST SIZE", al_menu.size() + "");
        return al_menu.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.size() > 0) {
            return al_menu.size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_photosfolder, parent, false);

            viewHolder.tv_foldern = (TextView) convertView.findViewById(R.id.tv_folder);    // 사진이 저장되어 있는 폴더 이름
            viewHolder.tv_foldersize = (TextView) convertView.findViewById(R.id.tv_folder2);    // 사진이 저장되어 있는 갯수
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);      // 사진 폴더를 보여줄때 가장 처음 보여줄 이미지

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_foldern.setText(al_menu.get(position).getStr_folder());
        viewHolder.tv_foldersize.setText(Integer.toString(al_menu.get(position).getAl_imagepath().size()));
        Glide.with(adapterContext).load("file://" + al_menu.get(position).getAl_imagepath().get(0))
        //이미지 중에서 가장 처음에 저장되어있는 사진을 불러오게 됩니다.==> 경로는 모든경로에서 찾게됩니다.
        //Glide 가 뭐지 ? == > 이미지 로딩 및 관리 오픈 소스 라이브러리 입니다.
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //diskCacheStrategy 는 디스크 캐시입니다. Glide 는 기본적으로 디스크 캐싱을 수행합니다.
                //DiskCacheStrategy.NONE : 디스크 캐싱을 하지 않는다. 라는 말입니다.
                .skipMemoryCache(true)
                //메모리 캐싱을 끄려면 skipMemoryCache(true)를 호출한다.
                .into(viewHolder.iv_image);
                //이미지를 보여줄 곳
        return convertView;
    }

    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;
    }
}
