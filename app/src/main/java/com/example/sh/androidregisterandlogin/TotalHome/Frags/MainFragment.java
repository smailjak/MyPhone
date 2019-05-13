package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalManage.FragOne.MyAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MainFragment extends Fragment {

    View view;
    RecyclerView rcv;
    Context context;
    Toolbar toolbar;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        rcv = view.findViewById(R.id.rcv_main);
        initCollapsingToolbar();
    }


    private void initCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
//        축소 된 툴바 제목 설정
//        상단 이미지 밑에 Myphone 이라고 적히는 부분입니다 .사실 이부분을 제외 시켜도 무방하긴 합니다.
        collapsingToolbarLayout.setTitle("U Soft Company");
    }

}
