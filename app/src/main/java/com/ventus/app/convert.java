package com.ventus.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class convert {
    public static double convertToDouble(String temp){
        String a = temp;
        //replace all commas if present with no comma
        String s = a.replaceAll(",","").trim();
        // if there are any empty spaces also take it out.
        String f = s.replaceAll(" ", "");
        //now convert the string to double
        double result = Double.parseDouble(f);
        return result; // return the result
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static String ppmToMg (String ppm, Double MolarMass){
        double ppmDouble = convertToDouble(ppm);
        ppmDouble = ppmDouble*((MolarMass/29.0)*1.2);
        String mg = String.valueOf(round(ppmDouble,1));
        return mg;
    }
    public static String ppmToVolumeFraction (String ppm){
        double ppmDouble = convertToDouble(ppm);
        ppmDouble = ppmDouble / 10000.0;
        String VolumeFraction = String.valueOf(round(ppmDouble,2));
        return VolumeFraction;
    }
    public static String ppmToLowExplosionLevel (String ppm, Double MaxConcentrationOfaComponentInAir ){
        double ppmDouble = convertToDouble(ppm);
        ppmDouble = MaxConcentrationOfaComponentInAir*convertToDouble(ppmToVolumeFraction(ppm))*10;
        String LowExplosionLevel = String.valueOf(round(ppmDouble,1));
        return LowExplosionLevel;
    }

    public static String getTemperature(String all){
        String[] values = all.split(" ");
        return values[0];
    }

    public static String getHum(String all){
        String[] values = all.split(" ");
        return values[1];
    }

    public static String getMQ135(String all){
        String[] values = all.split(" ");
        return values[2];
    }

    public static String getMQ4(String all){
        String[] values = all.split(" ");
        return values[3];
    }
    public static String getMQ7(String all){
        String[] values = all.split(" ");
        return values[4];
    }
}
