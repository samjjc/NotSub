package com.example.johnny.notsub;

import android.app.usage.UsageStats;
import android.graphics.drawable.Drawable;

/**
 * Created by Johnny on 2017-02-04.
 */

public class customUsageStats {
    private UsageStats usageStats;
    private Drawable appIcon;

    public customUsageStats (UsageStats u, Drawable d){
        usageStats = u;
        appIcon = d;
    }

    public UsageStats getUsageStats() {
        return usageStats;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setUsageStats(UsageStats usageStats) {
        this.usageStats = usageStats;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
