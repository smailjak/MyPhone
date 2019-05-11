package com.example.sh.androidregisterandlogin.TotalAddress;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.TotalDataItem.AddressDataItem;
import com.example.sh.androidregisterandlogin.databinding.LayoutPhonelistBinding;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.ArrayList;

public class ContactAdapter extends BaseRecylcerViewAdapter<AddressDataItem, ContactAdapter.ViewHolder> {

    public ContactAdapter(ArrayList<AddressDataItem> dataSet) {
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int position) {
        LayoutPhonelistBinding binding = LayoutPhonelistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

    static class ViewHolder extends RecyclerView.ViewHolder { // RecyclerView 라는클래스 안에 ViewHolder를 상속받음
        LayoutPhonelistBinding binding;

        ViewHolder(LayoutPhonelistBinding binding) {
            super(binding.getRoot()); //getRoot() 메소드를 사용하는 이유는 view 라는 retu
            this.binding = binding;
        }
    }
}
