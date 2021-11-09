package com.gv.composetestdemo.di

import com.gv.composetestdemo.InfoActivity
import com.gv.composetestdemo.ViewModel.UserListProvider
import com.gv.composetestdemo.websocket.CurrencyFetcher
import dagger.Component
import javax.inject.Singleton

/**
 * Created by VigneshG on 25/10/21.
 */
@Singleton
@Component(modules = [AppModule::class])
interface UserComponent {

    interface Factory {
        fun create(): UserComponent
    }

    fun userListProvider():UserListProvider



}