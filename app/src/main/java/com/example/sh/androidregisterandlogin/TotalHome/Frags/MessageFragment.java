package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Adapters.MessageAdapter;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageComparator;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageModel;
import com.example.sh.androidregisterandlogin.databinding.FragmentMessageBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lifeofcoding.cacheutlislibrary.CacheUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class MessageFragment extends Fragment {
    private FragmentMessageBinding binding;
    static final int REQUEST_PERMISSION_KEY = 1;
    private final String WATING_GREETINGS = "please wating ~ ^ ^ ";
    ArrayList<HashMap<String, String>> smsList = new ArrayList<>();
    MessageAdapter adapter;


    int name_count = 0;
    int null_name_count = 0;

    private ProgressDialog progressDialog;
    private LoadSmsAsyncTask loadSmsAsyncTask = new LoadSmsAsyncTask();

    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, parent, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);
        initMessageRcv(binding.rcvMsg);
        loadSmsAsyncTask.execute();
    }

    private void initMessageRcv(RecyclerView rcv) {
        adapter = new MessageAdapter(smsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setHasFixedSize(true);
        rcv.setAdapter(adapter);
    }

    private void initCollapsingToolbar(CollapsingToolbarLayout ctl) {
        ctl.setTitle("");
        binding.appbar.setExpanded(true);
        ctl.setTitle("메세지 개수 : " + smsList.size());
        ctl.setCollapsedTitleTextAppearance(R.style.coll_basic_title);
        ctl.setExpandedTitleTextAppearance(R.style.coll_expand_title);
    }

    public class LoadSmsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            smsList.clear();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(WATING_GREETINGS);
            progressDialog.show();
        }

        protected String doInBackground(String... args) {
            String xml = "";
            try {
                Uri uriInbox = Uri.parse("content://sms/inbox");
                Cursor inbox = getContext().getContentResolver().query(uriInbox, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
                Uri uriSent = Uri.parse("content://sms/sent");
                Cursor sent = getContext().getContentResolver().query(uriSent, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
                Cursor cursor = new MergeCursor(new Cursor[]{inbox, sent}); // Attaching inbox and sent sms
                Log.d("MainActivity.test11", "c.getCount() : " + cursor.getCount()); // 내가 보냈었던 메세지 갯수 .

                if (cursor.moveToFirst()) {

                    for (int i = 0; i < cursor.getCount(); i++) {
                        String name = "";
                        String phone = "";

                        String _id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                        String thread_id = cursor.getString(cursor.getColumnIndexOrThrow("thread_id"));
                        String msg = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                        String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                        String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                        phone = cursor.getString(cursor.getColumnIndexOrThrow("address"));

                        name = CacheUtils.readFile(thread_id);
                        name_count++;

                        if (name == null) {
                            null_name_count++;
                            name = MessageModel.getContactbyPhoneNumber(getActivity(), cursor.getString(cursor.getColumnIndexOrThrow("address")));
                            CacheUtils.writeFile(thread_id, name);
                        }
                        smsList.add(MessageModel.mappingInbox(_id, thread_id, name, phone, msg, type, timestamp, MessageModel.converToTime(timestamp)));
                        cursor.moveToNext();
                    }
                }
                cursor.close();

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            Collections.sort(smsList, new MessageComparator(MessageModel.KEY_TIMESTAMP, "dsc")); // Arranging sms by timestamp decending

            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {
            progressDialog.dismiss();
            initCollapsingToolbar(binding.collapsingToolbar);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getContext(), "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        changeSearchViewTextColor(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                item.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<HashMap<String, String>> filtermodellist = filter(smsList, newText);
                adapter.setfileter(filtermodellist);
                return false;
            }
        });
    }

    private List<HashMap<String, String>> filter(List<HashMap<String, String>> p1, String query) {
        query = query.toLowerCase();
        final List<HashMap<String, String>> filteredModelList = new ArrayList<>();
        for (HashMap<String, String> model : p1) {
            final String text = model.get(MessageModel.KEY_NAME).toLowerCase();
            if (text.startsWith(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.BLACK);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] PERMISSIONS = {Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        if (!MessageModel.hasPermissions(getContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_PERMISSION_KEY);
        }
    }


    @Override
    public void onDestroy() {
        loadSmsAsyncTask.cancel(true);
        super.onDestroy();
    }
}
