package com.example.johnny.notsub;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DemoActivity extends AppCompatActivity {
    SimpleDateFormat df;
    UsageDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        df = new SimpleDateFormat("dd MMM yyyy");
        mDbHelper = new UsageDbHelper(this);
        final UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.YEAR, -1);
        Calendar endCal = Calendar.getInstance();

        List<UsageStats> temp = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, beginCal.getTimeInMillis(), endCal.getTimeInMillis());

        if (temp.size() > 0) {

            TextView error = (TextView) findViewById(R.id.error);
            error.setVisibility(View.GONE);
            Collections.sort(temp, new Comparator<UsageStats>() {
                @Override
                public int compare(UsageStats t1, UsageStats t2) {
                    return Long.compare(t1.getLastTimeUsed(), t2.getLastTimeUsed());
                }
            });
            Collections.reverse(temp);

            final ArrayList<customUsageStats> queryUsageStats = new ArrayList<customUsageStats>();
            for (int i = 0; i < temp.size(); i++) {
                UsageStats currentUsage = temp.get(i);
                PackageManager packageManager= this.getPackageManager();
                String appName= " ";
                try {
                    appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(currentUsage.getPackageName(), PackageManager.GET_META_DATA));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                //if(checkWhitelist(appName)) {
                    Drawable appIcon;
                    try {
                        appIcon = this.getPackageManager()
                                .getApplicationIcon(currentUsage.getPackageName());
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.w("Main Activity", String.format("App Icon is not found for %s",
                                currentUsage.getPackageName()));
                        appIcon = this
                                .getDrawable(R.mipmap.ic_launcher);
                    }
                    queryUsageStats.add(new customUsageStats(currentUsage, appIcon));
                //}
            }
            UsageListAdapter itemsAdapter = new UsageListAdapter(this, queryUsageStats);

            ListView listView = (ListView) findViewById(R.id.list);

            listView.setAdapter(itemsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.v("MAIN", "CLICKED");
                    customUsageStats item = queryUsageStats.get(i);
                    insertPackage(item.getUsageStats().getPackageName());
                }
            });
        }
    }

    private void insertPackage (String pack){
        Log.v("MAIN", "Started interting");
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        PackageManager packageManager= this.getPackageManager();
        String appName= " ";
        try {
            appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(pack, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        values.put(UsageContract.AppEntry.COLUMN_PACKAGE_NAME, appName);

        long newRowId = db.insert(UsageContract.AppEntry.TABLE_NAME, null, values);

        if(newRowId == -1){
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Pet saved with row id: "+newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkWhitelist (String pack){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            Cursor c = db.query(UsageContract.AppEntry.TABLE_NAME, null, UsageContract.AppEntry.TABLE_NAME + "=" + pack, null, null, null, null);
        }
        catch(SQLException e){
            return true;
        }
        return true;

        //Cursor c = db.execSQL("SELECT * FROM "+ UsageContract.AppEntry.TABLE_NAME + " WHERE " +UsageContract.AppEntry.COLUMN_PACKAGE_NAME+"="+pack+";");
    }
}

