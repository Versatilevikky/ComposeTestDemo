package com.gv.composetestdemo

//import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import com.gv.composetestdemo.ViewModel.UserListViewModel
import com.gv.composetestdemo.ui.UserList
import com.gv.composetestdemo.ui.theme.ComposetestdemoTheme

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposetestdemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    UserList{
                        startActivity(InfoActivity.intent(this,it))
                    }
            }


        }
    }
}


}