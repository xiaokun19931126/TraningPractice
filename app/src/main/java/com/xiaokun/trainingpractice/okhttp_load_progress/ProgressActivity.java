package com.xiaokun.trainingpractice.okhttp_load_progress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaokun.trainingpractice.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "ProgressActivity";

    private Button mLoad;
    private TextView mProgress;
    private boolean isFirst = true;
    private TextView mStatus;
    private long startTime;
    private TextView mReceiveTime;
    private TextView mWaitTime;

    private Handler mHandler = new Handler()
    {

        private long endTime;

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    int progress = msg.arg1;
                    if (progress == 0 && isFirst)
                    {
                        isFirst = false;
                        endTime = System.currentTimeMillis();
                        Log.e(TAG, "获得ip+创建套接字+等待接收数据的时间总共:" + (endTime - startTime) + "毫秒");
                        mWaitTime.setText("获得ip+创建套接字+等待接收数据的时间总共:" + (endTime - startTime) + "毫秒");
                    }
                    mProgress.setText("加载进度:" + progress + "%");
                    if (progress == 100)
                    {
                        mStatus.setText("加载完成");
                        Log.e(TAG, "接收数据的时间总共：" + (System.currentTimeMillis() - endTime) + "毫秒");
                        mReceiveTime.setText("接收数据的时间总共：" + (System.currentTimeMillis() - endTime) + "毫秒");
                    }
                    break;
                default:

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        initView();
    }

    private void initView()
    {
        mLoad = (Button) findViewById(R.id.load);
        mProgress = (TextView) findViewById(R.id.progress);
        mStatus = (TextView) findViewById(R.id.status);
        mReceiveTime = (TextView) findViewById(R.id.receive_time);
        mWaitTime = (TextView) findViewById(R.id.wait_time);
        initListener(mLoad);
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
        switch (view.getId())
        {
            case R.id.load:
                isFirst = true;
                startTime = System.currentTimeMillis();
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        load();
                    }
                }).start();
                mProgress.setText("加载进度:" + 0 + "%");
                break;
            default:
                break;
        }
    }

    private void load()
    {
        String url = "https://publicobject.com/helloworld.txt";
        url = "http://imtt.dd.qq.com/16891/8EE1D586937A31F6E0B14DA48F8D362E.apk?fsname=com.dewmobile.kuaiya_5.4.2(CN)_216.apk&csr=1bbd";
        url = "http://gank.io/api/data/Android/10/1";

        url = " http://192.168.1.236:10200/api/AppVehicleData/GetRegionalVehicleFromRectangle";

        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        String body = "{\"App_Key\":\"wh_ztc\",\"DepartmentId\":0,\"DevStatus\":0,\"IgnorePage\":false,\"IsMainDev\":0,\"PageIndex\":1,\"PageSize\":20,\"PlatformId\":2,\"ResourceTypeId\":0,\"VehicleType\":0,\"isNotDevice\":false}";

        RequestBody requestBody = RequestBody.create(contentType, body);


        final Request request = new Request.Builder()
                .addHeader("token", "A53B87CFFA4D4EF4A4689B5F1D7116C6")
                .addHeader("Content-Type", "application/json")
                .url(url)
                .post(requestBody)
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
                        int progress = (int) ((100 * bytesRead) / contentLength);
                        Message msg = Message.obtain();
                        msg.arg1 = progress;
                        msg.what = 0;
                        mHandler.sendMessage(msg);
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
                Message msg = Message.obtain();
                msg.what = 1;
                mHandler.sendMessage(msg);
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
