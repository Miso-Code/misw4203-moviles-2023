package com.example.misw4203moviles2023

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher

fun matchToolbarTitle(
    title: CharSequence,
): ViewInteraction? {
    return Espresso.onView(ViewMatchers.isAssignableFrom(Toolbar::class.java))
        .check(ViewAssertions.matches(withToolbarTitle(CoreMatchers.`is`(title))))
}
private fun withToolbarTitle(
    textMatcher: Matcher<CharSequence>,
): Matcher<Any?> {
    return object : BoundedMatcher<Any?, Toolbar>(Toolbar::class.java) {
        override fun matchesSafely(toolbar: Toolbar): Boolean {
            return textMatcher.matches(toolbar.title)
        }

        override fun describeTo(description: Description) {
            description.appendText("with toolbar title: ")
            textMatcher.describeTo(description)
        }
    }
}

fun withImageDrawable(resourceId: Int): Matcher<View?> {
    return object : BoundedMatcher<View?, ImageView>(ImageView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has image drawable resource $resourceId")
        }

        override fun matchesSafely(imageView: ImageView): Boolean {
            return sameBitmap(imageView.context, imageView.drawable, resourceId)
        }
    }
}

private fun sameBitmap(context: Context, drawable: Drawable, resourceId: Int): Boolean {
    var currentDrawable: Drawable? = drawable
    var otherDrawable = context.getDrawable(resourceId)
    if (currentDrawable == null || otherDrawable == null) {
        return false
    }
    if (currentDrawable is StateListDrawable && otherDrawable is StateListDrawable) {
        currentDrawable = currentDrawable.getCurrent()
        otherDrawable = otherDrawable.getCurrent()
    }
    val bitmap = currentDrawable.toBitmap()
    val otherBitmap = otherDrawable.toBitmap()
    return bitmap.sameAs(otherBitmap)
}
