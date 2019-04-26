package com.example.sh.androidregisterandlogin.TotalMessage.Chat;

import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalMessage.Function;
import com.example.sh.androidregisterandlogin.TotalMessage.MapComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by SHAJIB on 7/13/2017.
 */

public class ChatActivity extends AppCompatActivity {

    ListView listView;
    ChatAdapter adapter;
    LoadSmsAsyncTask loadSmsAsyncTask;
    String name, address;
    EditText new_message;

    ImageButton sendMessageImageBtn;
    int thread_id_main;
    private Handler handler = new Handler();
    Thread t;
    ArrayList<HashMap<String, String>> smsList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> customList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> tmpList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        thread_id_main = Integer.parseInt(intent.getStringExtra("thread_id"));

        listView = (ListView) findViewById(R.id.listView);
        new_message = (EditText) findViewById(R.id.new_message);
        sendMessageImageBtn = (ImageButton) findViewById(R.id.sendMessageImageBtn);

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
                Cursor c = new MergeCursor(new Cursor[]{inbox, sent}); // Attaching inbox and sent sms


                if (c.moveToFirst()) {
                    for (int i = 0; i < c.getCount(); i++) {
                        String phone = "";
                        String _id = c.getString(c.getColumnIndexOrThrow("_id"));
                        String thread_id = c.getString(c.getColumnIndexOrThrow("thread_id"));
                        String msg = c.getString(c.getColumnIndexOrThrow("body"));
                        String type = c.getString(c.getColumnIndexOrThrow("type"));
                        String timestamp = c.getString(c.getColumnIndexOrThrow("date"));
                        phone = c.getString(c.getColumnIndexOrThrow("address"));

                        tmpList.add(Function.mappingInbox(_id, thread_id, name, phone, msg, type, timestamp, Function.converToTime(timestamp)));
                        c.moveToNext();
                    }
                }
                c.close();

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Collections.sort(tmpList, new MapComparator(Function.KEY_TIMESTAMP, "asc"));

            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (!tmpList.equals(smsList)) {
                smsList.clear();
                smsList.addAll(tmpList);
                adapter = new ChatAdapter(ChatActivity.this, smsList);
                listView.setAdapter(adapter);

            }
        }
    }

    void sendMessageImageBtn() {
        sendMessageImageBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String text = new_message.getText().toString();

                if (text.length() > 0) {
                    String tmp_msg = text;
                    new_message.setText("Sending....");
                    new_message.setEnabled(false);

                    if (Function.sendSMS(address, tmp_msg)) {
                        new_message.setText("");
                        new_message.setEnabled(true);
                        // Creating a custom list for newly added sms
                        customList.clear();
                        customList.addAll(smsList);
                        customList.add(Function.mappingInbox(null, null, null, null, tmp_msg, "2", null, "Sending..."));
                        adapter = new ChatAdapter(ChatActivity.this, customList);
                        listView.setAdapter(adapter);
                        //=========================
                    } else {
                        new_message.setText(tmp_msg);
                        new_message.setEnabled(true);
                    }
                }
            }
        });
    }

}







