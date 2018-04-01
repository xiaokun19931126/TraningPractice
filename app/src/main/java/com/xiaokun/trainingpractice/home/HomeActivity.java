package com.xiaokun.trainingpractice.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.xiaokun.trainingpractice.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/07
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class HomeActivity extends AppCompatActivity
{
    @BindView(android.R.id.list)
    RecyclerView list;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MultiTypeAdapter mAdapter;

    private Items items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initSwipeRefreshLayout();
        initRecyclerView();
        initData();
    }

    private void initSwipeRefreshLayout()
    {
//        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void initRecyclerView()
    {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        list.setLayoutManager(manager);
        list.addItemDecoration(decoration);

        items = new Items();
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(HomeEntity.HomeListBean.class, new HomeItemsViewBinder());
        list.setAdapter(mAdapter);
    }

    private void initData()
    {
        String json = readJsonFromAssets();
        HomeEntity homeEntity = new Gson().fromJson(json, HomeEntity.class);
        List<HomeEntity.HomeListBean> homeList = homeEntity.getHomeList();
        items.addAll(homeList);
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }

    private String readJsonFromAssets()
    {
        try
        {
            InputStream is = getAssets().open("home.json");
            InputStreamReader in = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(in);
            StringBuffer sb = new StringBuffer();
            String mLine;
            while ((mLine = br.readLine()) != null)
            {
                sb.append(mLine);
            }
            return sb.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    //String json = "{\"HomeList\":[{\"title\":\"OkHttp监听加载进度\"},{\"title\":\"下载监听，且通知栏更新进度\"},{\"title\":\"属性动画\"},{\"title\":\"空布局\"}],\"Code\":0,\"Message\":\"ok\"}";

}
