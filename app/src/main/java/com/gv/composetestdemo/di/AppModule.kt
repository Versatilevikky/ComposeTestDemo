package com.gv.composetestdemo.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


/**
 * Created by VigneshG on 18/10/21.
 */

@Module
 class  AppModule {

    @Singleton
    @Provides
     fun provideOkHttpClient() :OkHttpClient{
        var client = OkHttpClient().newBuilder().build()
        return  client
     }


    @Singleton
    @Provides
     fun provideGson() :Gson{
        val gson = Gson()
        return  gson
     }

//    @Provides
//    @Singleton
//    fun provideHttpCache(application: Application): Cache? {
//        val cacheSize = 10 * 1024 * 1024
//        return Cache(application.getCacheDir(), cacheSize.toLong())
//    }

//    @Provides
//    @Singleton
//    fun provideOkhttpClient(cache: Cache?): OkHttpClient? {
//        val client = OkHttpClient.Builder()
//        client.cache(cache)
//        return client.build()
//    }

//    @Singleton
//    @Provides
//    fun provideUserFetcher(client:OkHttpClient):UserFetcher= UserFetcher(client)

}