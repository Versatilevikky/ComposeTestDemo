package com.gv.composetestdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.gv.composetestdemo.ViewModel.InfoViewModel
import com.gv.composetestdemo.model.User
import com.gv.composetestdemo.ui.CircularIndeterminateProgressBar
import com.gv.composetestdemo.ui.SnackBar
import com.gv.composetestdemo.ui.SnackBarProvider
import com.gv.composetestdemo.ui.theme.ComposetestdemoTheme
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class InfoActivity : ComponentActivity() {


    private val infoViewModel: InfoViewModel by viewModels()

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

    override fun onResume() {
        super.onResume()
        infoViewModel.infoComponent.currencyProvider().currencyFetcher.initWebSocket()
//        Log.d("CurrencyComponent","what "+what)
//        Log.d("CurrencyComponent","currencyProvider "+currencyProvider)
        Log.d("CurrencyComponent","currencyFetcher "+infoViewModel.infoComponent.currencyProvider().currencyFetcher)
    }

    override fun onPause() {
        super.onPause()
        infoViewModel.infoComponent.currencyProvider().currencyFetcher.webSocketClient.close()
//        currencyComponent.currencyProvider().currencyFetcher.webSocketClient.close()
    }
}

@Composable
fun ViewMoreInfo(userInfo: User) {
    val infoViewModel:InfoViewModel= viewModel()
    val scrollState = rememberScrollState()
    var snackbarProvider = SnackBarProvider()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = Unit) {
        infoViewModel.infoComponent.userListProvider().getUserImage()


    }
    SideEffect {
        Log.d("CurrencyComponent","Compose --"+infoViewModel.infoComponent.currencyProvider().currencyFetcher)
    }


    val imageUrl = infoViewModel.infoComponent.userListProvider().userFetcher.userImage.observeAsState()
    val isLoading =  infoViewModel.infoComponent.userListProvider().userFetcher.loading.observeAsState()
     val bitcoinPrice= infoViewModel.infoComponent.currencyProvider().currencyFetcher.bitcoinPrice.observeAsState()
    val counter = infoViewModel.buttonClicked.observeAsState()
    val showSnackBar = infoViewModel.showSnackBar.observeAsState()
    val scope = rememberCoroutineScope()
    Column {


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

                Spacer(modifier = Modifier.height(16.dp))
                isLoading.value?.let { CircularIndeterminateProgressBar(it) }

                imageUrl.value?.let {
                    Image(
                        painter = rememberImagePainter(
                            data = it.results[0].picture.large,
                            onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                            builder = {
                                crossfade(true)
                                placeholder(R.drawable.user)
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                            .align(Alignment.CenterHorizontally)
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
                Spacer(modifier = Modifier.height(16.dp))


            }

        }
        Row(
            modifier = Modifier
                .padding(16.dp), horizontalArrangement = Arrangement.Start
        ) {
            getGooglePhotosIcon()
            bitcoinPrice.value?.let {
                Text(
                    text = "1 BTC: ${it.price} €",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        counter.value?.let {

            Button(modifier = Modifier
                .semantics { contentDescription = "button" }
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
                onClick = {
                    infoViewModel.buttonClicked.value=it+1
                    infoViewModel.showSnackBar.value=true

                }) {
                Text(text = it.toString())
            }


        }

        if(showSnackBar.value!!){
            showSnackBar(counter.value.toString()+" Time Clicked",snackbarHostState,scope,snackbarProvider)
            infoViewModel.showSnackBar.value=false
            }
        SnackBar(
            snackbarHostState = snackbarHostState,
            onActionClick = {
                snackbarProvider.dismissSnackBar()
            })
    }
}

fun showSnackBar(msg:String, snackbarHostState: SnackbarHostState, scope: CoroutineScope, snackbarProvider: SnackBarProvider) {


    snackbarProvider.showSnackBar(
        scope,
        snackbarHostState,
         msg,
        "DisMiss",
        SnackbarDuration.Short
    )


}


@Composable
fun instagramIcon() {
    val instaColors = listOf(Color.Yellow, Color.Red, Color.Magenta)
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
    ) {
        drawRoundRect(
            brush = Brush.linearGradient(colors = instaColors),
            cornerRadius = CornerRadius(60f, 60f),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(colors = instaColors),
            radius = 45f,
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(colors = instaColors),
            radius = 13f,
            center = Offset(this.size.width * .80f, this.size.height * 0.20f),
        )
    }
}

@Composable
fun messengerIcon() {
    val colors = listOf(Color(0xFF02b8f9), Color(0xFF0277fe))
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
    ) {

        val trianglePath = Path().let {
            it.moveTo(this.size.width * .20f, this.size.height * .77f)
            it.lineTo(this.size.width * .20f, this.size.height * 0.95f)
            it.lineTo(this.size.width * .37f, this.size.height * 0.86f)
            it.close()
            it
        }

        val electricPath = Path().let {
            it.moveTo(this.size.width * .20f, this.size.height * 0.60f)
            it.lineTo(this.size.width * .45f, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.56f, this.size.height * 0.46f)
            it.lineTo(this.size.width * 0.78f, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.54f, this.size.height * 0.60f)
            it.lineTo(this.size.width * 0.43f, this.size.height * 0.45f)
            it.close()
            it
        }

        drawOval(
            Brush.verticalGradient(colors = colors),
            size = Size(this.size.width, this.size.height * 0.95f)
        )

        drawPath(
            path = trianglePath,
            Brush.verticalGradient(colors = colors),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )

        drawPath(path = electricPath, color = Color.White)

    }
}


@Composable
private fun getGooglePhotosIcon() {
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
    ) {
        drawArc(
            color = Color(0xFFf04231),
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .25f, 0f)
        )
        drawArc(
            color = Color(0xFF4385f7),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .50f, size.height * .25f)
        )
        drawArc(
            color = Color(0xFF30a952),
            startAngle = 0f,
            sweepAngle = -180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(0f, size.height * .25f)
        )

        drawArc(
            color = Color(0xFFffbf00),
            startAngle = 270f,
            sweepAngle = -180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .25f, size.height * .50f)
        )


    }
}