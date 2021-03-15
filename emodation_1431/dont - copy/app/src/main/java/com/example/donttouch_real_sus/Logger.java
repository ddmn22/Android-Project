package com.example.donttouch_real_sus;

import android.util.Log;

public class Logger {
    private static final String TAG = "Recognition";

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void stamp() {
        Log.e(TAG, "");
    }
}
