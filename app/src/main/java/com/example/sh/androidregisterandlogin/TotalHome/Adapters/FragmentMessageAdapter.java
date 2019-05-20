package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageModel;
import com.example.sh.androidregisterandlogin.util.BaseRecylcerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentMessageAdapter extends BaseRecylcerViewAdapter<MessageModel, FragmentMessageAdapter.ViewHolder> {

    Context mContext;
    ArrayList<MessageModel> messageFilterList, messageDataItemArrayList;
    private ArrayList<HashMap<String, String>> data;
    MessageFilter messageFilter;
    View view;

    public FragmentMessageAdapter(Context context, ArrayList<MessageModel> messageDataItemArrayList, ArrayList<HashMap<String, String>> data) {
        super(messageDataItemArrayList);
        this.mContext = context;
        this.messageDataItemArrayList = messageDataItemArrayList;
        this.messageFilterList = messageDataItemArrayList;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_list_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public int getCount() {
        return data.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindView(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> song;
        song = data.get(position);
        String firstLetter = String.valueOf(song.get(MessageModel.KEY_NAME).charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(getItem(position));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);

        holder.imgInboxThumb.setImageDrawable(drawable);

        holder.tvInboxUser.setText(song.get(MessageModel.KEY_NAME));
        holder.tvInboxMsg.setText(song.get(MessageModel.KEY_MSG));
        holder.tvInboxDate.setText(song.get(MessageModel.KEY_TIME));

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgInboxThumb;
        TextView tvInboxUser;
        TextView tvInboxMsg;
        TextView tvInboxDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgInboxThumb = itemView.findViewById(R.id.img_inbox_thumb);
            tvInboxUser = itemView.findViewById(R.id.tv_inbox_user);
            tvInboxMsg = itemView.findViewById(R.id.tv_inbox_msg);
            tvInboxDate = itemView.findViewById(R.id.tv_inbox_date);
        }
    }
}
