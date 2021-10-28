package com.gv.composetestdemo.ViewModel

import android.util.Log
import com.gv.composetestdemo.data.UserFetcher
import com.gv.composetestdemo.model.Picture
import com.gv.composetestdemo.model.User
import com.gv.composetestdemo.model.UserImage
import com.gv.composetestdemo.model.UserResponse
import kotlinx.coroutines.Delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by VigneshG on 25/10/21.
 */
class UserListProvider @Inject constructor(){

    @Inject
    lateinit var  userFetcher: UserFetcher


    fun getUsersList():List<User>{
        var users: UserResponse?=null

        users=userFetcher.getUsers()
        return users!!.results
    }

    fun getUserImage(): Picture {
        return userFetcher.getUserImage().results[0].picture
    }
}