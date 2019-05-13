package com.example.sh.androidregisterandlogin.TotalApp;

import android.content.Context;

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
import com.example.sh.androidregisterandlogin.TotalDataItem.AppDataItem;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.List;

public class AppAdapter extends BaseRecylcerViewAdapter<AppDataItem, AppAdapter.ViewHolder> {

    Context context;

    public AppAdapter(List<AppDataItem> customizedListView, Context context) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelapps, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDataItem appDataItem = getItem(viewHolder.getAdapterPosition());

                if (appDataItem == null) {
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
        ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageInListView = itemView.findViewById(R.id.app_icon);
            nameInListView = itemView.findViewById(R.id.app_name);
            packageInListView = itemView.findViewById(R.id.app_package);
            versionInListView = itemView.findViewById(R.id.app_version);
            constraintLayout = itemView.findViewById(R.id.ll_main);

        }
    }
}
