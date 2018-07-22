package com.xiaokun.trainingpractice.workmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xiaokun.trainingpractice.R;

/**
 * Created by 肖坤 on 2018/7/22.
 *
 * @author 肖坤
 * @date 2018/7/22
 */

public class WorkerManagerActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "SecondActivity";
    private Button mButton;
    private Button mButton2;
    private MainViewModel mMainViewModel;
    public static final long SECOND = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_manager);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        initView();
    }

    private void initView()
    {
        mButton = findViewById(R.id.button);
        mButton2 = findViewById(R.id.button2);

        initListener(mButton, mButton2);
    }

    private void initListener(View... views)
    {
        for (View view : views)
        {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view)
    {
//        Log.i("OneTimeWorker", "onClick: ");
        switch (view.getId())
        {
            case R.id.button:
                long time = System.currentTimeMillis() + 20 * SECOND;
                Task task = new Task(time, "半分钟的延迟任务");
                mMainViewModel.executeOneTimeTask(task);
//                setMultiAlarm(time);
                break;
            case R.id.button2:
                long time1 = System.currentTimeMillis() + 30 * SECOND;
                Task task1 = new Task(time1, "一分钟的延迟任务");
                mMainViewModel.executeOneTimeTask(task1);
//                setMultiAlarm(time1);
                break;
            default:
                break;
        }
    }

    private void setMultiAlarm(long time)
    {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), (int) time, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else
        {
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
    }

}
