package com.xiaokun.trainingpractice.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/11
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class RetrofitActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<GankCategoryEntity> call = apiService.getCategoryData("Android", 20, 1);
        call.enqueue(new Callback<GankCategoryEntity>()
        {
            @Override
            public void onResponse(Call<GankCategoryEntity> call, Response<GankCategoryEntity> response)
            {

            }

            @Override
            public void onFailure(Call<GankCategoryEntity> call, Throwable t)
            {

            }
        });
    }
}
