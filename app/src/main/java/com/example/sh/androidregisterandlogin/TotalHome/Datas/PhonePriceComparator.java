package com.example.sh.androidregisterandlogin.TotalHome.Datas;

import java.util.ArrayList;
import java.util.Comparator;

public class PhonePriceComparator implements Comparator<PhonePriceDataItem> {

    @Override
    public int compare(PhonePriceDataItem first, PhonePriceDataItem second) {

        if(first.getMy_sell_money() == second.getMy_sell_money()){

            int firstOne = Integer.parseInt(first.getSell_money());
            int secondTwo = Integer.parseInt(second.getSell_money());

            return firstOne - secondTwo;
        }else {
            return first.getMy_sell_money().compareTo(second.getMy_sell_money());

        }
    }
}
