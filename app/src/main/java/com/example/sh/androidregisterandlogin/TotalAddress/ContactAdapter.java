package com.example.sh.androidregisterandlogin.TotalAddress;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;
import java.util.ArrayList;

public class ContactAdapter extends BaseRecylcerViewAdapter<Contact, ContactAdapter.ViewHolder> {
    Context context;

    public ContactAdapter(ArrayList<Contact> dataSet, Context context) {
        super(dataSet);
        this.context = context;
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int position) {
        viewHolder.tv_name.setText(getItem(position).getName());
        viewHolder.tv_phoneNumber.setText(getItem(position).getPhonenum());

        String firstLetter = String.valueOf(getItem(position).getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(getItem(position));
        TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color);
        viewHolder.iv_photo.setImageDrawable(drawable);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_phonelist,viewGroup,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact phonenumber = getItem(viewHolder.getAdapterPosition());

                if (phonenumber == null) {
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonenumber.getPhonenum()));
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_photo;
        TextView tv_name , tv_phoneNumber;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
            linearLayout = itemView.findViewById(R.id.ll_main);
        }
    }
}
