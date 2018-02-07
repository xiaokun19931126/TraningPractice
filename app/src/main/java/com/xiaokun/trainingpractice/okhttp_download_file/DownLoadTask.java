package com.xiaokun.trainingpractice.okhttp_download_file;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/02
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class DownLoadTask extends AsyncTask<String, Integer, Integer>
{

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSE = 2;
    public static final int TYPE_CANCELED = 3;

    private DownLoadListener listener;

    private boolean isCanceled = false;
    private boolean isPaused = false;

    private int lastProgress;

    public DownLoadTask(DownLoadListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        //jilu yixiazai de wenjianchangdu
        long downloadedLength = 0;
        String downloadUrl = params[0];
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        file = new File(directory + fileName);
        if (file.exists())
        {
            downloadedLength = file.length();
        }
        try
        {
            long contentLength = getContentLength(downloadUrl);
            if (contentLength == 0)
            {
                return TYPE_FAILED;
            } else if (contentLength == downloadedLength)
            {
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null)
            {
                Log.e("DownLoadTask", "(DownLoadTask.java:78)" + response.body().contentLength());
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadedLength);
                byte[] bytes = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(bytes)) != -1)
                {
                    if (isCanceled)
                    {
                        return TYPE_CANCELED;
                    } else if (isPaused)
                    {
                        return TYPE_PAUSE;
                    } else
                    {
                        total += len;
                        savedFile.write(bytes, 0, len);
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
            }
            response.body().close();
            return TYPE_SUCCESS;
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
                if (savedFile != null)
                {
                    savedFile.close();
                }
                if (isCanceled && file != null)
                {
                    file.delete();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
        int progress = values[0];
        if (progress > lastProgress)
        {
            lastProgress = progress;
            listener.onProgress(progress);
        }
    }

    @Override
    protected void onPostExecute(Integer status)
    {
        super.onPostExecute(status);
        switch (status)
        {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSE:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:

                break;
        }
    }

    public void pauseDownload()
    {
        isPaused = true;
    }

    public void cancelDownload()
    {
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful())
        {
            long length = response.body().contentLength();
            response.close();
            return length;
        } else
        {
            return 0;
        }
    }
}
