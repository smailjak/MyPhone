package com.example.sh.androidregisterandlogin.TotalAddress;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sh.androidregisterandlogin.ToTalHome.CollectActivity;
import com.example.sh.androidregisterandlogin.R;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends Activity {

    private ListView lv_contactlist;
    TextView address_sum_number;
    public static int address_coffhfunt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        lv_contactlist = (ListView) findViewById(R.id.lv_contactlist);
        address_sum_number = findViewById(R.id.address_sum_number);

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


    @Override
    protected void onResume() {
        super.onResume();
        ContactsAdapter adapter = new ContactsAdapter(ContactListActivity.this,
                R.layout.layout_phonelist, getContactList());

        lv_contactlist.setAdapter(adapter);
        lv_contactlist
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> contactlist, View v,
                                            int position, long resid) {
                        Contact phonenumber = (Contact) contactlist
                                .getItemAtPosition(position);

                        if (phonenumber == null) {
                            return;
                        }
//                       여기액티비티에서는 클릭하면 전화하는 화면으로 가게 한다 .
//                        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse(phonenumber.getPhonenum().replaceAll("-","")));
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonenumber.getPhonenum()));
                        startActivity(intent);
//                        intent.putExtra(MainActivity.SELECTED_PHONE, phonenumber.getPhonenum().replaceAll("-", ""));
//                        setResult(MainActivity.SUCCESS, data);
//                        finish();
                    }
                });
    }

    /**
     * 연락처를 가져오는 메소드.
     *
     * @return
     */
    private ArrayList<Contact> getContactList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = managedQuery(uri, projection, null,
                selectionArgs, sortOrder);

        ArrayList<Contact> contactlist = new ArrayList<Contact>();


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

            Contact acontact = new Contact();
            acontact.setPhotoid(contactCursor.getLong(0));
            acontact.setPhonenum(phonenumber);
            acontact.setName(contactCursor.getString(2));
            contactlist.add(acontact);
            address_count++;
            Log.d("ContactListActivity.qwe", "address_count : " + address_count);
        } while (contactCursor.moveToNext());
        String address_sum = String.valueOf(address_count);
        address_sum_number.setText(address_sum);
//            Intent intent = new Intent(ContactListActivity.this,CollectActivity.class);
//            intent.putExtra("address_to_collect",address_sum);

        Log.d("qwe", "contactlist : " + contactlist);
        return contactlist;

    }

    private class ContactsAdapter extends ArrayAdapter<Contact> {

        private int resId;
        private ArrayList<Contact> contactlist;
        private LayoutInflater Inflater;
        private Context context;

        public ContactsAdapter(Context context, int textViewResourceId,
                               List<Contact> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            resId = textViewResourceId;
            contactlist = (ArrayList<Contact>) objects;
            Inflater = (LayoutInflater) ((Activity) context)
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            ViewHolder holder;
            if (v == null) {
                v = Inflater.inflate(resId, null);
                holder = new ViewHolder();
                holder.tv_name = (TextView) v.findViewById(R.id.tv_name);
                holder.tv_phonenumber = (TextView) v.findViewById(R.id.tv_phonenumber);
                holder.iv_photoid = (ImageView) v.findViewById(R.id.iv_photo);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            Contact acontact = contactlist.get(position);

            if (acontact != null) {
                holder.tv_name.setText(acontact.getName());
                holder.tv_phonenumber.setText(acontact.getPhonenum());

                Bitmap bm = openPhoto(acontact.getPhotoid());
//                이부분이 만약 이미지가 없다면 기본 이미지를 보여주게 된다 .
                if (bm != null) {
                    holder.iv_photoid.setImageBitmap(bm);
                } else {

                    String firstLetter = String.valueOf(acontact.getName().charAt(0));
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color = generator.getColor(getItem(position));
                    TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color);

//                    holder.iv_photoid.setImageDrawable(getResources().getDrawable(R.drawable.total_photo));
                    holder.iv_photoid.setImageDrawable(drawable);

                }
            }
            return v;
        }

        /**
         * 연락처 사진 id를 가지고 사진에 들어갈 bitmap을 생성.
         *
         * @param contactId 연락처 사진 ID
         * @return bitmap  연락처 사진
         */
        private Bitmap openPhoto(long contactId) {
            Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
                    contactId);
            InputStream input = Contacts
                    .openContactPhotoInputStream(context.getContentResolver(),
                            contactUri);

            if (input != null) {
                return BitmapFactory.decodeStream(input);
            }

            return null;
        }

        private class ViewHolder {
            ImageView iv_photoid;
            TextView tv_name;
            TextView tv_phonenumber;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactListActivity.this, CollectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

