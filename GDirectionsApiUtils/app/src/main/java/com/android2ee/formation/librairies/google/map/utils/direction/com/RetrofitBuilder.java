package com.android2ee.formation.librairies.google.map.utils.direction.com;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android2ee.formation.librairies.google.map.utils.direction.parser.DirectionsJSONParser;
import com.android2ee.formations.librairies.google.map.utils.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import static com.android2ee.formation.librairies.google.map.utils.direction.com.IGDirectionServer.BASE_URL;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

@SuppressWarnings("DanglingJavadoc")
public class RetrofitBuilder {

    /***********************************************************
     * Constants / Keys.
     **********************************************************/
    private static final String TAG = "GD_RetrofitBuilder";

    private static Retrofit baseRetrofit = null;
    /**
     * Default Cache size when not provided
     */
    private static final int defaultCacheSize = 1024 * 1024;
    /**
     * Default Cache's name when not provided
     */
    private static final String defaultCacheName = "OkHttpCache";

    /***********************************************************
     * Creation Methods.
     **********************************************************/
    /**
     * This method will be remove in a close future. Please use the new method.
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
        return buildRetrofit(baseUrl, null, defaultCacheName, defaultCacheSize);
    }

    /**
     * Create new Retrofit instance with a custom client.
     *
     * @param baseUrl API base URL
     * @param builder The specific builder for specific client
     * @return Created Retrofit instance
     */
    protected static Retrofit buildRetrofit(String baseUrl, OkHttpClient.Builder builder) {
        return buildRetrofit(baseUrl, builder, defaultCacheName, defaultCacheSize);
    }

    /**
     * @param baseUrl   API base URL
     * @param builder   The specific builder for specific client
     * @param cacheName Cache directory name (if you don't know, pass null, we handle it)
     * @param cacheSize Maximum cache size (if cachName is null, this parameter can ba anything)
     * @return Created Retrofit instance
     */
    private static Retrofit buildRetrofit(String baseUrl, @Nullable OkHttpClient.Builder builder, @Nullable String cacheName, int cacheSize) {
        if (builder == null) {
            builder = new OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor());
        }
//        if(cacheName==null){
//            addCache(builder, defaultCacheName, defaultCacheSize);
//        }else{
//            addCache(builder, cacheName, cacheSize);
//        }

        return new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(new DirectionsJSONParser())
                .build();
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                });
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);
        return httpLoggingInterceptor;
    }
}
