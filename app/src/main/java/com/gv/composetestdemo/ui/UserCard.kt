package com.gv.composetestdemo.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gv.composetestdemo.model.User

/**
 * Created by VigneshG on 19/10/21.
 */

@Composable
fun UserCard(user: User, modifier: Modifier){
    Surface(shape = RoundedCornerShape(8.dp), elevation = 8.dp, modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(user.name.first, style = MaterialTheme.typography.h4, modifier = Modifier.padding(bottom = 4.dp))
                    Text(user.gender)

            }
        }
    }
}