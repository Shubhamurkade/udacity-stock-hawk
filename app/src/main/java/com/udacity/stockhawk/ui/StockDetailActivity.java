package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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

        ArrayList<DataPoint> points = new ArrayList<>();
        GraphView graph = (GraphView) findViewById(R.id.graph);

        for(int i=0; i<3; i++)
            {
                singleStock = allStocks[i].split(",");
                Date date = new Date(Long.parseLong(singleStock[0]));
                System.out.println(date);
                points.add(new DataPoint(new Date(Long.parseLong(singleStock[0])), Float.parseFloat(singleStock[1])));
            }

        DataPoint[] dbPoint = points.toArray(new DataPoint[points.size()]);
        System.out.println(points);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dbPoint);
        graph.addSeries(series);
        graph.getViewport().setMinX(new Date().getTime());
        graph.getViewport().setMaxX(new Date().getTime());
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        // set manual x bounds to have nice steps

        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        /*
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph);

// you can directly pass Date objects to DataPoint-Constructor
// this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });

        graph.addSeries(series);

// set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

    */
    }
}
