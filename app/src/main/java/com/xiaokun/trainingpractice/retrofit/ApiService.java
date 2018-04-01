package com.xiaokun.trainingpractice.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/11
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public interface ApiService
{
    String baseUrl = "http://gank.io/api/";

    @GET("data/{category}/{count}/{page}")
    Call<GankCategoryEntity> getCategoryData(@Path("category") String category,
                                             @Path("count") int count, @Path("page") int page);

}
