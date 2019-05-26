package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.sh.androidregisterandlogin.TotalHome.Datas.MessageModel;
import com.example.sh.androidregisterandlogin.data.SmsMessage;
import com.example.sh.androidregisterandlogin.databinding.FragmentMessageBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;


public class MessageFragment extends Fragment {
    private FragmentMessageBinding binding;
    static final int REQUEST_PERMISSION_KEY = 1;
    private final String WATING_GREETINGS = "please wating ~ ^ ^ ";

    private MessageAdapter adapter;
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

    private void initProgressBar() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(WATING_GREETINGS);
    }

    private void initMessageRcv(RecyclerView rcv) {
        adapter = new MessageAdapter(new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setHasFixedSize(true);
        rcv.setAdapter(adapter);
    }

    private void initCollapsingToolbar(CollapsingToolbarLayout ctl) {
        ctl.setTitle("");
        binding.appbar.setExpanded(true);
        ctl.setTitle("메세지 개수 : " + adapter.getItemCount());
        ctl.setCollapsedTitleTextAppearance(R.style.coll_basic_title);
        ctl.setExpandedTitleTextAppearance(R.style.coll_expand_title);
    }

    public class LoadSmsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            initProgressBar();
            progressDialog.show();
        }

        protected String doInBackground(String... args) {
            readSMSMessage();
            return "success";
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

    private void readSMSMessage() {
        Uri allMessage = Uri.parse("content://sms");
        ContentResolver cr;
        Cursor c;
        try {
            cr = getContext().getContentResolver();
            c = cr.query(allMessage,
                    new String[]{"_id", "thread_id", "address", "person", "date", "body"},
                    null, null,
                    "date DESC");
            while (c.moveToNext()) {
                SmsMessage msg = new SmsMessage();

                long messageId = c.getLong(0);
                msg.setMessageId(String.valueOf(messageId));

                long threadId = c.getLong(1);
                msg.setThreadId(String.valueOf(threadId));

                String address = c.getString(2);
                msg.setAddress(address);

                long contactId = c.getLong(3);
                msg.setContactId(String.valueOf(contactId));

                String contactId_string = String.valueOf(contactId);
                msg.setContactId_string(contactId_string);

                long timestamp = c.getLong(4);
                msg.setTimestamp(String.valueOf(timestamp));

                String body = c.getString(5);
                msg.setBody(body);

                adapter.addItem(msg);
            }
        }catch (Exception e){
            Log.e("#$Main", e.getMessage());
        }

    }
}
