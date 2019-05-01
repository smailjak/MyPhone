package com.example.sh.androidregisterandlogin.TotalManage;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.R;

public class FragmentOne extends Fragment {

    View view;
    Button image_ai_btn, music_ai_btn;

    public FragmentOne() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image_ai_btn = view.findViewById(R.id.image_ai_btn);
        music_ai_btn = view.findViewById(R.id.music_ai_btn);
        Log.d("fragment_one", "onCreateView: fragment_one");

        image_ai_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "사진관리 버튼입니다..", Toast.LENGTH_SHORT).show();
            }
        });

        music_ai_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "음악 버튼입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
