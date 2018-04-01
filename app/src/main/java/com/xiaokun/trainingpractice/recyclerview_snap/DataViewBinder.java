package com.xiaokun.trainingpractice.recyclerview_snap;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaokun.trainingpractice.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/22
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */
public class DataViewBinder extends ItemViewBinder<String, DataViewBinder.ViewHolder>
{

    private final Provider provider;
    private LinearLayoutManager manager;

    public DataViewBinder(RecyclerView.LayoutManager manager, PageSelectListener listener)
    {
        this.manager = (LinearLayoutManager) manager;
        provider = new Provider();
        provider.manager = (LinearLayoutManager) manager;
        provider.pageSelectListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent)
    {
        View root = inflater.inflate(R.layout.item_data, parent, false);
        return new ViewHolder(root, provider);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item)
    {
        holder.setItem(getPosition(holder), getAdapter().getItemCount(), item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.imag_view)
        ImageView imagView;

        Provider provider;

        ViewHolder(View itemView, Provider provider)
        {
            super(itemView);
            this.provider = provider;
            ButterKnife.bind(this, itemView);
//            ViewGroup.LayoutParams params = imagView.getLayoutParams();
//            params.width = imagView.getContext().getResources().getDisplayMetrics().widthPixels - 120;
//            imagView.setLayoutParams(params);
        }

        void setItem(int position, int itemCount, String url)
        {
            //由于recyclerview对holder的复用机制，这里会造成一定的问题，有的不是第一个和最后一个位置的地方
            //也会有margin
//            if (position == 0)
//            {
//                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imagView.getLayoutParams();
//                marginLayoutParams.setMargins(60, 0, 0, 0);
//            }
//            if (position == itemCount - 1)
//            {
//                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imagView.getLayoutParams();
//                marginLayoutParams.setMargins(0, 0, 60, 0);
//            }

//            int currentPosition = provider.manager.findLastCompletelyVisibleItemPosition();
//            provider.pageSelectListener.onSelected(currentPosition);

            Glide.with(imagView.getContext()).load(url).into(imagView);
        }
    }

    //提供参数
    class Provider
    {
        public LinearLayoutManager manager;

        public PageSelectListener pageSelectListener;
    }

    interface PageSelectListener
    {
        /**
         * 选择当前position页面的回调
         *
         * @param position
         */
        void onSelected(int position);
    }

}
