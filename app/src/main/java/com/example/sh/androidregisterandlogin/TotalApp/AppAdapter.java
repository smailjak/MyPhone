package com.example.sh.androidregisterandlogin.TotalApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sh.androidregisterandlogin.R;

import java.util.List;

public class AppAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<AppList> listStorage;

    //        constructor
    AppAdapter(Context context, List<AppList> customizedListView) {
//            layout inflater
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.modelapps, parent, false);
            listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
            listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
            listViewHolder.packageInListView = convertView.findViewById(R.id.app_package);
            listViewHolder.versionInListView = convertView.findViewById(R.id.version);

            convertView.setTag(listViewHolder);

        } else {
            listViewHolder = (ViewHolder) convertView.getTag();

        }

//            set data to our views
        listViewHolder.textInListView.setText(listStorage.get(position).getName());
        listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
        listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());
        listViewHolder.versionInListView.setText(listStorage.get(position).getVersion());

        return convertView; // return the whole view
    }

    class ViewHolder {
        //            Our views from modelapps xml
        TextView textInListView;
        ImageView imageInListView;
        TextView packageInListView;
        TextView versionInListView;

    }
}
