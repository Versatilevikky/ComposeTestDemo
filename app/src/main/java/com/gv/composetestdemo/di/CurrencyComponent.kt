package com.gv.composetestdemo.di

import com.google.android.material.shape.CutCornerTreatment
import com.gv.composetestdemo.InfoActivity
import com.gv.composetestdemo.websocket.CurrencyFetcher
import dagger.Component
import javax.inject.Singleton

/**
 * Created by VigneshG on 09/11/21.
 */
@Singleton
@Component
interface CurrencyComponent {
    interface Factory {
        fun create(): CurrencyComponent
    }
    fun currencyFetcher(): CurrencyFetcher

    fun inject(infoActivity: InfoActivity)

}