package atirek.com.servicetesting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SensorService extends Service {

    public int counter = 0;
    String TAG = getClass().getSimpleName();

    public SensorService() {
        Log.i(TAG, "here I am!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy!");
        restartSensor();
        //restart();
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "onTaskRemoved!");
        restartSensor();
        //restart();
    }

    void restartSensor() {
        stopTimerTask();
        Intent broadcastIntent = new Intent("RestartSensor");
        sendBroadcast(broadcastIntent);
    }

    void restart() {

        stopTimerTask();
        Intent broadcastIntent = new Intent("RestartSensor");
        PendingIntent restartPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, broadcastIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), restartPendingIntent);
    }

    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1000, 1000);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i(TAG, "in timer ++++  " + (counter++));
            }
        };
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
