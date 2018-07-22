package com.xiaokun.trainingpractice.workmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

/**
 * Created by 肖坤 on 2018/7/22.
 *
 * @author 肖坤
 * @date 2018/7/22
 */

public class MainViewModel extends AndroidViewModel
{
    private static final String TAG = "MainViewModel";
    private final WorkManager mWorkManager;
    public static final String KEY_TIME = "TIME";
    public static final String KEY_TASK = "TASK";

    public MainViewModel(@NonNull Application application)
    {
        super(application);
        mWorkManager = WorkManager.getInstance();
    }

    public void executeOneTimeTask(Task task)
    {
        Log.i("OneTimeWorker", "executeOneTimeTask: ");
        Data.Builder builder = new Data.Builder();
        builder.putLong(KEY_TIME, task.getDuration());
        builder.putString(KEY_TASK, task.getDesc());
        Data data = builder.build();

        OneTimeWorkRequest mWorkRequest = new OneTimeWorkRequest
                .Builder(OneTimeWorker.class)
//                .setInitialDelay(task.getDuration(), TimeUnit.SECONDS)
                .setInputData(data)
                .build();
        mWorkManager.beginUniqueWork(task.getDesc(), ExistingWorkPolicy.REPLACE, mWorkRequest);

        mWorkManager.enqueue(mWorkRequest);
    }

}
