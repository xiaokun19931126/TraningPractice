package com.xiaokun.trainingpractice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaokun.trainingpractice.okhttp_download_file.DownLoadProgressaActivity;
import com.xiaokun.trainingpractice.okhttp_load_progress.ProgressActivity;

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
            switch (mPosition)
            {
                case 0:
                    textview.getContext().startActivity(new Intent(textview.getContext(), ProgressActivity.class));
                    break;
                case 1:
                    textview.getContext().startActivity(new Intent(textview.getContext(), DownLoadProgressaActivity.class));
                    break;
                default:

                    break;
            }

        }

    }
}
