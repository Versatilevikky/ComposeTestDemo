package com.gv.composetestdemo.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gv.composetestdemo.di.*
import java.lang.Boolean.FALSE


/**
 * Created by VigneshG on 09/11/21.
 */
class InfoViewModel: ViewModel() {
    val infoComponent: InfoComponent by lazy {
        DaggerInfoComponent.create()
    }


    private val _buttonClicked: MutableLiveData<Int> = MutableLiveData(1)
    val buttonClicked :MutableLiveData<Int> = _buttonClicked
    val showSnackBar:MutableLiveData<Boolean> = MutableLiveData(FALSE)
}