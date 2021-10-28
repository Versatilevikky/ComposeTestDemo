package com.gv.composetestdemo.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gv.composetestdemo.data.UserFetcher
import com.gv.composetestdemo.di.DaggerUserComponent
import com.gv.composetestdemo.di.UserComponent
import com.gv.composetestdemo.model.User
import com.gv.composetestdemo.model.UserResponse
import javax.inject.Inject

/**
 * Created by VigneshG on 18/10/21.
 */
class UserListViewModel :ViewModel(){

    val userComponent:UserComponent by lazy {
        DaggerUserComponent.create()
    }

}