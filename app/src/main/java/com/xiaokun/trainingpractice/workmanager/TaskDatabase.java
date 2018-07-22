package com.xiaokun.trainingpractice.workmanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by 肖坤 on 2018/7/22.
 *
 * @author 肖坤
 * @date 2018/7/22
 */
@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase
{
    public static final String DB_NAME = "task.db";
    private static volatile TaskDatabase instance;

    public abstract TaskDao taskModel();

    public static TaskDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (TaskDatabase.class)
            {
                if (instance == null)
                {
                    instance = Room.databaseBuilder(context, TaskDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }

}
