package com.xiaokun.trainingpractice.okhttp_download_file;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiaokun.trainingpractice.R;

public class DownLoadProgressaActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button mStart;
    private Button mPause;
    private Button mCancel;
    private DownloadService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initView();
        initService();
        initPermission();
    }

    private void initView()
    {
        mStart = (Button) findViewById(R.id.start);
        mPause = (Button) findViewById(R.id.pause);
        mCancel = (Button) findViewById(R.id.cancel);
        initListener(mStart, mPause, mCancel);
    }

    private void initListener(View... views)
    {
        for (View view : views)
        {
            view.setOnClickListener(this);
        }
    }

    private void initService()
    {
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);//qidong
        bindService(intent, mConn, BIND_AUTO_CREATE);//bangding
    }

    private void initPermission()
    {
        //0 granted  -1 denied
        int permission = ContextCompat.checkSelfPermission(DownLoadProgressaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private ServiceConnection mConn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(DownLoadProgressaActivity.this, "queshao quanxian", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void onClick(View view)
    {
        if (downloadBinder == null)
        {
            return;
        }

        switch (view.getId())
        {
            case R.id.start:
                String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                url = "http://imtt.dd.qq.com/16891/8EE1D586937A31F6E0B14DA48F8D362E.apk?fsname=com.dewmobile.kuaiya_5.4.2(CN)_216.apk&csr=1bbd";
                downloadBinder.startDownload(url);
                break;
            case R.id.pause:
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        unbindService(mConn);
        super.onDestroy();
    }
}
