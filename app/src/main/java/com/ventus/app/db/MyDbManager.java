package com.ventus.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }


    //Открытие бд
    public void openDb(){
        db = myDbHelper.getWritableDatabase();
    }

    //Запись в бд
    public void insertToDb(String DHT11_Temp, String DHT11_Hum, String MQ4, String MQ7, String MQ135){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.DHT11_TEMP, DHT11_Temp);
        cv.put(MyConstants.DHT11_HUM, DHT11_Hum);
        cv.put(MyConstants.MQ4, MQ4);
        cv.put(MyConstants.MQ7, MQ7);
        cv.put(MyConstants.MQ135, MQ135);
        db.insert(MyConstants.TABLE_NAME,null, cv);

    }

    //Чтение бд
    public List<String> getFromDb(String columnName){
        List<String> List = new ArrayList<>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, null, null,null,null,null);

        while(cursor.moveToNext()){
            String text = cursor.getString(cursor.getColumnIndex(columnName));
            List.add(text);
        }
        cursor.close();
        return List;
    }

    //Закрытие бд
    public void closeDb(){
        myDbHelper.close();
    }
}
