package ru.antonc.budget.ui.main

import ru.antonc.budget.ui.base.OnBackPressedListener

fun MainActivity.setOnBackPressedListener(onBackPressedListener: OnBackPressedListener?) {
    if (!this.onBackPressedListener.contains(onBackPressedListener))
        this.onBackPressedListener.add(onBackPressedListener)
}

fun MainActivity.removeOnBakPressedListener(onBackPressedListener: OnBackPressedListener?) {
    this.onBackPressedListener.remove(onBackPressedListener)
}