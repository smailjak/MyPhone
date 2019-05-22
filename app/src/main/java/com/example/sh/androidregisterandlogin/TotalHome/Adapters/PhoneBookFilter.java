package com.example.sh.androidregisterandlogin.TotalHome.Adapters;


import android.util.Log;
import android.widget.Filter;

import com.example.sh.androidregisterandlogin.TotalHome.Datas.AddressDataItem;

import java.util.ArrayList;

public class PhoneBookFilter extends Filter {

    PhonebookAdapter phonebookAdapter;
    ArrayList<AddressDataItem> filterList;

    public PhoneBookFilter(ArrayList<AddressDataItem> filterList, PhonebookAdapter adapter) {
        this.phonebookAdapter = adapter;
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
            ArrayList<AddressDataItem> filteredAddress = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
//                    add additionalFeature to filtered models
                    filteredAddress.add(filterList.get(i));
                    Log.d("qweqwe", "performFiltering: " + filterList.get(i).getName());
                }
            }
            results.count = filteredAddress.size();
            results.values = filteredAddress;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        phonebookAdapter.addressDataItemArrayList = (ArrayList<AddressDataItem>) results.values;
//       refresh
        phonebookAdapter.notifyDataSetChanged();
    }
}
