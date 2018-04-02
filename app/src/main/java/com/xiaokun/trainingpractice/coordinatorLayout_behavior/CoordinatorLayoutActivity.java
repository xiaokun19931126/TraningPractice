package com.xiaokun.trainingpractice.coordinatorLayout_behavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaokun.trainingpractice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/28
 *     描述   : 嵌套滚动
 *     版本   : 1.0
 * </pre>
 */

public class CoordinatorLayoutActivity extends AppCompatActivity
{

    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.list_header)
    TextView listHeader;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.scroll_layout)
    CoordinatorLayout scrollLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecyclerAdapter());
    }


}
