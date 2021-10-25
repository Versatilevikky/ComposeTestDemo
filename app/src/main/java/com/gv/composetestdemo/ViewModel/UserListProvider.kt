package com.gv.composetestdemo.ViewModel

import android.util.Log
import com.gv.composetestdemo.data.UserFetcher
import com.gv.composetestdemo.model.User
import com.gv.composetestdemo.model.UserResponse
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
        for (i in users!!.results){
            Log.d("USERS",i.name.first.toString())
        }

        return users!!.results
    }
}