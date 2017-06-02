package atirek.com.servicetesting;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Intent serviceIntent;
    String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceIntent = new Intent(this, SensorService.class);
        if (!isMyServiceRunning(SensorService.class)) {
            startService(serviceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i(TAG, "isMyServiceRunning: " + true);
                return true;
            }
        }
        Log.i(TAG, "isMyServiceRunning: " + false);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

/*
        if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            startActivity(intent);
        }
*/

    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        Log.i(TAG, "onDestroy!");
        super.onDestroy();

    }
}