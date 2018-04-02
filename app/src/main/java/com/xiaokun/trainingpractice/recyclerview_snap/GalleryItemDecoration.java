package com.xiaokun.trainingpractice.recyclerview_snap;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaokun.trainingpractice.util.DimenUtils;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/22
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class GalleryItemDecoration extends RecyclerView.ItemDecoration
{
    private static final String TAG = "GalleryItemDecoration";

    //item左右的margin默认为0dp
    private int mPageMargin = 0;
    //左右边页面能看到的宽度默认为20dp
    private int sidePageVisionWidth = 20;
    private RecyclerView recyclerView;
    private boolean isFirstOne = false;
    private int itemCount;
    private int otherWidth;

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        //获取当前item的position
        final int position = parent.getChildAdapterPosition(view);
        //获取item的数量
        itemCount = parent.getAdapter().getItemCount();

        recyclerView = parent;

        parent.post(new Runnable()
        {
            @Override
            public void run()
            {
                //防止getWidth=0的情况出现
                setHorizontalParams(parent, view, position, itemCount);
            }
        });

        Log.e("GalleryItemDecoration", "getItemOffsets(GalleryItemDecoration.java:52)" + position);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(recyclerView.getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 设置水平方向的item的宽高属性
     *
     * @param parent
     * @param item
     * @param position
     * @param itemCount
     */
    private void setHorizontalParams(ViewGroup parent, View item, int position, int itemCount)
    {
        otherWidth = DimenUtils.dpToPx(4 * mPageMargin + 2 * sidePageVisionWidth);
        int itemWidth = parent.getWidth() - otherWidth;
        int itemHeight = parent.getHeight();

        //当第一页和最后一页时
        int leftMargin = position == 0 ? otherWidth / 2 : DimenUtils.dpToPx(mPageMargin);
        int rightMargin = position == itemCount - 1 ? otherWidth / 2 : DimenUtils.dpToPx(mPageMargin);

        setLayoutParams(item, leftMargin, 0, rightMargin, 0, itemWidth, itemHeight, position);
    }

    private void setLayoutParams(View itemView, int left, int top,
                                 int right, int bottom, int itemWidth, int itemHeight, int position)
    {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        boolean mMarginChange = false;
        boolean mWidthChange = false;
        boolean mHeightChange = false;

        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom)
        {
            lp.setMargins(left, top, right, bottom);
            mMarginChange = true;
        }
        if (lp.width != itemWidth)
        {
            lp.width = itemWidth;
            mWidthChange = true;
        }
        if (lp.height != itemHeight)
        {
            lp.height = itemHeight;
            mHeightChange = true;

        }

        // 因为方法会不断调用，只有在真正变化了之后才调用
        if (mWidthChange || mMarginChange || mHeightChange)
        {
            itemView.setLayoutParams(lp);
            if (position == 0 && !isFirstOne)
            {
                //防止首次进入时不显示左边距
                recyclerView.scrollToPosition(0);
                isFirstOne = true;
            }
//            int i = itemCount / 2;
//            if (position == i && !isFirstOne)
//            {
//                isFirstOne = true;
//            }
        }
    }

}
