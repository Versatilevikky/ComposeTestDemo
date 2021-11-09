package com.gv.composetestdemo.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gv.composetestdemo.ViewModel.UserListViewModel
import com.gv.composetestdemo.model.User

/**
 * Created by VigneshG on 19/10/21.
 */

@Composable
fun UserList(selectedItem: (User) -> Unit) {
    var userViewModel: UserListViewModel = viewModel()

    val isLoading = userViewModel.userComponent.userListProvider().userFetcher.loading.observeAsState()
    val userList = userViewModel.userComponent.userListProvider().userFetcher.userList.observeAsState()

    LaunchedEffect(key1 = Unit) {
        userViewModel.userComponent.userListProvider().getUsersList()
    }

    SideEffect {
        Log.d("UserFetcher", " ${isLoading.value}")
    }

    isLoading.value?.let {

        CircularIndeterminateProgressBar(isDisplayed = it)
    }

    userList.value?.let {
        it.results.add(0,userViewModel.tempUser)
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = it.results,
                itemContent = {
                    UserCard(user = it, selectedItem)
                }
            )
        }
    }
}

