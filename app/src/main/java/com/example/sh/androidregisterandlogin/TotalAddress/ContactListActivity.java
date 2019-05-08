package com.example.sh.androidregisterandlogin.TotalAddress;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.ToTalHome.CollectActivity;
import com.example.sh.androidregisterandlogin.TotalDataItem.AddressDataItem;
import com.example.sh.androidregisterandlogin.databinding.ActivityContactlistBinding;

import java.util.ArrayList;

public class ContactListActivity extends Activity {
    private ActivityContactlistBinding binding;
    int address_count = 0;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contactlist);
        initRv(binding.rvMain);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1000);
            } else {
                // READ_EXTERNAL_STORAGE 에 대한 권한이 있음.
//                getAudioListFromMediaDatabase();
            }
        }
        // OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
        else {
//            getAudioListFromMediaDatabase();
        }
    }

    private void initRv(RecyclerView rv) {
        adapter = new ContactAdapter(getContactList(), this);
        rv.setAdapter(adapter);

        LinearLayoutManager layoutManger = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManger);
        rv.setHasFixedSize(true);
    }

    /**
     * 연락처를 가져오는 메소드.
     *
     * @return
     */
    private ArrayList<AddressDataItem> getContactList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = managedQuery(uri, projection, null,
                selectionArgs, sortOrder);

        ArrayList<AddressDataItem> contactlist = new ArrayList<AddressDataItem>();


        contactCursor.moveToFirst();
        address_count = 0;
        do {
            String phonenumber = contactCursor.getString(1).replaceAll("-",
                    "");

            if (phonenumber.length() == 10) {
                phonenumber = phonenumber.substring(0, 3) + "-"
                        + phonenumber.substring(3, 6) + "-"
                        + phonenumber.substring(6);
            } else if (phonenumber.length() > 8) {
                phonenumber = phonenumber.substring(0, 3) + "-"
                        + phonenumber.substring(3, 7) + "-"
                        + phonenumber.substring(7);
            }

            AddressDataItem acontact = new AddressDataItem();
            acontact.setPhotoid(contactCursor.getLong(0));
            acontact.setPhonenum(phonenumber);
            acontact.setName(contactCursor.getString(2));
            contactlist.add(acontact);
            address_count++;
            Log.d("ContactListActivity.qwe", "address_count : " + address_count);
        } while (contactCursor.moveToNext());
        String address_sum = String.valueOf(address_count);
        binding.tvAddressNumber.setText("연락처개수 : " + address_sum);

        Log.d("qwe", "contactlist : " + contactlist);
        return contactlist;

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactListActivity.this, CollectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

