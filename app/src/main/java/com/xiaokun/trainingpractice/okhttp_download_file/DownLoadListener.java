package com.xiaokun.trainingpractice.okhttp_download_file;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/02
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public interface DownLoadListener
{
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
