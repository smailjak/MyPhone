package com.example.sh.androidregisterandlogin.TotalManage.FragOne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<ItemObject> mList;
    Context context;

    public MyAdapter(ArrayList<ItemObject> list, Context context) {
        this.mList = list;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        holder.tvSupportMoney.setText(String.valueOf(mList.get(position).getRelease()));
        holder.tvBirth.setText(String.valueOf(mList.get(position).getDirector()));
        holder.tvMyModelName.setText(String.valueOf(mList.get(position).getSell_money()));
        holder.tvMyModelShipment.setText(String.valueOf(mList.get(position).getMy_shipment()));
        holder.tvMySellMoney.setText(String.valueOf(mList.get(position).getMy_sell_money()));
        RequestOptions circle = new RequestOptions().circleCrop();
        Glide.with(holder.itemView).load(mList.get(position).getImg_url()).apply(circle).into(holder.ivImagePhone);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImagePhone;
        private TextView textView_title, tvSupportMoney, tvBirth, tvMyModelName, tvMyModelShipment, tvMySellMoney;

        public ViewHolder(View itemView) {
            super(itemView);

            ivImagePhone = itemView.findViewById(R.id.iv_image_phone);
            textView_title = itemView.findViewById(R.id.tv_title);
            tvSupportMoney = itemView.findViewById(R.id.tv_support_money);
            tvBirth = itemView.findViewById(R.id.tv_birth);
            tvMyModelShipment = itemView.findViewById(R.id.tv_my_model_shipment);
            tvMySellMoney = itemView.findViewById(R.id.tv_my_sell_money);
            tvMyModelName = itemView.findViewById(R.id.tv_my_model_name);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
