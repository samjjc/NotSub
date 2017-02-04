package com.example.johnny.notsub;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void findApps(View view){
        Intent intent = new Intent (this, AppsActivity.class);
        startActivity(intent);
    }

    public void demo (View view){
        Intent intent = new Intent (this, DemoActivity.class);
        startActivity(intent);
    }

    public void checkPermission (View view){
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }
}
