package com.example.sh.androidregisterandlogin.TotalApp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.List;

public class AppAdapter extends BaseRecylcerViewAdapter<AppList, AppAdapter.ViewHolder> {

    Context context;

    public AppAdapter(List<AppList> customizedListView, Context context) {
        super(customizedListView);
        this.context = context;
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int position) {
        viewHolder.nameInListView.setText(getItem(position).getName());
        viewHolder.packageInListView.setText(getItem(position).getPackages());
        viewHolder.versionInListView.setText(getItem(position).getVersion());
//        viewHolder.imageInListView.setImageDrawable(getItem(position).getIcon());
        RequestOptions circle = new RequestOptions().circleCrop();
        Glide.with(context)
                .load(getItem(position).getIcon())
                .apply(circle)
                .into(viewHolder.imageInListView);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.modelapps, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppList appList = getItem(viewHolder.getAdapterPosition());

                if (appList == null) {
                    return;
                }
            }
        });

        return viewHolder;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //            Our views from modelapps xml
        TextView nameInListView, packageInListView, versionInListView;
        ImageView imageInListView;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageInListView = itemView.findViewById(R.id.app_icon);
            nameInListView = itemView.findViewById(R.id.app_name);
            packageInListView = itemView.findViewById(R.id.app_package);
            versionInListView = itemView.findViewById(R.id.app_version);
            linearLayout = itemView.findViewById(R.id.ll_main);

        }
    }
}
