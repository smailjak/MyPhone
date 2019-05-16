package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageModel;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentMessageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> data;

    public FragmentMessageAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
    }

    public int getCount() {
//        여기가 12 로 나옴 .
        Log.d("SmsActivity.qwe", "getCount : " + data.size());
        return data.size();

    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        InboxViewHolder holder;
        if (convertView == null) {

            holder = new InboxViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.conversation_list_item, parent, false);

            holder.inbox_thumb = convertView.findViewById(R.id.img_inbox_thumb);
            holder.inbox_user = convertView.findViewById(R.id.tv_inbox_user);
            holder.inbox_msg = convertView.findViewById(R.id.tv_inbox_msg);
            holder.inbox_date = convertView.findViewById(R.id.tv_inbox_date);
            convertView.setTag(holder);

        } else {
            holder = (InboxViewHolder) convertView.getTag();
        }

        holder.inbox_thumb.setId(position);
        holder.inbox_user.setId(position);
        holder.inbox_msg.setId(position);
        holder.inbox_date.setId(position);

        HashMap<String, String> song;
        song = data.get(position);

        try {
            holder.inbox_user.setText(song.get(MessageModel.KEY_NAME));
            holder.inbox_msg.setText(song.get(MessageModel.KEY_MSG));
            holder.inbox_date.setText(song.get(MessageModel.KEY_TIME));

            String firstLetter = String.valueOf(song.get(MessageModel.KEY_NAME).charAt(0));
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(getItem(position));
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color);
            holder.inbox_thumb.setImageDrawable(drawable);


        } catch (Exception e) {
        }
        return convertView;
    }

    class InboxViewHolder {
        ImageView inbox_thumb;
        TextView inbox_user, inbox_msg, inbox_date;
    }

}
