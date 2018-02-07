package com.xiaokun.trainingpractice.okhttp_download_file;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.xiaokun.trainingpractice.R;

import java.io.File;

public class DownloadService extends Service
{
    private DownLoadTask downLoadTask;
    private String downloadUrl;

    private DownLoadListener listener = new DownLoadListener()
    {
        @Override
        public void onProgress(int progress)
        {
            getNotificationManager().notify(1, getNotification("下载中...", progress));
        }

        @Override
        public void onSuccess()
        {
            downLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载完成", -1));
        }

        @Override
        public void onFailed()
        {
            downLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载失败", -1));
        }

        @Override
        public void onPaused()
        {
            downLoadTask = null;
        }

        @Override
        public void onCanceled()
        {
            downLoadTask = null;
            stopForeground(true);
        }
    };

    public DownloadService()
    {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    class DownloadBinder extends Binder
    {
        public void startDownload(String url)
        {
            if (downLoadTask == null)
            {
                downloadUrl = url;
                downLoadTask = new DownLoadTask(listener);
                downLoadTask.execute(downloadUrl);
                startForeground(1, getNotification("下载中...", 0));
            }
        }

        public void pauseDownload()
        {
            if (downLoadTask != null)
            {
                downLoadTask.pauseDownload();
            }
        }

        public void cancelDownload()
        {
            if (downLoadTask != null)
            {
                downLoadTask.cancelDownload();
            } else
            {
                if (downloadUrl != null)
                {
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists())
                    {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                }
            }
        }

    }

    private NotificationManager getNotificationManager()
    {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress)
    {
        Intent intent = new Intent(this, DownLoadProgressaActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if (progress > 0)
        {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }

}
