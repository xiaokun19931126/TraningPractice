package com.xiaokun.trainingpractice.recyclerview_snap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.xiaokun.trainingpractice.R;
import com.xiaokun.trainingpractice.util.DimenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/03/22
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class RecyclerViewSnapActivity extends AppCompatActivity
{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private Items items;
    private MultiTypeAdapter mAdapter;
    private PagerSnapHelper snapHelper;


    DataViewBinder.PageSelectListener listener = new DataViewBinder.PageSelectListener()
    {
        @Override
        public void onSelected(int position)
        {
            Toast.makeText(RecyclerViewSnapActivity.this, "选择了" + position + "页面", Toast.LENGTH_SHORT).show();
        }
    };
    private DataAdapter dataAdapter;
    private int itemCount;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_snap);
        ButterKnife.bind(this);

//        intRecyclerViewWithMultiTypeAdapter();
        initRecyclerView();

//        initDataWithItems();
        initData();
    }

    private void intRecyclerViewWithMultiTypeAdapter()
    {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GalleryItemDecoration());

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        items = new Items();

        mAdapter = new MultiTypeAdapter(items);
        mAdapter.register(String.class, new DataViewBinder(manager, listener));

        recyclerView.setAdapter(mAdapter);
    }

    private void initRecyclerView()
    {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GalleryItemDecoration());

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        dataAdapter = new DataAdapter(this);
        recyclerView.setAdapter(dataAdapter);
        itemCount = dataAdapter.getItemCount();
    }

    private void initDataWithItems()
    {
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add("https://ws1.sinaimg.cn/large/610dc034ly1fp9qm6nv50j20u00miacg.jpg");
        imgUrls.add("https://ws1.sinaimg.cn/large/610dc034ly1foowtrkpvkj20sg0izdkx.jpg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180208080314_FhzuAJ_Screenshot.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180122090204_A4hNiG_Screenshot.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180115085556_8AeReR_taeyeon_ss_15_1_2018_7_58_51_833.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180109085038_4A7atU_rakukoo_9_1_2018_8_50_25_276.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180102083655_3t4ytm_Screenshot.jpeg");

        items.addAll(imgUrls);
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }

    private void initData()
    {
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add("https://ws1.sinaimg.cn/large/610dc034ly1fp9qm6nv50j20u00miacg.jpg");
        imgUrls.add("https://ws1.sinaimg.cn/large/610dc034ly1foowtrkpvkj20sg0izdkx.jpg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180208080314_FhzuAJ_Screenshot.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180129074038_O3ydq4_Screenshot.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180122090204_A4hNiG_Screenshot.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180115085556_8AeReR_taeyeon_ss_15_1_2018_7_58_51_833.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180109085038_4A7atU_rakukoo_9_1_2018_8_50_25_276.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg");
        imgUrls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180102083655_3t4ytm_Screenshot.jpeg");

        dataAdapter.setData(imgUrls);
        dataAdapter.notifyDataSetChanged();
//        recyclerView.scrollToPosition(itemCount / 2);

//        manager.scrollToPositionWithOffset(0, DimenUtils.dpToPx(20));
        manager.scrollToPositionWithOffset(100* imgUrls.size(), DimenUtils.dpToPx(20));

        //获取position和data中的size的对应位置   position%size 求余即可
//        recyclerView.scrollTo();
    }
}
