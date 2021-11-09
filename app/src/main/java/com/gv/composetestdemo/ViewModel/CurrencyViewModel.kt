package com.gv.composetestdemo.ViewModel

import androidx.lifecycle.ViewModel
import com.gv.composetestdemo.di.CurrencyComponent
import com.gv.composetestdemo.di.DaggerCurrencyComponent
import com.gv.composetestdemo.di.DaggerUserComponent
import com.gv.composetestdemo.di.UserComponent


/**
 * Created by VigneshG on 09/11/21.
 */
class CurrencyViewModel: ViewModel() {
    val currencyComponent: CurrencyComponent by lazy {
        DaggerCurrencyComponent.create()
    }
}