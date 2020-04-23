package co.cdmunoz.vp2onboarding.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.*
import android.view.View
import androidx.core.content.ContextCompat
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import org.hamcrest.Description
import org.hamcrest.Matcher

class CustomMatchers {
    companion object {
        fun withBackground(expectedResourceId: Int): Matcher<View?>? {
            return object : BoundedMatcher<View?, View>(View::class.java) {
                override fun matchesSafely(view: View): Boolean {
                    return sameBitmap(view.context, view.background, expectedResourceId)
                }

                override fun describeTo(description: Description) {
                    description.appendText("has background resource $expectedResourceId")
                }
            }
        }

        private fun sameBitmap(context: Context, drawable: Drawable, resourceId: Int): Boolean {
            var drawable: Drawable? = drawable
            var expectedDrawable = ContextCompat.getDrawable(context, resourceId)
            if (drawable == null || expectedDrawable == null) {
                return false
            }

            if (drawable is StateListDrawable && expectedDrawable is StateListDrawable) {
                drawable = drawable.getCurrent()
                expectedDrawable = expectedDrawable.getCurrent()
            }
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                val otherBitmap = (expectedDrawable as BitmapDrawable).bitmap
                return bitmap.sameAs(otherBitmap)
            }

            if ((drawable is VectorDrawable && expectedDrawable is VectorDrawable) || (drawable is VectorDrawableCompat && expectedDrawable is VectorDrawableCompat) || (drawable is GradientDrawable && expectedDrawable is GradientDrawable)) {
                return drawableToBitmap(drawable.current)!!.sameAs(drawableToBitmap(expectedDrawable.current))
            }
            return false
        }

        private fun drawableToBitmap(drawable: Drawable): Bitmap? {
            var bitmap: Bitmap? = null
            if (drawable is BitmapDrawable) {
                if (drawable.bitmap != null) {
                    return drawable.bitmap
                }
            }
            bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                Bitmap.createBitmap(
                    1,
                    1,
                    Bitmap.Config.ARGB_8888
                ) // Single color bitmap will be created of 1x1 pixel
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }

}