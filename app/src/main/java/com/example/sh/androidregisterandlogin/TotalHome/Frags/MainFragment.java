package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.sh.androidregisterandlogin.AdvertisingActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.FragmentMainAdapter;
import com.example.sh.androidregisterandlogin.TotalHome.HomeActivity;
import com.example.sh.androidregisterandlogin.TotalHome.Model;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    View view;
    RecyclerView rcv;
    Toolbar toolbar;
    FragmentMainAdapter fragmentMainAdapter;
    SearchView searchView;

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
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        rcv = view.findViewById(R.id.rcv_main);
        initCollapsingToolbar();
        initRcv();

    }

    private void initRcv() {
        fragmentMainAdapter = new FragmentMainAdapter(getContext(), getModels());
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcv.setLayoutManager(staggeredGridLayoutManager);
        rcv.setHasFixedSize(true);
        rcv.setAdapter(fragmentMainAdapter);

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


    private ArrayList<Model> getModels() {
        ArrayList<Model> models = new ArrayList<>();
//        Model p = new Model();
        Model p;

//        여기서는 setName 에 다가 이름을 입력
        p = new Model();
        p.setName("General");
        p.setImg(R.drawable.app);
        models.add(p);

        p = new Model();
        p.setName("Device Id");
        p.setImg(R.drawable.battery);
        models.add(p);

        p = new Model();
        p.setName("Display");
        p.setImg(R.drawable.music);
        models.add(p);  //now run the project , im checking in nougat and oreo

        p = new Model();
        p.setName("Battery");
        p.setImg(R.drawable.battery);
        models.add(p);

        p = new Model();
        p.setName("User Apps");
        p.setImg(R.drawable.phone);
        models.add(p);

        p = new Model();
        p.setName("System Apps");
        p.setImg(R.drawable.photo_manage);
        models.add(p);

        p = new Model();
        p.setName("Memory");
        p.setImg(R.drawable.total_photo);
        models.add(p);

        p = new Model();
        p.setName("CPU");
        p.setImg(R.drawable.question);
        models.add(p);

        p = new Model();
        p.setName("Sensors");
        p.setImg(R.drawable.dust_clock);
        models.add(p);

        p = new Model();
        p.setName("SIM");
        p.setImg(R.drawable.battery);
        models.add(p);

        return models;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                키보드의 검색 버튼을 누르면 이 함수가 호출됩니다.
                fragmentMainAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                이 함수는 searchview에 입력 할 때마다 호출됩니다.
                fragmentMainAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        다른 메뉴 항목 클릭을 여기에서 처리하십시오.
        if (id == R.id.action_settings) {
            Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_voice) {
            Toast.makeText(getContext(), "Voice", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
