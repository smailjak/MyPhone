package com.example.sh.androidregisterandlogin.TotalPhoto;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.GridView;
import com.example.sh.androidregisterandlogin.R;

/**
 * Created by deepshikha on 20/3/17.
 */

public class PhotosActivity extends AppCompatActivity {
    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        gridView = (GridView) findViewById(R.id.photos_gridview_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(this, TotalPhotoActivity.al_images, int_position);
        gridView.setAdapter(adapter);
    }
}