package com.gv.composetestdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.IdlingResource
import com.gv.composetestdemo.ViewModel.UserListViewModel
import com.gv.composetestdemo.ui.UserList
import com.gv.composetestdemo.ui.theme.ComposetestdemoTheme
import com.gv.myapplication.moduleA.SimpleIdlingResource

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposetestdemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    UserList {
                        startActivity(InfoActivity.intent(this, it))
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun getIdlingResource(): SimpleIdlingResource {

        return SimpleIdlingResource().getIdlingResource()
    }




}