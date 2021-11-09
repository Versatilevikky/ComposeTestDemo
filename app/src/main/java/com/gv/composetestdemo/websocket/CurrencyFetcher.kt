package com.gv.composetestdemo.websocket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gv.composetestdemo.model.BitcoinTicker
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory

/**
 * Created by VigneshG on 08/11/21.
 */
@Singleton
class CurrencyFetcher @Inject constructor() {
    companion object {
        const val WEB_SOCKET_URL = "wss://ws-feed.pro.coinbase.com"
        const val TAG = "Coinbase"
    }

    private val _bitcoinPrice: MutableLiveData<BitcoinTicker> = MutableLiveData()
    val bitcoinPrice: LiveData<BitcoinTicker> = _bitcoinPrice

     lateinit var webSocketClient: WebSocketClient
     fun initWebSocket() {
        val coinbaseUri: URI? = URI(WEB_SOCKET_URL)

        createWebSocketClient(coinbaseUri)

        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        webSocketClient.setSocketFactory(socketFactory)
        webSocketClient.connect()
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        webSocketClient = object : WebSocketClient(coinbaseUri) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen")
                subscribe()
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                setUpBtcPriceText(message)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
                unsubscribe()
            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
            }

        }
    }
    private fun subscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-EUR\"] }]\n" +
                    "}"
        )
    }

    private fun setUpBtcPriceText(message: String?) {
        message?.let {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<BitcoinTicker> = moshi.adapter(BitcoinTicker::class.java)
            var bitcoin = adapter.fromJson(message)
            _bitcoinPrice.postValue(bitcoin)
            Log.d("Coin BitCoin","bitcoin "+bitcoinPrice.value?.price)

        }
    }

    private fun unsubscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"unsubscribe\",\n" +
                    "    \"channels\": [\"ticker\"]\n" +
                    "}"
        )
    }
}