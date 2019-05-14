package com.example.sh.androidregisterandlogin.TotalHome;

import android.widget.Filter;


import java.util.ArrayList;

public class CustomFilter extends Filter {
//    extends 로 Filter 라는 것을 상속 받았다 .. 그런데 이거 무엇이지 ? 왜 Filter 를 상속받았지?? 뭘 쓸려고 ??

    FragmentMainAdapter fragmentMainAdapter;
    ArrayList<Model> filterList;

    public CustomFilter(ArrayList<Model> filterList, FragmentMainAdapter adapter) {
        this.fragmentMainAdapter = adapter;
        this.filterList = filterList;
    }

    //    filtering occurs
//    여기 함수로 들어가게 되면 전체 아이템을 반복을 돌아서 출력해주는 함수
//    검색기능을 구현했을 때 넣은 Override
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
//        check constraint validity
        if (constraint != null && constraint.length() > 0) {
//            change to upper case
            constraint = constraint.toString().toUpperCase();
//            store our filtered WODELS
            ArrayList<Model> filteredModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
//                check
                if (filterList.get(i).getName().toUpperCase().contains(constraint)) {
//                    add model to filtered models
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    //  검색기능을 구현했을때 , 넣은 함수이며 Override
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        fragmentMainAdapter.modelArrayList = (ArrayList<Model>) results.values;
//       refresh
        fragmentMainAdapter.notifyDataSetChanged();
    }
}
