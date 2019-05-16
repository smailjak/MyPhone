package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Adapters.FragmentMessageAdapter;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageModel;
import com.example.sh.androidregisterandlogin.TotalMessage.Chat.ChatActivity;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageComparator;
import com.example.sh.androidregisterandlogin.databinding.FragmentMessageBinding;
import com.lifeofcoding.cacheutlislibrary.CacheUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class MessageFragment extends Fragment {

    View view;
    FragmentMessageBinding fragmentMessageBinding;
    static final int REQUEST_PERMISSION_KEY = 1;
    ArrayList<HashMap<String, String>> smsList = new ArrayList<>();
    ArrayList<HashMap<String, String>> tmpList = new ArrayList<>();
    LoadSmsAsyncTask loadSmsAsyncTask = new LoadSmsAsyncTask();
    FragmentMessageAdapter adapter, tmpadapter;

    int name_count = 0;
    int null_name_count = 0;

    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        fragmentMessageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, parent, false);
        return fragmentMessageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(fragmentMessageBinding.toolbar);
        fragmentMessageBinding.listView.setEmptyView(fragmentMessageBinding.loadingBar);
        setHasOptionsMenu(true);
        initCollapsingToolbar();
        loadSmsAsyncTask.execute();
    }

    private void initCollapsingToolbar() {
        fragmentMessageBinding.collapsingToolbar.setTitle("");
        fragmentMessageBinding.appbar.setExpanded(true);
        fragmentMessageBinding.collapsingToolbar.setTitle("메세지 개수 : " + tmpList.size());
    }

    public class LoadSmsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//           또 들어갈 수 있으니깐 일단 비워준다.
            smsList.clear();
        }

        protected String doInBackground(String... args) { // ?? String ... 가 무엇일까요 ??
            String xml = "";

            try {
                Uri uriInbox = Uri.parse("content://sms/inbox");
                Cursor inbox = getContext().getContentResolver().query(uriInbox, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
//               이것들 하나하나 뭐하는 녀석들인지 알아야함 .
                Uri uriSent = Uri.parse("content://sms/sent");
                Cursor sent = getContext().getContentResolver().query(uriSent, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
                Cursor c = new MergeCursor(new Cursor[]{inbox, sent}); // Attaching inbox and sent sms
                Log.d("MainActivity.test11", "c.getCount() : " + c.getCount()); // 내가 보냈었던 메세지 갯수 .

                if (c.moveToFirst()) {

                    for (int i = 0; i < c.getCount(); i++) {
                        String name = "";
                        String phone = "";

                        String _id = c.getString(c.getColumnIndexOrThrow("_id"));
                        String thread_id = c.getString(c.getColumnIndexOrThrow("thread_id"));
                        String msg = c.getString(c.getColumnIndexOrThrow("body"));
                        String type = c.getString(c.getColumnIndexOrThrow("type"));
                        String timestamp = c.getString(c.getColumnIndexOrThrow("date"));
                        phone = c.getString(c.getColumnIndexOrThrow("address"));
                        name = CacheUtils.readFile(thread_id);
                        Log.d("MainActivity.test", "msg : " + msg);
                        name_count++;
                        // 이렇게 하면 17 번 돌게됩니다.
                        if (name == null) {
                            null_name_count++;
                            Log.d("MainActivity.test", " 저장안된 사람 : " + null_name_count);
                            name = MessageModel.getContactbyPhoneNumber(getActivity(), c.getString(c.getColumnIndexOrThrow("address")));
                            CacheUtils.writeFile(thread_id, name);
                        }
                        Log.d("MainActivity.test", "이름 갯수 : " + name_count);
                        smsList.add(MessageModel.mappingInbox(_id, thread_id, name, phone, msg, type, timestamp, MessageModel.converToTime(timestamp)));
                        c.moveToNext();
                    }
                }
                c.close();

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Collections.sort(smsList, new MessageComparator(MessageModel.KEY_TIMESTAMP, "dsc")); // Arranging sms by timestamp decending
            ArrayList<HashMap<String, String>> purified = MessageModel.removeDuplicates(smsList); // Removing duplicates from inbox & sent
            smsList.clear();
            smsList.addAll(purified);

            // Updating cache data
            try {
                MessageModel.createCachedFile(getContext(), "smsapp", smsList);
            } catch (Exception e) {
            }
            // Updating cache data
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (!tmpList.equals(smsList)) {
                adapter = new FragmentMessageAdapter(getContext(), smsList);
                fragmentMessageBinding.listView.setAdapter(adapter);
                fragmentMessageBinding.listView.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("name", smsList.get(+position).get(MessageModel.KEY_NAME));
                    intent.putExtra("address", tmpList.get(+position).get(MessageModel.KEY_PHONE));
                    intent.putExtra("thread_id", smsList.get(+position).get(MessageModel.KEY_THREAD_ID));
                    startActivity(intent);
                });
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
//                    loadSmsAsyncTask = new LoadSmsAsyncTask();
//                    loadSmsAsyncTask.execute();
                } else {
                    Toast.makeText(getContext(), "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void init() {
        smsList.clear();
        try {
            tmpList = (ArrayList<HashMap<String, String>>) MessageModel.readCachedFile(getContext(), "smsapp");
            Log.d("SmsActivity.qwe", "tmpList : " + tmpList.size());
            String sms_sum = String.valueOf(tmpList.size());
//            fragmentMessageBinding.messageSumTxt.setText("메시지개수 : " + sms_sum);
            tmpadapter = new FragmentMessageAdapter(getContext(), tmpList);
            fragmentMessageBinding.listView.setAdapter(tmpadapter);

            fragmentMessageBinding.listView.setOnItemClickListener((parent, view, position, id) -> {
                loadSmsAsyncTask.cancel(true);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("name", tmpList.get(+position).get(MessageModel.KEY_NAME));
                intent.putExtra("address", tmpList.get(+position).get(MessageModel.KEY_PHONE));
                intent.putExtra("thread_id", tmpList.get(+position).get(MessageModel.KEY_THREAD_ID));
                startActivity(intent);
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        String[] PERMISSIONS = {Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        if (!MessageModel.hasPermissions(getContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_PERMISSION_KEY);
        } else {

            init();
//            loadSmsAsyncTask = new LoadSmsAsyncTask();
//            loadSmsAsyncTask.execute();
        }
    }
}
