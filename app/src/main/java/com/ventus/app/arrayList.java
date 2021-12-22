package com.ventus.app;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class arrayList {
    public static ArrayList<Entry> lineChartDataSetRandom(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();
        for (int i = 0; i < 50; i++) {
            dataSet.add(new Entry(i,(float)getRandomNumber()));
        }
        return dataSet;
    }
    public static double getRandomNumber(){
        double x = Math.random();
        return convert.round(x,1);
    }
}
