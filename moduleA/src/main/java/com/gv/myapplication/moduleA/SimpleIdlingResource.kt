package com.gv.myapplication.moduleA

import androidx.annotation.VisibleForTesting
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by VigneshG on 08/11/21.
 */
class SimpleIdlingResource : IdlingResource {

    companion object{
     var  mIdlingResource: SimpleIdlingResource=SimpleIdlingResource()
    }


    @Volatile
    private var mCallback: IdlingResource.ResourceCallback? = null

    // Idleness is controlled with this boolean.
    private val mIsIdleNow = AtomicBoolean(true)
    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        mCallback = callback
    }


    fun setIdleState(isIdleNow: Boolean) {
        mIsIdleNow.set(isIdleNow)
        if (isIdleNow && mCallback != null) {
            mCallback!!.onTransitionToIdle()
        }
    }

    fun getIdlingResource(): SimpleIdlingResource {
//        if (mIdlingResource == null) {
//            mIdlingResource = SimpleIdlingResource()
//        }
        return mIdlingResource as SimpleIdlingResource
    }
}