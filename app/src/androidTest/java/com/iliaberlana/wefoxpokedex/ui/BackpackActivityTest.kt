package com.iliaberlana.wefoxpokedex.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.iliaberlana.wefoxpokedex.R
import com.iliaberlana.wefoxpokedex.ui.backpack.BackpackActivity
import org.junit.Rule
import org.junit.Test

class BackpackActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(BackpackActivity::class.java, true, false)

    @Test
    fun showsCatchButton() {
        activityRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.pokemon_catch))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showsOrderButton() {
        activityRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.action_order))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}