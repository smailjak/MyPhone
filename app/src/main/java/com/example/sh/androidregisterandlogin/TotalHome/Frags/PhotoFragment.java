package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.PhotoFolderDataItem;
import com.example.sh.androidregisterandlogin.TotalHome.Adapters.PhotoFolderAdapter;
import com.example.sh.androidregisterandlogin.databinding.FragmentPhotoBinding;

import java.util.ArrayList;

public class PhotoFragment extends Fragment {

    public ArrayList<PhotoFolderDataItem> al_images = new ArrayList<>();
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_folder;
    private SearchView searchView;
    private PhotoFolderAdapter photoFolderAdapter;
    FragmentPhotoBinding fragmentPhotoBinding;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPhotoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false);
        return fragmentPhotoBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(fragmentPhotoBinding.toolbar);
        setHasOptionsMenu(true);
        initCollapsingToolbar();
        permissionCheck();
        initRv();
    }

    private void initRv() {
        photoFolderAdapter = new PhotoFolderAdapter(fn_imagespath(), getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        fragmentPhotoBinding.rcvTotalPhoto.setAdapter(photoFolderAdapter);
        fragmentPhotoBinding.rcvTotalPhoto.setHasFixedSize(true);
        fragmentPhotoBinding.rcvTotalPhoto.setLayoutManager(gridLayoutManager);
    }

    private void initCollapsingToolbar() {

        fragmentPhotoBinding.collapsingToolbar.setTitle("");
        fragmentPhotoBinding.photoAppbar.setExpanded(true);
        fragmentPhotoBinding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.coll_basic_title);
        fragmentPhotoBinding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.coll_expand_title);
    }

    private void permissionCheck() {
        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }
    }

    public ArrayList<PhotoFolderDataItem> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<PhotoFolderDataItem> photoFolderDataItems = new ArrayList<>();

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

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
                PhotoFolderDataItem obj_model = new PhotoFolderDataItem(cursor.getString(column_index_folder_name), al_path);
                al_images.add(obj_model);
            }
        }

        int imageCount = 0;
        for (int i = 0; i < al_images.size(); i++) {
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                imageCount++;
            }
        }
        fragmentPhotoBinding.collapsingToolbar.setTitle("사진개수 : "+imageCount);
        return al_images;
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_imagespath().size();
                    } else {
                        Toast.makeText(getActivity(), "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                키보드의 검색 버튼을 누르면 이 함수가 호출됩니다.
                photoFolderAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                이 함수는 searchview에 입력 할 때마다 호출됩니다.
                photoFolderAdapter.getFilter().filter(s);
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
