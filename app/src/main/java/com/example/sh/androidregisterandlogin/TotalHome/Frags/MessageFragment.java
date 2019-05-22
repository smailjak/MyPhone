package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.lifeofcoding.cacheutlislibrary.CacheUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class MessageFragment extends Fragment {

    private FragmentMessageBinding binding;
    static final int REQUEST_PERMISSION_KEY = 1;
    Context mContext; // Context mContext ;
    private final String WATING_GREETINGS = "please wating ~ ^ ^ ";
    ArrayList<HashMap<String, String>> smsList = new ArrayList<>();
    ArrayList<HashMap<String, String>> tmpList = new ArrayList<>();
    MessageAdapter adapter, tmpadapter;
    ProgressDialog progressDialog;
    int name_count = 0;
    int null_name_count = 0;

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
        initRecyclerView(binding.rcvMsg);
        initCollapsingToolbar();
        new LoadSmsAsyncTask().execute();
    }

    private void initRecyclerView(RecyclerView rcv) {
        adapter = new MessageAdapter(smsList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setHasFixedSize(true);
        rcv.setAdapter(adapter);
    }

    private void initCollapsingToolbar() {
        binding.collapsingToolbar.setTitle("");
        binding.appbar.setExpanded(true);
        try {
            tmpList = (ArrayList<HashMap<String, String>>) MessageModel.readCachedFile(getContext(), "smsapp");
            binding.collapsingToolbar.setTitle("메세지 개수 : " + tmpList.size());
            binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.coll_basic_title);
            binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.coll_expand_title);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class LoadSmsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//           또 들어갈 수 있으니깐 일단 비워준다.
            smsList.clear();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(WATING_GREETINGS);
            progressDialog.show();

        }

        protected String doInBackground(String... args) { // ?? String ... 가 무엇일까요 ??
            String xml = "";

            try {
                Uri uriInbox = Uri.parse("content://sms/inbox");
                Cursor inbox = getContext().getContentResolver().query(uriInbox, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
//               이것들 하나하나 뭐하는 녀석들인지 알아야함 .
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
                        Log.d("MainActivity.test", "msg : " + msg);
                        name_count++;
                        // 이렇게 하면 17 번 돌게됩니다.
                        if (name == null) {
                            null_name_count++;
                            Log.d("MainActivity.test", " 저장안된 사람 : " + null_name_count);
                            name = MessageModel.getContactbyPhoneNumber(getActivity(), cursor.getString(cursor.getColumnIndexOrThrow("address")));
                            CacheUtils.writeFile(thread_id, name);
                        }
                        Log.d("MainActivity.test", "이름 갯수 : " + name_count);
                        smsList.add(MessageModel.mappingInbox(_id, thread_id, name, phone, msg, type, timestamp, MessageModel.converToTime(timestamp)));
                        cursor.moveToNext();
                    }
                }
                cursor.close();

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
//                adapter = new FragmentMessageAdapter(getContext(), smsList);
//                fragmentMessageBinding.listView.setAdapter(adapter);
//                fragmentMessageBinding.listView.setOnItemClickListener((parent, view, position, id) -> {
//                    Intent intent = new Intent(getContext(), ChatActivity.class);
//                    intent.putExtra("name", smsList.get(+position).get(MessageModel.KEY_NAME));
//                    intent.putExtra("address", tmpList.get(+position).get(MessageModel.KEY_PHONE));
//                    intent.putExtra("thread_id", smsList.get(+position).get(MessageModel.KEY_THREAD_ID));
//                    startActivity(intent);
//                });
                binding.rcvMsg.setAdapter(adapter);
            }
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
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
//            Log.d("SmsActivity.qwe", "tmpList : " + tmpList.size());
//            binding.tvMessageCount.setText("메시지개수 : " + tmpList.size());
//            tmpadapter = new FragmentMessageAdapter(getContext(), tmpList);
//            binding.listView.setAdapter(tmpadapter);
//
//            binding.listView.setOnItemClickListener((parent, view, position, id) -> {
//                loadSmsAsyncTask.cancel(true);
//                Intent intent = new Intent(getActivity(), ChatActivity.class);
//                intent.putExtra("name", tmpList.get(+position).get(MessageModel.KEY_NAME));
//                intent.putExtra("address", tmpList.get(+position).get(MessageModel.KEY_PHONE));
//                intent.putExtra("thread_id", tmpList.get(+position).get(MessageModel.KEY_THREAD_ID));
//                startActivity(intent);
//            });
//        } catch (Exception e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tmpadapter = new MessageAdapter(tmpList, getContext());
        binding.rcvMsg.setAdapter(tmpadapter);
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
        }
    }
}
