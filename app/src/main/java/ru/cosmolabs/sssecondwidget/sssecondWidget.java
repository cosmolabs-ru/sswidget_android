package ru.cosmolabs.sssecondwidget;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

public class sssecondWidget extends AppWidgetProvider {

    final String LOG_TAG = "myLogs";
    TextView tv;
    static int uc = 0;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(LOG_TAG, "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        startTimer(context, appWidgetManager, appWidgetIds);
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,int appWidgetId) {
        RemoteViews widgetView = new RemoteViews(context.getPackageName(),
                R.layout.layout);
        TimeZone tz = TimeZone.getTimeZone("GMT-2");
        Calendar c = Calendar.getInstance(tz);
        int year = c.get(Calendar.YEAR) - 1;
        int month =(c.get(Calendar.DAY_OF_YEAR) / 73);
        int date = c.get(Calendar.DAY_OF_YEAR) % 73 - 1;
        int weekday = date % 5;
        String ssdate = String.format(Locale.US, "%d.%d.%d D%d", year, month, date, weekday);
        double ss = (1000.0/864.0)*(c.get(Calendar.HOUR_OF_DAY) * 3600 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND));
        ss /= 1000;
        String s = String.format(Locale.US, "%.3f", ss);
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(0.5f), 4,6, 0); // set size
        widgetView.setTextViewText(R.id.tv2, ss1);
        widgetView.setTextViewText(R.id.tvdate, ssdate);
        appWidgetManager.updateAppWidget(appWidgetId, widgetView);
    }

    Timer myTimer = new Timer();
    private void startTimer(final Context context, final AppWidgetManager appWidgetManager,
                            final int[] appWidgetIds) {
        myTimer.schedule(new TimerTask() {
            @Override
                    public void run() {
                updateAppWidget(context, appWidgetManager, appWidgetIds[0]);
            }
        }, 0, 200);
    }

}
