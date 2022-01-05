package com.lasitha.capacitorkioskmode;

import android.annotation.SuppressLint;
import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    private String TAG = MyDeviceAdminReceiver.class.getSimpleName();

    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), MyDeviceAdminReceiver.class);
    }
}
