package com.gv.myapplication.moduleA

import androidx.annotation.VisibleForTesting
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by VigneshG on 08/11/21.
 */
class SimpleIdlingResource  {

    companion object{
        private const val RESOURCE = "GLOBAL"
//        var  mIdlingResource: SimpleIdlingResource=SimpleIdlingResource()
        @JvmField var countingIdlingResource = CountingIdlingResource(RESOURCE)
    }




    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
    fun isIdlenow():Boolean{
        return countingIdlingResource.isIdleNow
    }

//    @Volatile
//    private var mCallback: IdlingResource.ResourceCallback? = null
//
//    // Idleness is controlled with this boolean.
//    private val mIsIdleNow = AtomicBoolean(true)
//    override fun getName(): String {
//        return this.javaClass.name
//    }
//
//    override fun isIdleNow(): Boolean {
//        return mIsIdleNow.get()
//    }
//
//    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
//        mCallback = callback
//    }


//    fun setIdleState(isIdleNow: Boolean) {
//        mIsIdleNow.set(isIdleNow)
//        if (isIdleNow && mCallback != null) {
//            mCallback!!.onTransitionToIdle()
//        }
//    }

    fun getIdlingResource(): CountingIdlingResource {
//        if (mIdlingResource == null) {
//            mIdlingResource = SimpleIdlingResource()
//        }
        return countingIdlingResource as CountingIdlingResource
    }
}