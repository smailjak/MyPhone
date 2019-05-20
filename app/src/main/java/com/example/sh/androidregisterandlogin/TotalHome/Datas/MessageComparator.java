package com.example.sh.androidregisterandlogin.TotalHome.Datas;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by SHAJIB on 7/12/2017.
 */

public class MessageComparator implements Comparator<HashMap<String, String>> {
    private final String key;
    private final String order;

    public MessageComparator(String key, String order) {
        this.key = key;
        this.order = order;
    }

    public int compare(HashMap<String, String> first,
                       HashMap<String, String> second) {
        // TODO: Null checking, both for maps and values
        String firstValue = first.get(key);
        String secondValue = second.get(key);
        if (this.order.toLowerCase().contentEquals("asc")) {
            return firstValue.compareTo(secondValue);
        } else {
            return secondValue.compareTo(firstValue);
        }
    }
}