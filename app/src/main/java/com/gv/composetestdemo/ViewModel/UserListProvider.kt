package com.gv.composetestdemo.ViewModel

import com.gv.composetestdemo.data.UserFetcher
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by VigneshG on 25/10/21.
 */

@Singleton
class UserListProvider @Inject constructor(){

    @Inject
    lateinit var  userFetcher: UserFetcher



    fun getUsersList(){
        userFetcher.getUsers()
    }

    fun getUserImage() {
        userFetcher.getUserImage()
    }
}