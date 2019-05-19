package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Adapters.FragmentPhonebookAdapter;
import com.example.sh.androidregisterandlogin.TotalDataItem.AddressDataItem;
import com.example.sh.androidregisterandlogin.databinding.FragmentPhonebookBinding;

import java.util.ArrayList;


public class PhoneBookFragment extends Fragment {

    FragmentPhonebookBinding fragmentPhonebookBinding;
    FragmentPhonebookAdapter fragmentPhonebookAdapter;

    int address_count = 0;

    public PhoneBookFragment() {
    }

    public static PhoneBookFragment newInstance() {
        return new PhoneBookFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentPhonebookBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_phonebook, container, false);
        return fragmentPhonebookBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(fragmentPhonebookBinding.toolbar);
        setHasOptionsMenu(true);
        permissionCheck();
        initCollapsingToolbar();
        initRcv();
    }

    private void initCollapsingToolbar() {
        fragmentPhonebookBinding.collapsingToolbar.setTitle("");
        fragmentPhonebookBinding.appbar.setExpanded(true);
        fragmentPhonebookBinding.collapsingToolbar.setTitle("연락처개수 : " + getContactList().size());
        fragmentPhonebookBinding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.coll_basic_title);
        fragmentPhonebookBinding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.coll_expand_title);
    }


    private void permissionCheck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1000);
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


    private void initRcv() {
        fragmentPhonebookAdapter = new FragmentPhonebookAdapter(getContext(), getContactList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentPhonebookBinding.rcvPhoneBook.setLayoutManager(linearLayoutManager);
        fragmentPhonebookBinding.rcvPhoneBook.setHasFixedSize(true);
        fragmentPhonebookBinding.rcvPhoneBook.setAdapter(fragmentPhonebookAdapter);
    }


    private ArrayList<AddressDataItem> getContactList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = getContext().getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);

        ArrayList<AddressDataItem> contactlist = new ArrayList<>();


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
        } while (contactCursor.moveToNext());
//        String address_sum = String.valueOf(address_count);

        return contactlist;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                키보드의 검색 버튼을 누르면 이 함수가 호출됩니다.
                fragmentPhonebookAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                이 함수는 searchview에 입력 할 때마다 호출됩니다.
                fragmentPhonebookAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        다른 메뉴 항목 클릭을 여기에서 처리하십시오.
        if (id == R.id.action_settings) {
            Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_voice) {
            Toast.makeText(getContext(), "Voice", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
