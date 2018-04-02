package com.xiaokun.trainingpractice.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaokun.trainingpractice.R;
import com.xiaokun.trainingpractice.coordinatorLayout_behavior.CoordinatorLayoutActivity;
import com.xiaokun.trainingpractice.okhttp_download_file.DownLoadProgressaActivity;
import com.xiaokun.trainingpractice.okhttp_load_progress.ProgressActivity;
import com.xiaokun.trainingpractice.property_animation.AnimationActivity;
import com.xiaokun.trainingpractice.recyclerview_snap.RecyclerViewSnapActivity;
import com.xiaokun.trainingpractice.retrofit.RetrofitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.ItemViewBinder;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/07
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */
public class HomeItemsViewBinder extends ItemViewBinder<HomeEntity.HomeListBean, HomeItemsViewBinder.ViewHolder>
{

    private int position;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent)
    {
        View root = inflater.inflate(R.layout.item_home_items, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HomeEntity.HomeListBean homeEntity)
    {
        position = getPosition(holder);
        holder.setItems(homeEntity, position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.textview)
        TextView textview;
        int mPosition;

        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setItems(HomeEntity.HomeListBean homeEntity, int position)
        {
            mPosition = position;
            textview.setText(homeEntity.getTitle());
        }

        @OnClick(R.id.textview)
        public void onViewClicked()
        {
            Intent intent = new Intent();
            Context context = textview.getContext();
            switch (mPosition)
            {
                case 0:
                    intent.setClass(context, ProgressActivity.class);
                    break;
                case 1:
                    intent.setClass(context, DownLoadProgressaActivity.class);
                    break;
                case 2:
                    intent.setClass(context, AnimationActivity.class);
                    break;
                case 4:
                    intent.setClass(context, RetrofitActivity.class);
                    break;
                case 5:
                    intent.setClass(context, RecyclerViewSnapActivity.class);
                    break;
                case 6:
                    intent.setClass(context, CoordinatorLayoutActivity.class);
                    break;
                default:
                    intent.setClass(context, ProgressActivity.class);
                    break;
            }
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, 0);
        }

    }
}
