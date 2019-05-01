package com.example.sh.androidregisterandlogin.TotalManage;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sh.androidregisterandlogin.R;

import java.util.ArrayList;

public class FragmentTwo extends Fragment {

    View view;
    RecyclerView rcv;
    Context context;

    public FragmentTwo() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("onCreateView", "onCreateView: 여기 안오니 ???");
        view = inflater.inflate(R.layout.fragment_two,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<RecyclerItem> arrayList = new ArrayList<>();
        arrayList.add(new RecyclerItem(R.drawable.app,"아이템1"));
        arrayList.add(new RecyclerItem(R.drawable.battery,"아이템2"));
        arrayList.add(new RecyclerItem(R.drawable.cho_fine_dust,"아이템3"));
        arrayList.add(new RecyclerItem(R.drawable.music,"아이템4"));
        arrayList.add(new RecyclerItem(R.drawable.ic_email,"아이템5"));
        arrayList.add(new RecyclerItem(R.drawable.mainphoto,"아이템6"));
        ManageAdapter manageAdapter = new ManageAdapter(getContext(),arrayList);
        rcv = view.findViewById(R.id.fragment_two_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(context));
        rcv.setHasFixedSize(true);
        rcv.setAdapter(manageAdapter);
    }
}
