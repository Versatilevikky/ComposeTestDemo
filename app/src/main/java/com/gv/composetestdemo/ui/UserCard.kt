package com.gv.composetestdemo.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gv.composetestdemo.model.User

/**
 * Created by VigneshG on 19/10/21.
 */

@Composable
fun UserCard(user: User,selectedItem: (User) -> Unit){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
//            .clickable {
//////                    selectedItem(user)
//                           }
        ,
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
//                .clickable(onClick = {selectedItem(user)})

            ,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(text = user.name.first, style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = user.gender,
                    style = MaterialTheme.typography.body1,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

            }

        }

    }
}