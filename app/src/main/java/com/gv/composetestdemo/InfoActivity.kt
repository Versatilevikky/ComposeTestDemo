package com.gv.composetestdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import com.gv.composetestdemo.model.User
import com.gv.composetestdemo.ui.theme.ComposetestdemoTheme

class InfoActivity : ComponentActivity() {

    companion object{
        private const val UserInfo = "userInfo"
        fun intent(context: Context, user: User)=
            Intent(context,InfoActivity::class.java).apply {
                putExtra(UserInfo,user)
            }
    }
    private val user : User by lazy {
        intent?.getSerializableExtra(UserInfo) as User
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposetestdemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                   ViewMoreInfo(userInfo = user)
                }
            }
        }
    }
}

@Composable
fun ViewMoreInfo(userInfo:User){
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(10.dp)
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_background),
////                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(shape = RoundedCornerShape(4.dp)),
//                contentScale = ContentScale.Fit
//            )
//            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "test",
//                style = MaterialTheme.typography.h3
            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = tvShow.overview,
//                style = MaterialTheme.typography.h5
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Original release : ${tvShow.year}",
//                style = MaterialTheme.typography.h5
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "IMDB : ${tvShow.rating}",
//                style = MaterialTheme.typography.h5
//            )

        }
    }
}
