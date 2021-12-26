package com.ventus.app.tools;

import android.content.Context;

import com.github.mikephil.charting.data.Entry;
import com.ventus.app.db.MyDbManager;

import java.util.ArrayList;

public class arrayList {
    public static MyDbManager myDbManager;

    public static ArrayList<Entry> getArrayListFromDB(Context context, String key){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();
        myDbManager = new MyDbManager(context);
        myDbManager.openDb();
        int i = 0;
        for (String resultString : myDbManager.getFromDb(key)){
            dataSet.add(new Entry(i, Float.parseFloat(resultString)));
            i++;
        }
        myDbManager.closeDb();
        return dataSet;
    }



//    public static ArrayList<Entry> lineChartDataSetRandom(){
//        ArrayList<Entry> dataSet = new ArrayList<Entry>();
//        for (int i = 0; i < 50; i++) {
//            dataSet.add(new Entry(i,(float)getRandomNumber()));
//        }
//        return dataSet;
//    }
//    public static double getRandomNumber(){
//        double x = Math.random();
//        return convert.round(x,1);
//    }
}
