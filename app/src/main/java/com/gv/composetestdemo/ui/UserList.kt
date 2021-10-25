package com.gv.composetestdemo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.gv.composetestdemo.ViewModel.UserListViewModel

/**
 * Created by VigneshG on 19/10/21.
 */

@Composable
fun UserList() {
        var user:UserListViewModel= viewModel()
        var userList = user.userComponent.userListProvider().getUsersList()
         LazyColumnFor(items = userList) { item ->
                UserCard(item, Modifier.padding(16.dp))
            }
}