package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shubham on 07-03-2017.
 */

public class StockDetailActivity extends AppCompatActivity {

    //@BindView(R.id.for_testing_data)
    //TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stock_detail_layout);
        //ButterKnife.bind(this);
        Intent activityThatCalledThisActivity = getIntent();


            String symbol = activityThatCalledThisActivity.getStringExtra(Contract.Quote.COLUMN_SYMBOL);
            String price = activityThatCalledThisActivity.getStringExtra(Contract.Quote.COLUMN_PRICE);
            String absoluteChange = activityThatCalledThisActivity.getStringExtra(Contract.Quote.COLUMN_ABSOLUTE_CHANGE);
            String percentageChange = activityThatCalledThisActivity.getStringExtra(Contract.Quote.COLUMN_PERCENTAGE_CHANGE);
            String history = activityThatCalledThisActivity.getStringExtra(Contract.Quote.COLUMN_HISTORY);

            String[] allStocks = history.split("\n");
            String[] singleStock;

        LineChart chart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();

        final String [] dateString = new String[allStocks.length];
        for(int i=0; i<allStocks.length; i++)
            {
                singleStock = allStocks[i].split(",");
                Date date = new Date(Long.parseLong(singleStock[0]));
                System.out.println(date);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                entries.add(new Entry(i, Float.parseFloat(singleStock[1])));
                dateString[allStocks.length -1 - i] = df.format(date);
                System.out.println(i);
            }

        IAxisValueFormatter formatter = new IAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dateString[(int) value];
            }

            // we don't draw numbers, so no decimal digits needed
        };
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        System.out.println(formatter);
        chart.setData(lineData);
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setTextColor(Color.WHITE);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTextColor(Color.WHITE);
        chart.invalidate();


    }
}
