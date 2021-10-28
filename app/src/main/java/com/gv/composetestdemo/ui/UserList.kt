package com.gv.composetestdemo.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gv.composetestdemo.ViewModel.UserListViewModel
import com.gv.composetestdemo.model.User

/**
 * Created by VigneshG on 19/10/21.
 */

@Composable
fun  UserList(selectedItem: (User) -> Unit) {
        var userViewModel:UserListViewModel= viewModel()
        var isloading =userViewModel.userComponent.userListProvider().userFetcher.loading.observeAsState()
        var userList =  userViewModel.userComponent.userListProvider().getUsersList()
         Log.d("UserFetcher"," "+isloading)
        isloading.value?.let { CircularIndeterminateProgressBar(isDisplayed = it) }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(
                    items = userList,
                    itemContent = {
                        UserCard(user = it, selectedItem)
                    }
                )
            }




}

