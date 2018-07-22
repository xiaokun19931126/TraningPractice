package com.xiaokun.trainingpractice.workmanager;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by 肖坤 on 2018/7/22.
 *
 * @author 肖坤
 * @date 2018/7/22
 */
@Entity(tableName = "task")
public class Task
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long duration;
    private String desc;

    public Task(int id, long duration, String desc)
    {
        this.id = id;
        this.duration = duration;
        this.desc = desc;
    }

    @Ignore
    public Task(long duration, String desc)
    {
        this.duration = duration;
        this.desc = desc;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public long getDuration()
    {
        return duration;
    }

    public void setDuration(long duration)
    {
        this.duration = duration;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
