package com.gv.composetestdemo.di

import com.gv.composetestdemo.ViewModel.UserListProvider
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