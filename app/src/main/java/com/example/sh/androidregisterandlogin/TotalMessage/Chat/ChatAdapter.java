package com.example.sh.androidregisterandlogin.TotalMessage.Chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ChatAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatViewHolder holder = null;
        if (convertView == null) {
            holder = new ChatViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.chat_item, parent, false);

            holder.txtMsgYou =  convertView.findViewById(R.id.txtMsgYou);
            holder.lblMsgYou =  convertView.findViewById(R.id.lblMsgYou);
            holder.timeMsgYou =  convertView.findViewById(R.id.timeMsgYou);
            holder.lblMsgFrom =  convertView.findViewById(R.id.lblMsgFrom);
            holder.timeMsgFrom =  convertView.findViewById(R.id.timeMsgFrom);
            holder.txtMsgFrom =  convertView.findViewById(R.id.txtMsgFrom);
            holder.msgFrom = (LinearLayout) convertView.findViewById(R.id.msgFrom);
            holder.msgYou = (LinearLayout) convertView.findViewById(R.id.msgYou);

            convertView.setTag(holder);
        } else {
            holder = (ChatViewHolder) convertView.getTag();
        }
        holder.txtMsgYou.setId(position);
        holder.lblMsgYou.setId(position);
        holder.timeMsgYou.setId(position);
        holder.lblMsgFrom.setId(position);
        holder.timeMsgFrom.setId(position);
        holder.txtMsgFrom.setId(position);
        holder.msgFrom.setId(position);
        holder.msgYou.setId(position);

        HashMap<String, String> song;
        song = data.get(position);
        try {
            if (song.get(MessageModel.KEY_TYPE).contentEquals("1")) {
                holder.lblMsgFrom.setText(song.get(MessageModel.KEY_NAME));
                holder.txtMsgFrom.setText(song.get(MessageModel.KEY_MSG));
                holder.timeMsgFrom.setText(song.get(MessageModel.KEY_TIME));
                holder.msgFrom.setVisibility(View.VISIBLE);
                holder.msgYou.setVisibility(View.GONE);
            } else {
                holder.lblMsgYou.setText("You");
                holder.txtMsgYou.setText(song.get(MessageModel.KEY_MSG));
                holder.timeMsgYou.setText(song.get(MessageModel.KEY_TIME));
                holder.msgFrom.setVisibility(View.GONE);
                holder.msgYou.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
        }
        return convertView;
    }
    class ChatViewHolder {
        LinearLayout msgFrom, msgYou;
        TextView txtMsgYou, lblMsgYou, timeMsgYou, lblMsgFrom, txtMsgFrom, timeMsgFrom;
    }
}
