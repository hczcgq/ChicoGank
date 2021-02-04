package com.chico.gank.http

import com.chico.gank.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
class HttpClient {

    private var client: HttpClient? = null
    private var okHttp: OkHttpClient? = null
    private var logInterceptor: HttpLoggingInterceptor? = null
    private var gson: Gson? = null

    fun get(): HttpClient? {
        if (client == null) {
            synchronized(HttpClient::class.java) {
                if (client == null) {
                    client = HttpClient()
                }
            }
        }
        return client
    }

    fun retrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl("https://gank.io/")
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun retrofit(url: String): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /*OkHttp实例化*/
    private fun getOkHttpClient(): OkHttpClient? {
        if (okHttp == null) {
            okHttp = OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(5, 6, TimeUnit.MINUTES))
                .addInterceptor(getLogInterceptor())
                .build()
        }
        return okHttp
    }

    private fun getLogInterceptor(): Interceptor {
        if (logInterceptor == null) {
            logInterceptor = HttpLoggingInterceptor()
            logInterceptor?.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        }
        return logInterceptor!!
    }


    fun getGson(): Gson? {
        if (gson == null) {
            gson = GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithModifiers(
                    Modifier.PUBLIC,
                    Modifier.STATIC,
                    Modifier.TRANSIENT,
                    Modifier.VOLATILE
                )
                .create()
        }
        return gson
    }
}