package com.ventus.app.db;

public class MyConstants {
    public static final String TABLE_NAME = "ventus_table";
    public static final String _ID = "_id";

    public static final String DHT11_TEMP = "dht11Temp";
    public static final String DHT11_HUM = "dht11Hum";
    public static final String MQ4 = "mq4";
    public static final String MQ7 = "mq7";
    public static final String MQ135 = "mq135";

    public static final String DB_NAME = "ventus.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY,"
            + DHT11_TEMP + " TEXT," + DHT11_HUM + " TEXT," + MQ4 + " TEXT," + MQ7 + " TEXT," + MQ135 + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
