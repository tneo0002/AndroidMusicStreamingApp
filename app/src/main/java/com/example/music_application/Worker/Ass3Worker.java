package com.example.music_application.Worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.music_application.BuildConfig;
import com.example.music_application.R;

import java.util.Calendar;
import java.util.Date;

public class Ass3Worker extends Worker {

    private static final String TAG = "ASS3Worker";

    public Ass3Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data workData = getInputData();
        int number  = workData.getInt("Number", -1);
        Date currentTime = Calendar.getInstance().getTime();
        Log.d(TAG, currentTime + "Number: " + number);

        for (int i = 10; i > 0; i--) {
            Log.d(TAG, currentTime + "Number was: " + i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        String task = "Current Android Version Notification";
        String desc = "You are currently working on Android 12";
        DisplayNotification(task, desc);
        Log.d(String.valueOf(currentTime), "Notification generated!");
        return Result.success();
    }

    // Create the job that the work manager would like to do, here use display notification as an example
    private void DisplayNotification(String task, String desc) {
        NotificationManager notiManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // P = Android 9, SDK Version Code is SDK 28
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Create a notification channel, the third parameter means the importance level of this notification
            NotificationChannel notificationChannel = new NotificationChannel("Assignment3_Notification", "Version_Show", NotificationManager.IMPORTANCE_MIN);
            notiManager.createNotificationChannel(notificationChannel);
        }

        // Create the notification
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(getApplicationContext(), "Assignment3_Notification").setContentTitle(task).setContentText(desc).setSmallIcon(R.mipmap.ic_launcher_round);
        notiManager.notify(1, notiBuilder.build());

    }
}
