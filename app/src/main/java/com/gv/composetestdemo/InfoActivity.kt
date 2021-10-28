package com.gv.composetestdemo

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.gv.composetestdemo.ViewModel.UserListViewModel
import com.gv.composetestdemo.model.User
import com.gv.composetestdemo.ui.CircularIndeterminateProgressBar
import com.gv.composetestdemo.ui.theme.ComposetestdemoTheme
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.glide.GlideImage

class InfoActivity : ComponentActivity() {

    companion object {
        private const val UserInfo = "userInfo"
        fun intent(context: Context, user: User) =
            Intent(context, InfoActivity::class.java).apply {
                putExtra(UserInfo, user)
            }
    }

    private val user: User by lazy {
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
fun ViewMoreInfo(userInfo: User) {
    val userListViewModel: UserListViewModel = viewModel()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit) {
        userListViewModel.userComponent.userListProvider().getUserImage()
    }

    val imageUrl = userListViewModel.userComponent.userListProvider().userFetcher.userImage.observeAsState()
    val isLoading = userListViewModel.userComponent.userListProvider().userFetcher.loading.observeAsState()

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
            Text(
                text = "Image Below",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(16.dp))
            isLoading.value?.let { CircularIndeterminateProgressBar(it) }

            imageUrl.value?.let {
                Image(
                    painter = rememberImagePainter(
                        data = it.results[0].picture,
                        onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.user)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = userInfo.name.first,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = userInfo.gender,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "mail : ${userInfo.email}",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Phone : ${userInfo.phone}",
                style = MaterialTheme.typography.h5
            )
        }
    }
}
