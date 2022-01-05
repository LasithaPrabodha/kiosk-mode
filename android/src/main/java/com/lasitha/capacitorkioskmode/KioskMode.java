package com.lasitha.capacitorkioskmode;


import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.app.admin.SystemUpdatePolicy;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.UserManager;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.Nullable;

import com.getcapacitor.BridgeActivity;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

public class KioskMode extends BridgeActivity {

    private ComponentName mAdminComponentName;
    private DevicePolicyManager mDevicePolicyManager;
    private String packageName = "com.lasitha.capacitorkioskmode";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        mAdminComponentName = MyDeviceAdminReceiver.getComponentName(this);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        mDevicePolicyManager.removeActiveAdmin(mAdminComponentName);

    }

    public Boolean isAdmin() {
        return mDevicePolicyManager.isDeviceOwnerApp(packageName);
    }

    public void setKioskPolicies(Boolean enable, Boolean isAdmin) {
        if (isAdmin) {
            setRestrictions(enable);
            enableStayOnWhilePluggedIn(enable);
            setUpdatePolicy(enable);
            setAsHomeApp(enable);
            setKeyGuardEnabled(enable);
        }
        setLockTask(enable, isAdmin);
        setImmersiveMode(enable);
    }

    // region restrictions
    private void setRestrictions(Boolean disallow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setUserRestriction(UserManager.DISALLOW_SAFE_BOOT, disallow);
        }
        setUserRestriction(UserManager.DISALLOW_FACTORY_RESET, disallow);
        setUserRestriction(UserManager.DISALLOW_ADD_USER, disallow);
        setUserRestriction(UserManager.DISALLOW_MOUNT_PHYSICAL_MEDIA, disallow);
        setUserRestriction(UserManager.DISALLOW_ADJUST_VOLUME, disallow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mDevicePolicyManager.setStatusBarDisabled(mAdminComponentName, disallow);
        }
    }

    private void setUserRestriction(String restriction, Boolean disallow) {
        if (disallow) {
            mDevicePolicyManager.addUserRestriction(mAdminComponentName, restriction);
        } else {
            mDevicePolicyManager.clearUserRestriction(mAdminComponentName, restriction);
        }
    }
    // endregion


    private void enableStayOnWhilePluggedIn(Boolean active) {

        if (active) {
            mDevicePolicyManager.setGlobalSetting(
                    mAdminComponentName,
                    Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
                    String.valueOf((BatteryManager.BATTERY_PLUGGED_AC
                            | BatteryManager.BATTERY_PLUGGED_USB
                            | BatteryManager.BATTERY_PLUGGED_WIRELESS))
            );
        } else {
            mDevicePolicyManager.setGlobalSetting(mAdminComponentName, Settings.Global.STAY_ON_WHILE_PLUGGED_IN, "0");
        }
    }

    private void setLockTask(Boolean start, Boolean isAdmin) {
        if (isAdmin) {
            mDevicePolicyManager.setLockTaskPackages(
                    mAdminComponentName, start ? new String[]{packageName} :
                            new String[]{}
            );
        }
        if (start) {
            startLockTask();
        } else {
            stopLockTask();
        }
    }

    private void setUpdatePolicy(Boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (enable) {
                mDevicePolicyManager.setSystemUpdatePolicy(
                        mAdminComponentName,
                        SystemUpdatePolicy.createWindowedInstallPolicy(60, 120)
                );
            } else {
                mDevicePolicyManager.setSystemUpdatePolicy(mAdminComponentName, null);

            }

        }
    }

    private void setAsHomeApp(Boolean enable) {
        if (enable) {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MAIN);

            intentFilter.addCategory(Intent.CATEGORY_HOME);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            mDevicePolicyManager.addPersistentPreferredActivity(
                    mAdminComponentName, intentFilter, new ComponentName(packageName, KioskMode.
                            class.getName())
            );
        } else {
            mDevicePolicyManager.clearPackagePersistentPreferredActivities(
                    mAdminComponentName, packageName
            );
        }
    }

    private void setKeyGuardEnabled(Boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mDevicePolicyManager.setKeyguardDisabled(mAdminComponentName, !enable);
        }
    }

    private void setImmersiveMode(Boolean enable) {
        if (enable) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public IntentSender createIntentSender(Context context, int sessionId, String packageName) {
        Intent intent = new Intent("INSTALL_COMPLETE");
        if (packageName != null) {
            intent.putExtra("PACKAGE_NAME", packageName);
        }
        PendingIntent pendingIntent = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(
                    context,
                    sessionId,
                    intent,
                    FLAG_IMMUTABLE
            );
        }
        if (pendingIntent != null) {
            return pendingIntent.getIntentSender();
        } else {
            return null;
        }
    }

}
