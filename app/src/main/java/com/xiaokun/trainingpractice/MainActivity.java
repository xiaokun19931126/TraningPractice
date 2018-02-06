package com.xiaokun.trainingpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                load();
            }
        }).start();
    }

    private static final String TAG = "MainActivity";

    private void load()
    {
        String url = "https://publicobject.com/helloworld.txt";
        url = "http://imtt.dd.qq.com/16891/8EE1D586937A31F6E0B14DA48F8D362E.apk?fsname=com.dewmobile.kuaiya_5.4.2(CN)_216.apk&csr=1bbd";
        url = "http://gank.io/api/data/Android/10/1";
        final Request request = new Request.Builder()
                .url(url)
                .build();

        final ProgressListener progressListener = new ProgressListener()
        {
            @Override
            public void update(long bytesRead, long contentLength, boolean done)
            {
                if (done)
                {
                    Log.e(TAG, "complete");
                } else
                {
                    if (contentLength == -1)
                    {
                        Log.e(TAG, "content-length: unknown");
                    } else
                    {
                        Log.e(TAG, "content-length: " + contentLength);
                    }

                    if (contentLength != -1)
                    {
                        Log.e(TAG, "下载进度：" + (100 * bytesRead) / contentLength);
                    }

                }
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor()
                {
                    @Override
                    public Response intercept(Chain chain) throws IOException
                    {
                        Response originalResponse = chain.proceed(chain.request());

                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                }).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Log.e(TAG, "onResponse" + response.body().string());
            }
        });
    }

    interface ProgressListener
    {
        void update(long bytesRead, long contentLength, boolean done);
    }

    private static class ProgressResponseBody extends ResponseBody
    {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener)
        {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Nullable
        @Override
        public MediaType contentType()
        {
            return responseBody.contentType();
        }

        @Override
        public long contentLength()
        {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source()
        {
            if (bufferedSource == null)
            {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source)
        {
            return new ForwardingSource(source)
            {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException
                {
                    long bytesRead = super.read(sink, byteCount);
                    //read() 返回读取的字节数量,或者是当流读取完毕时返回-1
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

}
