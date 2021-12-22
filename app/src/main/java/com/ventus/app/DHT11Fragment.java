package com.ventus.app;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class DHT11Fragment extends Fragment {


    public DHT11Fragment() {
        // Required empty public constructor
    }
    public static Context contextOfApplication;

    public static String testTemperature = "11.1";
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);

        onClickSettingsButton(v);onChartSettingsButton(v);


        /**
         *
         * INIT
         *
         */
        final TextView upperTextWithInfo = (TextView) v.findViewById(R.id.upperTextWithInfo); upperTextWithInfo.setText(R.string.dht11_info);
        final TextView firstInfoText = (TextView) v.findViewById(R.id.firstInfoText);
        final TextView secondInfoText = (TextView) v.findViewById(R.id.secondInfoText);
        final TextView thirdInfoText = (TextView) v.findViewById(R.id.thirdInfoText);
        final TextView fourInfoText = (TextView) v.findViewById(R.id.fourInfoText);
        final TextView emoInfoText = (TextView) v.findViewById(R.id.emoInfoText);
        final LineChart lineChart = (LineChart) v.findViewById(R.id.lineChart);
        if ((Shared.existPreferences(getContext(),"URL_REQUEST_CONFIG5"))){
            emoInfoText.setText(Shared.getStringPreferences(getContext(),"URL_REQUEST_CONFIG5"));
        }
        /**
         *
         *
         *
         */

        /**
         *
         * Request
         *
         */
        okHTTP dht11 = new okHTTP();
        final int textSize = Math.round(firstInfoText.getTextSize());
        dht11.run("http://"+Shared.getStringPreferences(getActivity(),"URL_REQUEST_CONFIG4")+"/dht11", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            if (this != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] arrSplit = myResponse.split(" ");
                                        String temp = arrSplit[0], hum = arrSplit[1];
                                        firstInfoText.setText(differentSize.spannableTwoString("В помещении", textSize, String.valueOf(convert.round(convert.convertToDouble(temp), 1)),
                                                textSize * 4, "°C", textSize + 10));
                                        secondInfoText.setText(differentSize.spannableTwoString("Влажность", textSize, String.valueOf(convert.round(convert.convertToDouble(hum), 1)),
                                                textSize * 4, "°C", textSize + 10));
                                    }
                                });
                            }
                        }
                    }
                });


        okHTTP openWeather = new okHTTP();
        openWeather.run("https://api.openweathermap.org", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    System.out.println("KEY " + myResponse);
                    if (this != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject(myResponse);
                                    JSONArray array = json.getJSONArray("weather");
                                    JSONObject object = array.getJSONObject(0);
                                    String description = object.getString("description");

                                    JSONObject temp1 = json.getJSONObject("main");
                                    Double temperature = temp1.getDouble("temp");
                                    Double humidity = temp1.getDouble("humidity");

                                    thirdInfoText.setText(differentSize.spannableTwoString("На улице", textSize, Double.toString(temperature), textSize * 4, "°C", textSize + 10));
                                    fourInfoText.setText(differentSize.spannableTwoString("Влажность", textSize, Double.toString(humidity), textSize * 4, "%", textSize + 10));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        });
        /**
         *
         *
         *
         */

        /**
         *
         * CHART
         *
         */
        LineDataSet lineDataSet = new LineDataSet(arrayList.lineChartDataSetRandom(),"Temperature");
        ArrayList <ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);

        lineChart.invalidate();

        lineChart.setNoDataText("Загрузка...");
        lineChart.getDescription().setEnabled(false);
        lineDataSet.setValueTextColor(ContextCompat.getColor(getActivity(),R.color.fontColor));
        lineDataSet.setColor(ContextCompat.getColor(getActivity(),R.color.itemBackground));
        YAxis yAxisRight = lineChart.getAxisRight(); yAxisRight.setTextColor(ContextCompat.getColor(getActivity(),R.color.fontColor));
        YAxis yAxisLeft = lineChart.getAxisLeft(); yAxisLeft.setTextColor(ContextCompat.getColor(getActivity(),R.color.fontColor));
        XAxis xAxis = lineChart.getXAxis(); xAxis.setTextColor(ContextCompat.getColor(getActivity(),R.color.fontColor));
        Legend l = lineChart.getLegend(); l.setTextColor(ContextCompat.getColor(getActivity(),R.color.fontColor));
        /**
         *
         *
         *
         */
        return v;
    }





    private void onClickSettingsButton(View view) {
        final ImageButton settingsButton = (ImageButton) view.findViewById(R.id.settingButton);
        settingsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.intent.action.SettingsActivity");
                        startActivity(intent);
                    }
                }
        );
    }
    private void onChartSettingsButton(View view) {
        final ImageButton chartButton = (ImageButton) view.findViewById(R.id.chartButton);
        chartButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Проверка фрагмента
                        Shared.saveStringPreferences(getActivity(),"FRAGMENT_STATUS","DHT11");
                        Toast.makeText(getActivity(),Shared.getStringPreferences(getActivity(),"FRAGMENT_STATUS"),Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent("android.intent.action.ChartActivity");
                        startActivity(intent);
                    }
                }
        );
    }
}
