package com.gv.composetestdemo.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.gv.composetestdemo.ViewModel.UserListViewModel
import com.gv.composetestdemo.model.UserImage
import com.gv.composetestdemo.model.UserResponse
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by VigneshG on 18/10/21.
 */
@Singleton
class UserFetcher @Inject constructor( ) {

    @Inject
     lateinit var  client: OkHttpClient
     @Inject
     lateinit var gson: Gson

     var  loading :MutableLiveData<Boolean> = MutableLiveData()

    companion object {
        const val QUERY_PAGE_SIZE=25
        private const val API_URL = "https://randomuser.me/api/?inc=gender,name,phone,email,nat&results=20"
        private const val IMAGE_URL = "https://randomuser.me/api/?gender=female&inc=picture&results=1&noinfo"
    }


    fun getUsers():UserResponse
    {
        var responseResult: UserResponse?=null
        val request = Request.Builder().url(API_URL).build()

        var countDownLatch=CountDownLatch(1)
        loading.postValue(true)
        client.newCall(request = request).enqueue(object:Callback {
            override fun onFailure(call: Call, e: IOException) {

                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {

                    responseResult = gson.fromJson(response.body?.string(), UserResponse::class.java)
                    Log.d("UserFetcher",responseResult.toString())
                countDownLatch.countDown()
                loading.postValue(false)

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

    fun getUserImage(): UserImage {
        var responseResult: UserImage?=null
        val request = Request.Builder().url(IMAGE_URL).build()
        loading.postValue(true)
        var countDownLatch=CountDownLatch(1)

        client.newCall(request = request).enqueue(object:Callback {
            override fun onFailure(call: Call, e: IOException) {

                countDownLatch.countDown()
            }


            override fun onResponse(call: Call, response: Response) {
                responseResult = gson.fromJson(response.body?.string(), UserImage::class.java)
                Log.d("UserFetcher",responseResult.toString())
                countDownLatch.countDown()
                loading.postValue(false)

            }
        })

        countDownLatch.await()
//        loading.value=false
        return responseResult!!
    }
}