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
import com.gv.myapplication.moduleA.SimpleIdlingResource
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by VigneshG on 18/10/21.
 */
@Singleton
class UserFetcher @Inject constructor() {

    @Inject
    lateinit var client: OkHttpClient

    @Inject
    lateinit var gson: Gson

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _userResponse: MutableLiveData<UserResponse> = MutableLiveData()
    private val _userImage: MutableLiveData<UserImage> = MutableLiveData()

    val loading: LiveData<Boolean> = _loading
    val userList: LiveData<UserResponse> = _userResponse
    val userImage: LiveData<UserImage> = _userImage

    companion object {
        const val QUERY_PAGE_SIZE = 25
        private const val API_URL = "https://randomuser.me/api/?inc=gender,name,phone,email,nat&results=20"
        private const val IMAGE_URL = "https://randomuser.me/api/?gender=female&inc=picture&results=1&noinfo"
    }


    fun getUsers() {
        val request = Request.Builder().url(API_URL).build()

//        val countDownLatch = CountDownLatch(1)
        Log.d("TestStatus","GetUsser Idl set false:  before :" +SimpleIdlingResource.countingIdlingResource.isIdleNow)
//        SimpleIdlingResource().getIdlingResource().setIdleState(false)
        SimpleIdlingResource().increment()
        Log.d("TestStatus","GetUsser Idla fterset false : after -" +SimpleIdlingResource.countingIdlingResource.isIdleNow)
        _loading.value = (true)

        client.newCall(request = request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                SimpleIdlingResource().decrement()
//                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {

                val responseResult = gson.fromJson(response.body?.string(), UserResponse::class.java)
                Log.d("UserFetcher", responseResult.toString())
//                countDownLatch.countDown()
                _userResponse.postValue(responseResult)
                _loading.postValue(false)
                Log.d("TestStatus","GetUsser Idl set true : before -" +SimpleIdlingResource.countingIdlingResource.isIdleNow)
//                SimpleIdlingResource().getIdlingResource().setIdleState(true)
                SimpleIdlingResource().decrement()
                Log.d("TestStatus","GetUsser Idl afterset TRUE :aft - " +SimpleIdlingResource.countingIdlingResource.isIdleNow)
            }
        })

//        val adapter = json.adapter(UserResponse::class.java)
//        val puzzle:UserResponse  =
//            adapter.fromJson(payload) ?: return Result.Error(
//                IllegalArgumentException("Could not deserialize JSON blob! $payload")
//            )

    }

    fun getUserImage() {
        val request = Request.Builder().url(IMAGE_URL).build()
        Log.d("TestStatus","GetUsserImage Idl set false : befor - " +SimpleIdlingResource.countingIdlingResource.isIdleNow)
//        SimpleIdlingResource().getIdlingResource().setIdleState(false)
        SimpleIdlingResource().increment()
        Log.d("TestStatus","GetUsserImage Idl afterset false :aft - " +SimpleIdlingResource.countingIdlingResource.isIdleNow)
//        val countDownLatch = CountDownLatch(1)
        _loading.value = (true)


        client.newCall(request = request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
//                countDownLatch.countDown()
//                SimpleIdlingResource().getIdlingResource().setIdleState(true)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseResult = gson.fromJson(response.body?.string(), UserImage::class.java)
                Log.d("UserFetcher", responseResult.toString())
                Log.d("TestStatus","GetUsserImage Idl set TRUE : before" +SimpleIdlingResource.countingIdlingResource.isIdleNow)
//                SimpleIdlingResource().getIdlingResource().setIdleState(true)
                SimpleIdlingResource().decrement()
                Log.d("TestStatus","GetUsserImage Idl after set TRUE : after - " +SimpleIdlingResource.countingIdlingResource.isIdleNow)
//                countDownLatch.countDown()
                _userImage.postValue(responseResult)
                _loading.postValue(false)
            }
        })
    }
}