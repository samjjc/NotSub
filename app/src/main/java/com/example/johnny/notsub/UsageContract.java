package com.example.johnny.notsub;

import android.provider.BaseColumns;

/**
 * Created by Johnny on 2017-02-04.
 */

public class UsageContract {

    public static abstract class AppEntry implements BaseColumns {
        public static final String TABLE_NAME = "Packages";

        public static final String COLUMN_PACKAGE_NAME = "package";
    }
}
