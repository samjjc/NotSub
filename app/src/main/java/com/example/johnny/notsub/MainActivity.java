package com.example.johnny.notsub;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SimpleDateFormat df;
    StringBuilder sb;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        test = (TextView) findViewById(R.id.test);
        sb = new StringBuilder();
    }

    public void getUsage(View view){
        final UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);

        final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, cal.getTimeInMillis(), System.currentTimeMillis());
        System.out.println("results for " + df.format(cal.getTime()) + " GMT" + " - " + System.currentTimeMillis());
        for (UsageStats app : queryUsageStats) {
            sb.append(app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000) + " | " + df.format(app.getLastTimeUsed()));
        }
        test.setText(sb.toString());
    }

    public void checkPermission (View view){
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }
}
