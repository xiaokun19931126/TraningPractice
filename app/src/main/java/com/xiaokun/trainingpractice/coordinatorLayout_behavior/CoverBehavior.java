package com.xiaokun.trainingpractice.coordinatorLayout_behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/28
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class CoverBehavior extends CoordinatorLayout.Behavior<View>
{
    private static final String TAG = "CoverBehavior";
    private int mHeaderInitOffset;
    private int mHeaderCurrentOffset;
    private int mHeaderEndOffset;

    public CoverBehavior()
    {
    }

    public CoverBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CoverBehavior(int mHeaderInitOffset, int mHeaderCurrentOffset, int mHeaderEndOffset)
    {
        this.mHeaderInitOffset = mHeaderInitOffset;
        this.mHeaderCurrentOffset = mHeaderCurrentOffset;
        this.mHeaderEndOffset = mHeaderEndOffset;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency)
    {
        return child instanceof LinearLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, final View child, View dependency)
    {
        RecyclerView recyclerView = (RecyclerView) ((ViewGroup) dependency).getChildAt(1);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("CoverBehavior", "onScrolled(CoverBehavior.java:57)" + dy);
                ViewCompat.offsetTopAndBottom(child, -dy);
            }
        });
        return true;
    }
}
