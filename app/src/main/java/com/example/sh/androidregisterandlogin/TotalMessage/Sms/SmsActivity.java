package com.example.sh.androidregisterandlogin.TotalMessage.Sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.sh.androidregisterandlogin.ToTalHome.CollectActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalMessage.Chat.ChatActivity;
import com.example.sh.androidregisterandlogin.TotalMessage.Function;
import com.example.sh.androidregisterandlogin.TotalMessage.MapComparator;
import com.example.sh.androidregisterandlogin.databinding.ActivitySmsBinding;
import com.lifeofcoding.cacheutlislibrary.CacheUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SmsActivity extends AppCompatActivity {

    private ActivitySmsBinding binding;
    static final int REQUEST_PERMISSION_KEY = 1;
    ArrayList<HashMap<String, String>> smsList = new ArrayList<>();
    ArrayList<HashMap<String, String>> tmpList = new ArrayList<>();
    static SmsActivity inst;
    LoadSmsAsyncTask loadSmsAsyncTask;
    InboxAdapter adapter, tmpadapter;

    // 나한테 메세지를 보냈는 사람의 총 갯수 변수
    int name_count = 0;
    int null_name_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sms);

        CacheUtils.configureCache(this);
        binding.listView.setEmptyView(binding.loadingBar);

    } // onCreate

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                    loadSmsAsyncTask = new LoadSmsAsyncTask();
                    loadSmsAsyncTask.execute();
                } else {
                    Toast.makeText(SmsActivity.this, "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void init() {
        smsList.clear();
        try {
            tmpList = (ArrayList<HashMap<String, String>>) Function.readCachedFile(SmsActivity.this, "smsapp");
            Log.d("SmsActivity.qwe", "tmpList : " + tmpList.size());
            String sms_sum = String.valueOf(tmpList.size());
            binding.messageSumTxt.setText("메시지개수 : "+sms_sum);
            tmpadapter = new InboxAdapter(SmsActivity.this, tmpList);
            binding.listView.setAdapter(tmpadapter);

            binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    loadSmsAsyncTask.cancel(true);
                    Intent intent = new Intent(SmsActivity.this, ChatActivity.class);
                    intent.putExtra("name", tmpList.get(+position).get(Function.KEY_NAME));
                    intent.putExtra("address", tmpList.get(+position).get(Function.KEY_PHONE));
                    intent.putExtra("thread_id", tmpList.get(+position).get(Function.KEY_THREAD_ID));
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
        }
    }

    class LoadSmsAsyncTask extends AsyncTask<String, Void, String> {
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
                Cursor inbox = getContentResolver().query(uriInbox, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
//               이것들 하나하나 뭐하는 녀석들인지 알아야함 .
                Uri uriSent = Uri.parse("content://sms/sent");
                Cursor sent = getContentResolver().query(uriSent, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
                Cursor c = new MergeCursor(new Cursor[]{inbox, sent}); // Attaching inbox and sent sms
                Log.d("MainActivity.test11", "c.getCount() : " + c.getCount()); // 내가 보냈었던 메세지 갯수 .
//                  19

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
                            name = Function.getContactbyPhoneNumber(getApplicationContext(), c.getString(c.getColumnIndexOrThrow("address")));
                            CacheUtils.writeFile(thread_id, name);
                        }
                        Log.d("MainActivity.test", "이름 갯수 : " + name_count);
                        smsList.add(Function.mappingInbox(_id, thread_id, name, phone, msg, type, timestamp, Function.converToTime(timestamp)));
                        c.moveToNext();
                    }
                }
                c.close();

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Collections.sort(smsList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging sms by timestamp decending
            ArrayList<HashMap<String, String>> purified = Function.removeDuplicates(smsList); // Removing duplicates from inbox & sent
            smsList.clear();
            smsList.addAll(purified);

            // Updating cache data
            try {
                Function.createCachedFile(SmsActivity.this, "smsapp", smsList);
            } catch (Exception e) {
            }
            // Updating cache data
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (!tmpList.equals(smsList)) {
                adapter = new InboxAdapter(SmsActivity.this, smsList);
                binding.listView.setAdapter(adapter);
                binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {
                        Intent intent = new Intent(SmsActivity.this, ChatActivity.class);
                        intent.putExtra("name", smsList.get(+position).get(Function.KEY_NAME));
                        intent.putExtra("address", tmpList.get(+position).get(Function.KEY_PHONE));
                        intent.putExtra("thread_id", smsList.get(+position).get(Function.KEY_THREAD_ID));
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] PERMISSIONS = {Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        if (!Function.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        } else {

            init();
            loadSmsAsyncTask = new LoadSmsAsyncTask();
            loadSmsAsyncTask.execute();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SmsActivity.this, CollectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}





