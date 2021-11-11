package com.gv.composetestdemo

import android.util.Log
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.gv.myapplication.moduleA.SimpleIdlingResource
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ExampleInstrumentedTest {
    var mIdlingResource: IdlingResource?=null

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
//        mIdlingResource = composeTestRule.activity.getIdlingResource()
        mIdlingResource = SimpleIdlingResource().getIdlingResource()
        IdlingRegistry.getInstance().register(mIdlingResource)

    }
    @Test
    fun test() {
        // Context of the app under test.
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        composeTestRule.onNodeWithText("Test").performClick()
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        Log.d("TestStatus","51" +SimpleIdlingResource().isIdlenow())
//        composeTestRule.onNodeWithContentDescription("ProgressBar").assertExists()
        composeTestRule.onNodeWithText("Phone : 9999").assertExists()
        Log.d("TestStatus","53" +SimpleIdlingResource().isIdlenow())
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        Log.d("TestStatus","55" +SimpleIdlingResource().isIdlenow())
        composeTestRule.onNodeWithText("1 BTC :").assertExists()
        composeTestRule.onNodeWithContentDescription("button").performClick()
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        composeTestRule.onNodeWithText("2 Time Clicked").assertExists()
        composeTestRule.onNodeWithText("DisMiss").performClick()
        composeTestRule.onNodeWithContentDescription("button").performClick()
        composeTestRule.onNodeWithText("3 Time Clicked").assertExists()
        composeTestRule.onNodeWithContentDescription("ProgressBar").assertDoesNotExist()
        pressBack()
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        composeTestRule.onNodeWithText("Test").performClick()
        composeTestRule.onNodeWithText("1 BTC :").assertExists()
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(mIdlingResource)
    }
}