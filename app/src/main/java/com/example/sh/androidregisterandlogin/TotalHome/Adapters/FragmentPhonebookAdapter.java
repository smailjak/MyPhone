package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.TotalDataItem.AddressDataItem;
import com.example.sh.androidregisterandlogin.databinding.LayoutPhonelistBinding;
import com.example.sh.androidregisterandlogin.util.BaseRecyclerViewAdapter;

import java.util.ArrayList;

public class FragmentPhonebookAdapter extends BaseRecyclerViewAdapter<AddressDataItem, FragmentPhonebookAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<AddressDataItem> addressFilterList, addressDataItemArrayList;
    PhoneBookFilter phoneBookFilter;
    View view;

    public FragmentPhonebookAdapter(Context context, ArrayList<AddressDataItem> dataSet) {
        super(dataSet);
        this.context = context;
        this.addressFilterList = dataSet;
        this.addressDataItemArrayList = dataSet;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder { // RecyclerView 라는클래스 안에 ViewHolder를 상속받음
        LayoutPhonelistBinding binding;

        ViewHolder(LayoutPhonelistBinding binding) {
            super(binding.getRoot()); //getRoot() 메소드를 사용하는 이유는 view 라는 return
            this.binding = binding;
        }
    }
    //    return filter obj
//    검색기능을 구현했을때 추가한 함수
    @Override
    public Filter getFilter() {
        if (phoneBookFilter == null) {
            phoneBookFilter = new PhoneBookFilter(addressFilterList,this);
        }
        return phoneBookFilter;
    }
}
