package com.example.sh.androidregisterandlogin.TotalHome.Adapters;

import android.widget.Filter;


import com.example.sh.androidregisterandlogin.data.AdditionalFeature;

import java.util.ArrayList;

public class MainFilter extends Filter {
    FragmentMainAdapter fragmentMainAdapter;
    ArrayList<AdditionalFeature> filterList;

    public MainFilter(ArrayList<AdditionalFeature> filterList, FragmentMainAdapter adapter) {
        this.fragmentMainAdapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
//        check constraint validity
        if (constraint != null && constraint.length() > 0) {
//            change to upper case
            constraint = constraint.toString().toUpperCase();
//            store our filtered WODELS
            ArrayList<AdditionalFeature> filteredAdditionalFeatures = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
//                    add additionalFeature to filtered models
                    filteredAdditionalFeatures.add(filterList.get(i));
                }
            }
            results.count = filteredAdditionalFeatures.size();
            results.values = filteredAdditionalFeatures;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    //  검색기능을 구현했을때 , 넣은 함수이며 Override
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        fragmentMainAdapter.setItems((ArrayList<AdditionalFeature>)results.values);
        fragmentMainAdapter.notifyDataSetChanged();
    }
}
