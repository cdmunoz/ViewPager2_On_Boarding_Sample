package co.cdmunoz.vp2onboarding

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.cdmunoz.vp2onboarding.utils.CustomMatchers.Companion.withBackground
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnBoardingActivityUITest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<OnBoardingActivity>()

    @Test
    fun initial_state_on_boarding_screen_UI_test() {
        val titleToCheck = "On Boarding Title 1"
        onView(withText(titleToCheck)).check(matches(isDisplayed()))
        onView(withId(R.id.on_board_bottom_msg)).check(matches(isDisplayed()))
        with(onView(withId(R.id.on_board_sign_up))) {
            check(matches(isDisplayed()))
            check(matches(isEnabled()))
        }
        with(onView(withId(R.id.on_board_login))) {
            check(matches(isDisplayed()))
            check(matches(isEnabled()))
        }
        onView(withId(R.id.on_boarding_marker_container)).check(matches(isDisplayed()))
        onView(withId(R.id.on_boarding_initial_circle)).check(matches(withBackground(R.drawable.bg_green_circle)))
        onView(withId(R.id.on_boarding_view_pager)).check(matches(isDisplayed()))
    }

    @Test
    fun swipe_on_boarding_to_second_page_UI_test() {
        val titleToCheck = "On Boarding Title 2"
        onView(withId(R.id.on_boarding_view_pager)).perform(ViewActions.swipeLeft()) //page 2
        onView(withText(titleToCheck)).check(matches(isDisplayed()))
        onView(withId(R.id.on_boarding_initial_circle)).check(matches(withBackground(R.drawable.bg_gray_circle)))
        onView(withId(R.id.on_boarding_middle_circle)).check(matches(withBackground(R.drawable.bg_green_circle)))
        onView(withId(R.id.on_boarding_last_circle)).check(matches(withBackground(R.drawable.bg_gray_circle)))
    }

    @Test
    fun swipe_on_boarding_to_third_page_UI_test() {
        val titleToCheck = "On Boarding Title 3"
        with(onView(withId(R.id.on_boarding_view_pager))) {
            perform(ViewActions.swipeLeft()) //page 2
            perform(ViewActions.swipeLeft()) //page 3
        }
        onView(withText(titleToCheck)).check(matches(isDisplayed()))
        onView(withId(R.id.on_boarding_initial_circle)).check(matches(withBackground(R.drawable.bg_gray_circle)))
        onView(withId(R.id.on_boarding_middle_circle)).check(matches(withBackground(R.drawable.bg_gray_circle)))
        onView(withId(R.id.on_boarding_last_circle)).check(matches(withBackground(R.drawable.bg_green_circle)))
    }

    @Test
    fun swipe_on_boarding_to_third_page_and_return_to_initial_UI_test() {
        with(onView(withId(R.id.on_boarding_view_pager))) {
            perform(ViewActions.swipeLeft()) //page 2
            perform(ViewActions.swipeLeft()) //page 3
            perform(ViewActions.swipeRight()) //page 2
            perform(ViewActions.swipeRight()) //page 1
        }
        initial_state_on_boarding_screen_UI_test()
    }
}
