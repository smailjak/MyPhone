package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.AddressDataItem;
import com.example.sh.androidregisterandlogin.databinding.ItemPhonebookBinding;
import com.example.sh.androidregisterandlogin.util.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhonebookAdapter extends BaseRecyclerViewAdapter<AddressDataItem, PhonebookAdapter.ViewHolder> {


    public PhonebookAdapter( ArrayList<AddressDataItem> dataSet) {
        super(dataSet);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.tvName.setText(getItem(position).getName());
        holder.binding.tvPhoneNumber.setText(getItem(position).getPhonenum());

        String firstLetter = String.valueOf(getItem(position).getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(getItem(position));
        TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color);
        holder.binding.ivPhoto.setImageDrawable(drawable);
    }

    @Override
    public void setfileter(List<AddressDataItem> listitem) {
        super.setfileter(listitem);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        ItemPhonebookBinding binding = ItemPhonebookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        final ViewHolder viewHolder = new ViewHolder(binding);
        binding.constraintMain.setOnClickListener(v -> {
            AddressDataItem phonenumber = getItem(viewHolder.getAdapterPosition());
            if (phonenumber == null) {
                return;
            }
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonenumber.getPhonenum()));
            parent.getContext().startActivity(intent);
        });
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // RecyclerView 라는클래스 안에 ViewHolder를 상속받음
        ItemPhonebookBinding binding;

        ViewHolder(ItemPhonebookBinding binding) {
            super(binding.getRoot()); //getRoot() 메소드를 사용하는 이유는 view 라는 return
            this.binding = binding;
        }
    }
}
