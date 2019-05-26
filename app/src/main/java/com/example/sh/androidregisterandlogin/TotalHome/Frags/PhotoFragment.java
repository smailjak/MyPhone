package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalHome.Adapters.PhotoFolderAdapter;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.PhotoFolderDataItem;
import com.example.sh.androidregisterandlogin.databinding.FragmentPhotoBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class PhotoFragment extends Fragment {

    public ArrayList<PhotoFolderDataItem> al_images = new ArrayList<>();
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_folder;
    private PhotoFolderAdapter adapter;
    FragmentPhotoBinding binding;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);
        initCollapsingToolbar(binding.collapsingToolbar);
        permissionCheck();
        initRv(binding.rcvTotalPhoto);
    }

    private void initRv(RecyclerView rcv) {
        adapter = new PhotoFolderAdapter(fn_imagespath(), getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcv.setAdapter(adapter);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(gridLayoutManager);
    }

    private void initCollapsingToolbar(CollapsingToolbarLayout ctl) {
        ctl.setTitle("");
        binding.photoAppbar.setExpanded(true);
        ctl.setCollapsedTitleTextAppearance(R.style.coll_basic_title);
        ctl.setExpandedTitleTextAppearance(R.style.coll_expand_title);
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
                // 폴더를 보여주게 된다.
            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                PhotoFolderDataItem obj_model = new PhotoFolderDataItem(cursor.getString(column_index_folder_name), al_path);
                al_images.add(obj_model);
                // 파일 하나하나 를 보여주게된다.
            }
        }

        int imageCount = 0;
        for (int i = 0; i < al_images.size(); i++) {
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                imageCount++;
            }
        }
        binding.collapsingToolbar.setTitle("사진개수 : " + imageCount);
        return al_images;
    }

    //   권한
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
//    ActionBar 구현하는 부분

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        changeSearchViewTextColor(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                키보드의 검색 버튼을 누르면 이 함수가 호출됩니다.
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                item.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<PhotoFolderDataItem> filtermodellist = filter(al_images, newText);
                adapter.setfileter(filtermodellist);
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

    private List<PhotoFolderDataItem> filter(List<PhotoFolderDataItem> p1, String query) {
        query = query.toLowerCase();
        final List<PhotoFolderDataItem> filteredModelList = new ArrayList<>();
        for (PhotoFolderDataItem model : p1) {
            final String text = model.getStr_folder().toLowerCase();
            if (text.startsWith(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.BLACK);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }
}