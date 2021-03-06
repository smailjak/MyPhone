package com.example.sh.androidregisterandlogin.TotalMessage.Chat;

import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageModel;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageComparator;
import com.example.sh.androidregisterandlogin.databinding.ActivityChatActivityBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class ChatActivity extends AppCompatActivity {
    private ActivityChatActivityBinding binding;
    ChatAdapter adapter;
    LoadSmsAsyncTask loadSmsAsyncTask;
    String name, address;
    int thread_id_main;
    private Handler handler = new Handler();
    ArrayList<HashMap<String, String>> smsList = new ArrayList<>();
    ArrayList<HashMap<String, String>> customList = new ArrayList<>();
    ArrayList<HashMap<String, String>> tmpList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat_activity);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        thread_id_main = Integer.parseInt(intent.getStringExtra("thread_id"));

        startLoadingSms();
        sendMessageImageBtn();

    }

    public void startLoadingSms() {
        final Runnable r = new Runnable() {
            public void run() {

                loadSmsAsyncTask = new LoadSmsAsyncTask();
                loadSmsAsyncTask.execute();

                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(r, 0);
    }

    class LoadSmsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tmpList.clear();
        }

        protected String doInBackground(String... args) {
            String xml = "";

            try {
                Uri uriInbox = Uri.parse("content://sms/inbox");
                Cursor inbox = getContentResolver().query(uriInbox, null, "thread_id=" + thread_id_main, null, null);
                Uri uriSent = Uri.parse("content://sms/sent");
                Cursor sent = getContentResolver().query(uriSent, null, "thread_id=" + thread_id_main, null, null);
                Cursor cursor = new MergeCursor(new Cursor[]{inbox, sent}); // Attaching inbox and sent sms


                if (cursor.moveToFirst()) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        String phone = "";
                        String _id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                        String thread_id = cursor.getString(cursor.getColumnIndexOrThrow("thread_id"));
                        String msg = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                        String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                        String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                        phone = cursor.getString(cursor.getColumnIndexOrThrow("address"));

                        tmpList.add(MessageModel.mappingInbox(_id, thread_id, name, phone, msg, type, timestamp, MessageModel.converToTime(timestamp)));
                        cursor.moveToNext();
                    }
                }
                cursor.close();

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Collections.sort(tmpList, new MessageComparator(MessageModel.KEY_TIMESTAMP, "asc"));

            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (!tmpList.equals(smsList)) {
                smsList.clear();
                smsList.addAll(tmpList);
                adapter = new ChatAdapter(ChatActivity.this, smsList);
                binding.listView.setAdapter(adapter);

            }
        }
    }

    void sendMessageImageBtn() {
        binding.sendMessageImageBtn.setOnClickListener(view-> {

                String text = binding.newMessage.getText().toString();

                if (text.length() > 0) {
                    String tmp_msg = text;
                    binding.newMessage.setText("Sending....");
                    binding.newMessage.setEnabled(false);

                    if (MessageModel.sendSMS(address, tmp_msg)) {
                        binding.newMessage.setText("");
                        binding.newMessage.setEnabled(true);
                        // Creating a custom list for newly added sms
                        customList.clear();
                        customList.addAll(smsList);
                        customList.add(MessageModel.mappingInbox(null, null, null, null, tmp_msg, "2", null, "Sending..."));
                        adapter = new ChatAdapter(ChatActivity.this, customList);
                        binding.listView.setAdapter(adapter);
                        //=========================
                    } else {
                        binding.newMessage.setText(tmp_msg);
                        binding.newMessage.setEnabled(true);
                    }
                }
        });
    }
}







