package com.gv.composetestdemo.websocket

import com.gv.composetestdemo.data.UserFetcher
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by VigneshG on 09/11/21.
 */
@Singleton
class CurrenncyProvider @Inject constructor() {
    @Inject
    lateinit var  currencyFetcher: CurrencyFetcher

    fun getPrice(){
        currencyFetcher.bitcoinPrice
    }
}