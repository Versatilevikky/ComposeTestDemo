package com.gv.composetestdemo.data

import android.util.Log
import com.google.gson.Gson
import com.gv.composetestdemo.model.UserResponse
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

/**
 * Created by VigneshG on 18/10/21.
 */
class UserFetcher @Inject constructor( ) {

    @Inject
     lateinit var  client: OkHttpClient
     @Inject
     lateinit var gson: Gson

    companion object {
        const val QUERY_PAGE_SIZE=25
        private const val API_URL = "https://randomuser.me/api/?inc=gender,name,phone,email,nat&results=20"
    }


    fun getUsers():UserResponse
    {
        var responseResult: UserResponse?=null
        val request = Request.Builder().url(API_URL).build()

        var countDownLatch=CountDownLatch(1)

        client.newCall(request = request).enqueue(object:Callback {
            override fun onFailure(call: Call, e: IOException) {

                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {


                    responseResult = gson.fromJson(response.body?.string(), UserResponse::class.java)
                    Log.d("UserFetcher",responseResult.toString())
                countDownLatch.countDown()

            }
        })

        countDownLatch.await()
        return responseResult!!

//        val adapter = json.adapter(UserResponse::class.java)
//        val puzzle:UserResponse  =
//            adapter.fromJson(payload) ?: return Result.Error(
//                IllegalArgumentException("Could not deserialize JSON blob! $payload")
//            )




    }
}