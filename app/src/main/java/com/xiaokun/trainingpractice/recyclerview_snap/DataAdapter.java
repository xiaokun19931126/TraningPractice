package com.xiaokun.trainingpractice.recyclerview_snap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaokun.trainingpractice.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/22
 *     描述   : 简单实现无限循环。
 *              1.getItemCount返回Integer.MAX_VALUE；
 *              2.setItem(imgUrls.get(position % imgUrls.size()));
 *     版本   : 1.0
 * </pre>
 */

public class DataAdapter extends Adapter<RecyclerView.ViewHolder>
{
    private static final String TAG = "DataAdapter";
    private Context mContext;
    private List<String> imgUrls;

    public DataAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    public DataAdapter(Context mContext, List<String> urls)
    {
        this.mContext = mContext;
        this.imgUrls = urls;
    }

    public void setData(List<String> urls)
    {
        this.imgUrls = urls;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_data, parent, false);
        return new MyHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int size = imgUrls.size();
        Log.e(TAG, "position:" + position);

        if (holder instanceof MyHolder)
        {
            ((MyHolder) holder).setItem(imgUrls.get(position % size));
        }
    }

    @Override
    public int getItemCount()
    {
        return Integer.MAX_VALUE;
    }

    class MyHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.imag_view)
        ImageView imagView;

        public MyHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewGroup.LayoutParams layoutParams = imagView.getLayoutParams();
            layoutParams.width = 960;
            imagView.setLayoutParams(layoutParams);
        }

        void setItem(String url)
        {
            Glide.with(imagView.getContext()).load(url).into(imagView);
        }
    }


}
