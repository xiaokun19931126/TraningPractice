package com.xiaokun.trainingpractice.workmanager;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by 肖坤 on 2018/7/22.
 *
 * @author 肖坤
 * @date 2018/7/22
 */
@Dao
public interface TaskDao
{

    @Insert(onConflict = IGNORE)
    void insertTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> obtainAllTask();

}
