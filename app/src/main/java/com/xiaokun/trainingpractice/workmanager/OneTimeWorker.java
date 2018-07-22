package com.xiaokun.trainingpractice.workmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;

/**
 * Created by 肖坤 on 2018/7/22.
 *
 * @author 肖坤
 * @date 2018/7/22
 */

public class OneTimeWorker extends Worker
{
    private static final String TAG = "OneTimeWorker";

    @NonNull
    @Override
    public Result doWork()
    {
        Data inputData = getInputData();
        long millis = inputData.getLong(MainViewModel.KEY_TIME, 0);
        Log.i(TAG, "doWork: " + millis);

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), (int) millis, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        } else
        {
            alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        }
        return Result.SUCCESS;
    }
}
