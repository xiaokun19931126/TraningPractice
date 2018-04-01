package com.xiaokun.trainingpractice.util;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/22
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class DimenUtils
{
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth()
    {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeigth()
    {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Activity activity)
    {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;

    }
}
