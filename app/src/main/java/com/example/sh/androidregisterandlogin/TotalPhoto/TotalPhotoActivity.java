package com.example.sh.androidregisterandlogin.TotalPhoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.ToTalHome.CollectActivity;
import com.example.sh.androidregisterandlogin.R;

import java.util.ArrayList;


public class TotalPhotoActivity extends AppCompatActivity {

    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    Adapter_PhotosFolder obj_adapter;
    TextView total_photo_number;
    GridView gridViewFolder;
    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_total_photo);
        gridViewFolder = findViewById(R.id.total_gridview_folder);
        total_photo_number = findViewById(R.id.total_photo_number);
        permission();
        gridViewClick();

    }

    public void gridViewClick() {
        gridViewFolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                 AdapterView 에 담을 수 있는것은 ? 이다 . 이것이 무엇을 의미하는지 이해하지 못했습니다.
                Intent intent = new Intent(TotalPhotoActivity.this, PhotosActivity.class);
                intent.putExtra("value", i);
                startActivity(intent);
            }
        });
    }

    private void permission() {
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
        } else {
            Log.e("Else", "Else");
            fn_imagespath();
        }
    }

    /**
     * @return
     */
    public ArrayList<Model_images> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;

//      MediaStore는 시스템이 제공하는 Provider를 이용해 미디어 파일(오디오, 이미지, 비디오)를 쿼리할 수 있다
//        EXTERNAL_CONTENT_URI 라는 말은 외부 라는 말입니다 . ==> 외부에 저장되어 있는 미디어 리스트 보기
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        //      String 배열 이름은 projection 으로 설정하고 ,
//      MediaStore.MediaColumns.DATA ==> 미디어의 뜻은 정보를 전송하는 매체입니다.
//       정보를 전송하는 매체 Store 에서 , 정보전송의 기둥에 , DATA 를 배열에 넣습니다.
//      정보 전송하는 매체 Store 에서 , 이미지 전송  , 양동이 이름을 보여주는것을 배열에 넣습니다.

        Log.d("TotalPhotoActivity.qwe", "MediaStore.MediaColumns.DATA : " + MediaStore.MediaColumns.DATA);
        Log.d("TotalPhotoActivity.qwe", "MediaStore.Images.Media.BUCKET_DISPLAY_NAME : " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
//      Cursor 는 한 row 를 가르키는 로직입니다.
//      예를 들어서 ,
//        아래와 같이 Cursor인스턴스인 c에 넣어주어. 사용할 수 있습니다. 마치 데이터베이스 table 같다는 느낌을 받았습니다.
//                Cursor c = db.query(
//                Database.MoneyList.TABLE_NAME,  // The table to query
//                projection,                               // The columns to return
//                null,                                // The columns for the WHERE clause
//                null,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                sortOrder                                 // The sort order
//        );

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));

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
                //절대경로와 상대경로를 찾아보면 나옵니다 .

                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);
                al_images.add(obj_model);
            }  // else 문 끝나는 부분
        }   // while문 끝나는 부분

//      al_images 는 사진 폴더 개수 와 파일 개수
        int imageCount = 0;
        for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder() + i);
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j) + j);
                imageCount++;
            }
        }
        total_photo_number.setText(Integer.toString(imageCount));

        obj_adapter = new Adapter_PhotosFolder(getApplicationContext(), al_images);
        gridViewFolder.setAdapter(obj_adapter);
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


