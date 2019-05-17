package com.example.sh.androidregisterandlogin.TotalManage.FragOne;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.nodes.Element;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalDataItem.FragOneDataItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentOne extends Fragment {

    View view;
    private final String WATING_GREETINGS = "please wating ~ ^ ^ ";
    private ArrayList<FragOneDataItem> list = new ArrayList();
    RecyclerView recyclerView;
    Context context;
    ProgressDialog progressDialog;

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
        new AsyncTaskTest().execute();
    }

    class AsyncTaskTest extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(WATING_GREETINGS);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://www.uplussave.com/dev/lawList.mhp").get();
                Elements mElementDataSize = doc.select("tbody").select("tr");

                for (Element elem : mElementDataSize) {
                    String totalData = elem.select("td").text(); //
                    String myTitle = elem.select("tr p[class=phoneName]").text();
                    String myImgUrl = elem.select("tr p[class=phoneImg] img").attr("src");
                    String myGosi = "공시지원금" + elem.select("td span[class=point_color01]").text();
                    String myBirthday = "공시일 :" + elem.select("td span[class=color_gy8]").text();
                    String myModel_name = "모델명 : " + elem.select("td").next().first().text();
                    String myShipment = "출고가 : " + elem.select("td").next().next().first().text();
                    String mySellMoney = "판매가 : " + elem.select("td").next().next().next().next().first().text();

                    list.add(new FragOneDataItem(myTitle, myImgUrl, myGosi, myBirthday, myModel_name, myShipment, mySellMoney));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            TotalManageFragmentAdapter totalManageFragmentAdapter = new TotalManageFragmentAdapter(list, view.getContext());
            recyclerView = view.findViewById(R.id.rcv_fragment_one);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(totalManageFragmentAdapter);
            progressDialog.dismiss();

        }
    }
}
