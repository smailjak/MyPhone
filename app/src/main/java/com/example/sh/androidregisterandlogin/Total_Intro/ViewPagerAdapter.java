package com.example.sh.androidregisterandlogin.Total_Intro;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sh.androidregisterandlogin.R;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images ={R.drawable.intro1,R.drawable.intro2,R.drawable.intro3,R.drawable.intro4,R.drawable.intro5};



    public ViewPagerAdapter(Context context){
        this.context = context;
    }

//    이미지에 대한 사이즈
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_app_custom,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ViewPagerAdapter.qwe","click");
                Intent intent = new Intent(context,Detail_intro.class);
//               내가 만든것 .
//               그리고 내가 만들지 않은것 어떻게 돌아가는지 반드시 알아야한다 .
//                StartActivity는 context 에서 갖고있는 녀석이다 .
//                 Context는 뭘할 수 있냐면은 == > 내 ID 전달이 가능하다 .
//                App ID 전달이 가능하고 System API 를 사용할 할 수 있다.
//                그리고 주변 정보 나 상황정보들을 갖고 있다. 앱상황정보나 주변정보를 갖고있다.
//                Context 는 2가지 정보를 가지고있습니다.
//                한가지는 ApplicationContext 그리고 ActivityContext 를 가지고있다.
//                ApplicationContext 는 어플리케이션의 상황정보
//                Activity sms
//                그리고 콘택스트 열어보면 엄청나게 많다.
//                Class ABC 라고 하면
//                Oncreate 밑에다가 Context = this ; 라고 많이들 적는다 .
//                그럼 그게 뭐냐 ?? this 는 뭐지 ?? ==> 함수 밖에있는것이다.
//                그럼 this가 뜻하는것은 ABC를 뜻하게 되는것이고 ABC 는 extends 로 상속 AppCompat 을받는다 이것은 액티비티니깐
//                결국 Context는 액티비티이다 .
//                결국 우리가 startActivity 를 사용할 수 있는 이유는 상속을 받았으니깐 사용할 수 가 있다 .
                context.startActivity(intent);
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        아마 아이템이 없을경우 인듯 합니다.
        ViewPager vp = (ViewPager) container;
        View view = (View)object;
        vp.removeView(view);
    }
}
