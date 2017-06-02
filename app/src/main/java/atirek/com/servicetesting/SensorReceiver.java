package atirek.com.servicetesting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(SensorReceiver.class.getSimpleName(), "Service Restarting!");
        SensorService service = new SensorService();
        context.startService(new Intent(context, service.getClass()));
    }
}
