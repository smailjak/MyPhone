package com.example.sh.androidregisterandlogin.TotalPhoto.TotalFolder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.ToTalHome.CollectActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityTotalPhotoBinding;

import java.util.ArrayList;

public class TotalPhotoActivity extends AppCompatActivity {
    Context mContext;
    private ActivityTotalPhotoBinding binding;
    public ArrayList<Model_images> al_images = new ArrayList<>();
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_folder;
    private PhotosFolderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_total_photo);
        permissionCheck();
        initRv(binding.rcvTotalPhoto);
    }

    private void initRv(RecyclerView rv) {
        adapter = new PhotosFolderAdapter(fn_imagespath(), this);
        rv.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);
    }

    private void permissionCheck() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(TotalPhotoActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(TotalPhotoActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(TotalPhotoActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }
    }

    public ArrayList<Model_images> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }

            if (boolean_folder) {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);
            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images(cursor.getString(column_index_folder_name), al_path);
                al_images.add(obj_model);
            }
        }

        int imageCount = 0;
        for (int i = 0; i < al_images.size(); i++) {
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                imageCount++;
            }
        }
        Log.e("#$Total", "10");
        binding.totalPhotoNumber.setText("사진개수 : " + imageCount);
        return al_images;
    }

    // 권한에 대한 메소드 입니다.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_imagespath().size();
                    } else {
                        Toast.makeText(TotalPhotoActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TotalPhotoActivity.this, CollectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}


