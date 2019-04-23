package com.example.sh.androidregisterandlogin.TotalMessage.Sms;

import android.app.Activity;
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
import com.example.sh.androidregisterandlogin.TotalMessage.Function;

import java.util.ArrayList;
import java.util.HashMap;

class InboxAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public InboxAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
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
        InboxViewHolder holder = null;
        if (convertView == null) {

            holder = new InboxViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.conversation_list_item, parent, false);

            holder.inbox_thumb = (ImageView) convertView.findViewById(R.id.inbox_thumb);
            holder.inbox_user = (TextView) convertView.findViewById(R.id.inbox_user);
            holder.inbox_msg = (TextView) convertView.findViewById(R.id.inbox_msg);
            holder.inbox_date = (TextView) convertView.findViewById(R.id.inbox_date);
            convertView.setTag(holder);

        } else {
            holder = (InboxViewHolder) convertView.getTag();
        }

        holder.inbox_thumb.setId(position);
        holder.inbox_user.setId(position);
        holder.inbox_msg.setId(position);
        holder.inbox_date.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try {
            holder.inbox_user.setText(song.get(Function.KEY_NAME));
            holder.inbox_msg.setText(song.get(Function.KEY_MSG));
            holder.inbox_date.setText(song.get(Function.KEY_TIME));

            String firstLetter = String.valueOf(song.get(Function.KEY_NAME).charAt(0));
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(getItem(position));
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color);
            holder.inbox_thumb.setImageDrawable(drawable);
        } catch (Exception e) {
        }
        return convertView;
    }
}
