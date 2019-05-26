package com.example.sh.androidregisterandlogin.TotalPhoto.DetailFile;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityPhotosBinding;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {

    private ActivityPhotosBinding binding;
    int int_position = 0;
    ArrayList<File_images> al_files = new ArrayList<>();
    boolean boolean_file;
    String absolutePathOfImage;
    PhotoFileAdapter adapter;
    ArrayList<String> al_path = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photos);
        int_position = getIntent().getIntExtra("value", 1);
        Log.d("qweqwe", "onCreate: int_position " + int_position);
        initRv(binding.rcvPhotosFile);
    }

    private void initRv(RecyclerView rcv) {
        adapter = new PhotoFileAdapter(fileImagesPath(), this);
        rcv.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rcv.setLayoutManager(gridLayoutManager);
        rcv.setHasFixedSize(true);
    }

    public ArrayList<File_images> fileImagesPath() {
        al_files.clear();
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_file_name;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA
                , MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA); // 0 으로 나옴
        column_index_file_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME); // 1 으로 나옴

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data); // 전체 이미지 경로가 나옴
//            Log.d("photo", "fileImagesPath: " + cursor.getString(column_index_file_name));
//           전체 경로를 다 불러오게 되면 비효울적이것같은 ???...
//            Log.d("photo", "fileImagesPath: " + absolutePathOfImage);
            for (int i = 0; i < al_files.size(); i++) {
            Log.d("photo", "fileImagesPath: " + al_files.get(i).getAlFilePath());

//                if (al_files.get(i).getAlFilePath().equals(cursor.getString(column_index_file_name))) {
//                    Log.d("photo", "fileImagesPath:  여기는 true ");
//                    boolean_file = true;
//                    int_position = i;
//                    break;
//                } else {
//                    Log.d("qwe", "fileImagesPath:  여기는 true ");
//                    boolean_file = false;
//                }
            }

            if (boolean_file) {
                al_path.addAll(al_files.get(int_position).getAlFilePath());
                al_path.add(absolutePathOfImage);
                al_files.get(int_position).setAlFilePath(al_path);
            } else {
                al_path.add(absolutePathOfImage);
                File_images file_images = new File_images(al_path, cursor.getString(column_index_file_name));
                al_files.add(file_images);
            }
        }
        return al_files;
    }
}