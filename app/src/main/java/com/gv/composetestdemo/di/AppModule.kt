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

}