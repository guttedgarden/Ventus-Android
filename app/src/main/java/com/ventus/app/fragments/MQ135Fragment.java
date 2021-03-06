package com.ventus.app.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
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
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ventus.app.R;
import com.ventus.app.db.MyConstants;
import com.ventus.app.tools.Shared;
import com.ventus.app.tools.arrayList;
import com.ventus.app.tools.convert;
import com.ventus.app.tools.differentSize;
import com.ventus.app.tools.okHTTP;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MQ135Fragment extends Fragment {


    public MQ135Fragment() {
        // Required empty public constructor
    }
    public static Context contextOfApplication;

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
        final TextView upperTextWithInfo = (TextView) v.findViewById(R.id.upperTextWithInfo);
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
        upperTextWithInfo.setText(R.string.mq135_info);
        okHTTP mq135 = new okHTTP();
        final int textSize = Math.round(firstInfoText.getTextSize());
        mq135.run("http://"+Shared.getStringPreferences(getActivity(),"URL_REQUEST_CONFIG3")+"/mq135", new Callback() {
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
                                firstInfoText.setText(differentSize.spannableTwoString("?? ????????????????", textSize, myResponse,
                                        textSize * 4, "???", textSize + 10));
                                secondInfoText.setText(differentSize.spannableTwoString("???????????????? ????????????????????????", textSize, convert.ppmToMg(myResponse, 44.01),
                                        textSize * 4, "????/??", textSize + 10));
                                secondInfoText.append(Html.fromHtml("<sup><small>3</sup></small>"));
                                thirdInfoText.setText(differentSize.spannableTwoString("???????????????? ????????", textSize, convert.ppmToVolumeFraction(myResponse),
                                        textSize * 4, "%", textSize + 10));
                                fourInfoText.setText(differentSize.spannableTwoString("????????", textSize, convert.ppmToLowExplosionLevel(myResponse, 0.0),
                                        textSize * 4, "%", textSize + 10));
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
        LineDataSet lineDataSet = new LineDataSet(arrayList.getArrayListFromDB(getActivity(), MyConstants.MQ135),"ppm");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);

        lineChart.invalidate();

        lineChart.setNoDataText("????????????????...");
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


                        //???????????????? ??????????????????
                        Shared.saveStringPreferences(getActivity(),"FRAGMENT_STATUS","MQ135");
                        Toast.makeText(getActivity(),Shared.getStringPreferences(getActivity(),"FRAGMENT_STATUS"),Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent("android.intent.action.ChartActivity");
                        startActivity(intent);
                    }
                }
        );
    }
}