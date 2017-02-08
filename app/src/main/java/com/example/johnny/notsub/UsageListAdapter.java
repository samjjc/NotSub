package com.example.johnny.notsub;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Johnny on 2017-02-04.
 */

public class UsageListAdapter extends ArrayAdapter<customUsageStats> {
    SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy");
    Context context;

    public UsageListAdapter(Context c, ArrayList<customUsageStats> usageAdapter) {
        super(c, 0, usageAdapter);
        context = c;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        customUsageStats currentStat = getItem(position);

        ImageView icon = (ImageView) listItemView.findViewById(R.id.app_icon);
        if(currentStat.getAppIcon()!= null) {
            icon.setImageDrawable(currentStat.getAppIcon());
            icon.setVisibility(View.VISIBLE);
        }else{
            icon.setVisibility(View.GONE);
        }
        TextView name = (TextView) listItemView.findViewById(R.id.textview_package_name);
        String packageName =currentStat.getUsageStats().getPackageName();
        PackageManager packageManager= context.getPackageManager();
        String appName= " ";
        try {
            appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        name.setText(appName);

        TextView lastTime = (TextView) listItemView.findViewById(R.id.textview_last_time_used);
        lastTime.setText(df.format(currentStat.getUsageStats().getLastTimeUsed()));




        return listItemView;
    }
}
