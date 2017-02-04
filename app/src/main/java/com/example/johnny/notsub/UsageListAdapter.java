package com.example.johnny.notsub;

import android.content.Context;
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
    SimpleDateFormat df = new SimpleDateFormat("EEE, MMMM d, yyyy");

    public UsageListAdapter(Context context, ArrayList<customUsageStats> usageAdapter) {
        super(context, 0, usageAdapter);
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
        name.setText(currentStat.getUsageStats().getPackageName());

        TextView lastTime = (TextView) listItemView.findViewById(R.id.textview_last_time_used);
        lastTime.setText(df.format(currentStat.getUsageStats().getLastTimeUsed()));




        return listItemView;
    }
}
