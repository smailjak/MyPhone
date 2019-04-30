package com.example.sh.androidregisterandlogin.TotalPhoto.DetailFile;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityPhotosBinding;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {

    private ActivityPhotosBinding binding;

    public static int int_position = 0;
    public ArrayList<File_images> al_files = new ArrayList<>();
    boolean boolean_file;
    PhotoFileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photos);
        int_position = getIntent().getIntExtra("value", 0); // ? 이건뭐지 ??
        initRv(binding.photosRcvFile);
    }

    private void initRv(RecyclerView rv) {
        adapter = new PhotoFileAdapter(fileImagesPath(), this);
        rv.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);
    }

    public ArrayList<File_images> fileImagesPath() {
        al_files.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_file_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_file_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            for (int i = 0; i < al_files.size(); i++) {
                if (al_files.get(i).getAlFilePath().equals(cursor.getString(column_index_file_name))) {
                    boolean_file = true;
                    int_position = i;
                    break;
                } else {
                    boolean_file = false;
                }
            }

            if (boolean_file) {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_files.get(int_position).getAlFilePath());
                al_path.add(absolutePathOfImage);
                al_files.get(int_position).setAlFilePath(al_path);
            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                File_images file_images = new File_images(al_path, cursor.getString(column_index_file_name));
                al_files.add(file_images);
            }
        }

        int imageCount = 0;
        for (int i = 0; i < al_files.size(); i++) {
            for (int j = 0; j < al_files.get(i).getAlFilePath().size(); j++) {
                imageCount++;
            }
        }
        Log.e("#$Total", "10");
//        binding.totalPhotoNumber.setText(Integer.toString(imageCount));
        return al_files;
    }
}