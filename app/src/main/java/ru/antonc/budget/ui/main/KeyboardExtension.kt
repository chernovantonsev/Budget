package ru.antonc.budget.ui.main

import android.app.Activity
import android.graphics.Rect
import android.view.ViewGroup

fun Activity.getRootView(): ViewGroup {
    return findViewById(android.R.id.content)
}

fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()

    val activityRoot = getRootView().getChildAt(0)

    activityRoot.getWindowVisibleDisplayFrame(r)

    val location = IntArray(2)
    getRootView().getLocationOnScreen(location)

    val screenHeight = activityRoot.rootView.height
    val heightDiff = screenHeight - r.height() - location[1]

    return heightDiff > screenHeight * 0.15
}
