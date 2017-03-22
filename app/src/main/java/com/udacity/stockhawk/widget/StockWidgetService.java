package com.udacity.stockhawk.widget;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DecimalFormat;
import android.os.Binder;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

/**
 * Created by Shubham on 14-03-2017.
 */

public class StockWidgetService extends RemoteViewsService{

    public class StockData{

        String symbol;
        String price;
        String absolute_change;
        String percentage_change;
        String history;
    }
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor mCursorData = null;

            @Override
            public void onCreate() {


            }

            @Override
            public void onDataSetChanged() {

                if (mCursorData != null)
                    mCursorData.close();
                final long identityToken = Binder.clearCallingIdentity();
                    mCursorData = getContentResolver().query(Contract.Quote.URI,
                            Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),
                            null, null, Contract.Quote.COLUMN_SYMBOL);
                Binder.restoreCallingIdentity(identityToken);
                for(int i=0; i<mCursorData.getCount(); i++)
                {
                    mCursorData.moveToNext();
                    System.out.println(mCursorData.getString(Contract.Quote.POSITION_SYMBOL));
                }

            }

            @Override
            public void onDestroy() {
                if (mCursorData != null) {
                    mCursorData.close();
                    mCursorData = null;
                }

            }

            @Override
            public int getCount() {
                return mCursorData.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                RemoteViews rv = new RemoteViews(getPackageName(), R.layout.widget_item);

                if (mCursorData.moveToPosition(position)) {
                    rv.setTextViewText(R.id.symbol, mCursorData.getString(Contract.Quote.POSITION_SYMBOL));
                    rv.setTextViewText(R.id.price, mCursorData.getString(Contract.Quote.POSITION_PRICE));

                    float absoluteChange = mCursorData.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);

                    if (absoluteChange > 0)
                        rv.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
                    else
                        rv.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);

                }
                StockData stockData = new StockData();
                stockData.symbol = mCursorData.getString(1);
                stockData.price = mCursorData.getString(2);
                stockData.absolute_change = mCursorData.getString(3);
                stockData.percentage_change = mCursorData.getString(4);
                stockData.history = mCursorData.getString(5);

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra(Contract.Quote.COLUMN_SYMBOL, stockData.symbol);
                fillInIntent.putExtra(Contract.Quote.COLUMN_PRICE, stockData.price);
                fillInIntent.putExtra(Contract.Quote.COLUMN_ABSOLUTE_CHANGE, stockData.absolute_change);
                fillInIntent.putExtra(Contract.Quote.COLUMN_PERCENTAGE_CHANGE, stockData.percentage_change);
                fillInIntent.putExtra(Contract.Quote.COLUMN_HISTORY, stockData.history);

                rv.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                return rv;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };

    }
}
