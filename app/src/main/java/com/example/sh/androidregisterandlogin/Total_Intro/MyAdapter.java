package com.example.sh.androidregisterandlogin.Total_Intro;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalMessage.Sms.SmsActivity;
import com.example.sh.androidregisterandlogin.TotalAddress.ContactListActivity;
import com.example.sh.androidregisterandlogin.TotalAudio.TotalMusicActivity;
import com.example.sh.androidregisterandlogin.TotalPhoto.TotalPhotoActivity;

import java.util.List;

public class MyAdapter extends PagerAdapter {


    Context context;
    List<Movie> movieList;

    public MyAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        //Inflate view
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, container, false);
        //View
        final ImageView movie_image = (ImageView) view.findViewById(R.id.movie_image);

        movie_image.setImageResource(movieList.get(position).getImage());

        //Set Event Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Click to view , here you can add startActivity
                //I just use Toast
                Toast.makeText(context, "" + movieList.get(position).getName(), Toast.LENGTH_SHORT).show();

//                if(movieList.get(position) == movieList.get(0)){
//                    Intent intent = new Intent(context,TotalPhotoActivity.class);
//                    context.startActivity(intent);
//                }
                if (position == 0) {
                    Intent intent = new Intent(context, TotalPhotoActivity.class);
//                   new Intent 하고 context 만 해도 되는이유는 ??
//                    context 자체가 액티비티니깐 !! == > 원래 적을때는 MainActivity.this 이렇게했지만 context 해도 됨
                    context.startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(context, ContactListActivity.class);
                    context.startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(context, SmsActivity.class);
                    context.startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(context, TotalMusicActivity.class);
                    context.startActivity(intent);
                }
            }
        });

//        btn_fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Button clicked ! Your Favorite film ", Toast.LENGTH_SHORT).show();
//            }
//        });

        container.addView(view);
        return view;
    }
}
