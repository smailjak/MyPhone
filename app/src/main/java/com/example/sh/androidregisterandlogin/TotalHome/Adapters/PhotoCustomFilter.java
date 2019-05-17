package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.widget.Filter;

import com.example.sh.androidregisterandlogin.TotalHome.Datas.PhotoFolderDataItem;

import java.util.ArrayList;

public class PhotoCustomFilter extends Filter {

    FragmentPhotoFolderAdapter fragmentPhotoFolderAdapter;
    ArrayList<PhotoFolderDataItem> photoFilterList;

    public PhotoCustomFilter(ArrayList<PhotoFolderDataItem> photoFilterList, FragmentPhotoFolderAdapter fragmentPhotoFolderAdapter) {
        this.fragmentPhotoFolderAdapter = fragmentPhotoFolderAdapter;
        this.photoFilterList = photoFilterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<PhotoFolderDataItem> filterPhoto = new ArrayList<>();
            for (int i = 0; i < photoFilterList.size(); i++) {
                if (photoFilterList.get(i).getStr_folder().toUpperCase().contains(constraint)) {
                    filterPhoto.add(photoFilterList.get(i));
                }
            }
            results.count = filterPhoto.size();
            results.values = filterPhoto;
        } else {
            results.count = photoFilterList.size();
            results.values = photoFilterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        fragmentPhotoFolderAdapter.photoFolderDataItemArrayList = (ArrayList<PhotoFolderDataItem>) results.values;
        fragmentPhotoFolderAdapter.notifyDataSetChanged();
    }
}
