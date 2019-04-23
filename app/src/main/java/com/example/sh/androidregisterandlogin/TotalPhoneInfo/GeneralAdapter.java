package com.example.sh.androidregisterandlogin.TotalPhoneInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sh.androidregisterandlogin.R;

public class GeneralAdapter extends ArrayAdapter<String> {
    Context context;
    String myTitles[];
    String myDescrpitions[];
    GeneralActivity generalActivity =new GeneralActivity();

    GeneralAdapter(Context c, String[] titles, String[] descriptions) {
        super(c, R.layout.tworow, R.id.title, titles);
        this.context = c;
        this.myTitles = titles;
        this.myDescrpitions = descriptions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.tworow, parent, false);
//            vies in tworow.xml
        TextView myTitle = row.findViewById(R.id.titleTv);
        TextView myDescription = row.findViewById(R.id.descTv);
//            set text to views
        myTitle.setText(generalActivity.titles[position]);
        myDescription.setText(generalActivity.descriptions[position]);

        return row;
    }
}
