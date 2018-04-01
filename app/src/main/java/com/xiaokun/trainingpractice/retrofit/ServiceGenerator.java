package com.xiaokun.trainingpractice.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/11
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class ServiceGenerator
{
    //当使用final修饰时,代表本app只是用这个baseUrl
    //那么一旦baseUrl改变,那么就坑爹了
    //引出一个问题,动态修改baseUrl?
    //how to change baseUrl at Runtime
    public static final String BASE_URL = "http://gank.io/api/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <S> S createService(Class<S> serviceClass)
    {
        if (!httpClient.interceptors().contains(loggingInterceptor))
        {
            httpClient.addInterceptor(loggingInterceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

}
