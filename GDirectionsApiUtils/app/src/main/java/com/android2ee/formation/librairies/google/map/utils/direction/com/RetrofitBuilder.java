package com.android2ee.formation.librairies.google.map.utils.direction.com;

import android.support.annotation.Nullable;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.android2ee.formation.librairies.google.map.utils.direction.com.IGDirectionServer.BASE_URL;

public class RetrofitBuilder {
    private static final String TAG = "RetrofitBuilder";
    /***********************************************************
     * Constants / Keys.
     **********************************************************/

    /**
     * The base URL for Retrofit
     */
    private static Retrofit baseRetrofit = null;
    /**
     * Default Cache size when not provided
     */
    private static final int defaultCacheSize=1024 * 1024;
    /**
     * Default Cache's name when not provided
     */
    private static final String defaultCacheName ="OkHttpCache";

    /***********************************************************
     * Creation Methods.
     **********************************************************/
    /**
     * This method will be remove in a close future. Please use the new method.
     * @return
     */
    public static Retrofit getBaseRetrofit() {
        if (baseRetrofit == null) {
            baseRetrofit = buildRetrofit(BASE_URL);
        }
        return baseRetrofit;
    }

    /**
     * Create new Retrofit instance with generic cache and logging configuration.
     *
     * @param baseUrl API base URL
     * @return Created Retrofit instance
     */
    private static Retrofit buildRetrofit(String baseUrl) {
        return buildRetrofit(baseUrl,null,defaultCacheName,defaultCacheSize);
    }

    /**
     * Create new Retrofit instance with a custom client.
     *
     * @param baseUrl API base URL
     * @param builder The specific builder for specific client
     * @return Created Retrofit instance
     */
    protected static Retrofit buildRetrofit(String baseUrl, OkHttpClient.Builder builder) {
        return buildRetrofit(baseUrl,builder, defaultCacheName,defaultCacheSize);
    }

    /**
     *
     * @param baseUrl API base URL
     * @param builder The specific builder for specific client
     * @param cacheName Cache directory name (if you don't know, pass null, we handle it)
     * @param cacheSize Maximum cache size (if cachName is null, this parameter can ba anything)
     * @return Created Retrofit instance
     */
    private static Retrofit buildRetrofit(String baseUrl, @Nullable OkHttpClient.Builder builder, @Nullable String cacheName, int cacheSize) {
        if(builder==null){
            builder = new OkHttpClient.Builder();
        }
//        if(cacheName==null){
//            addCache(builder, defaultCacheName, defaultCacheSize);
//        }else{
//            addCache(builder, cacheName, cacheSize);
//        }

        return new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }
    /***********************************************************
     *  Managing Cache Creation
     **********************************************************/


    /**
     * Create the Cache file for OkHttp
     * @param builder
     * @param cacheName
     * @param cacheSize
     * @return
     */
//    protected static OkHttpClient.Builder addCache(OkHttpClient.Builder builder,  String cacheName, int cacheSize) {
//        if (BaseApplication.getInstance() != null) {
//            //you are not in a unit test so
//            File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), cacheName);
//            Cache cacheDir = new Cache(cacheFile, cacheSize);
//            builder.cache(cacheDir);
//        }
//
//        return builder;
//    }

}
