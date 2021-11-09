package com.gv.composetestdemo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
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
        mIdlingResource = composeTestRule.activity.getIdlingResource()
        IdlingRegistry.getInstance().register(mIdlingResource)
//        val activityScenario: ActivityScenario<*> = ActivityScenario.launch(
//            MainActivity::class.java
//        )
//        activityScenario.onActivity(ActivityScenario.ActivityAction<MainActivity> { activity ->
//            mIdlingResource = activity.getIdlingResource()
//            // To prove that the test fails, omit this call:
//
//        })
    }
    @Test
    fun test() {
        // Context of the app under test.
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        composeTestRule.onNodeWithText("Test").performClick()
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        composeTestRule.onNodeWithText("Phone : 9999").assertExists()
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        composeTestRule.onNodeWithContentDescription("button").performClick()
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
        composeTestRule.onNodeWithText("DisMiss").performClick()
        composeTestRule.onRoot().printToLog("Test-UserFetcher")
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(mIdlingResource)
    }
}