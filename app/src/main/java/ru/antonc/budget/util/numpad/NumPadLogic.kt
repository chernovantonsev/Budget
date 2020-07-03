package ru.antonc.budget.util.numpad

import android.view.View
import ru.antonc.budget.R


internal object NumPadLogic {

    const val BACK_BUTTON = 10
    const val DOT_BUTTON = 11


    /**
     * Return int on button click
     *
     * @param view
     * @return
     */
    fun returnInteger(view: View): Int {
        return when (view.id) {
            R.id.btnNumpad0 -> {
                0
            }
            R.id.btnNumpad1 -> {
                1
            }
            R.id.btnNumpad2 -> {
                2
            }
            R.id.btnNumpad3 -> {
                3
            }
            R.id.btnNumpad4 -> {
                4
            }
            R.id.btnNumpad5 -> {
                5
            }
            R.id.btnNumpad6 -> {
                6
            }
            R.id.btnNumpad7 -> {
                7
            }
            R.id.btnNumpad8 -> {
                8
            }
            R.id.btnNumpad9 -> {
                9
            }
            R.id.btnNumpadBack -> {
                BACK_BUTTON
            }
            R.id.btnNumpadDot -> {
                DOT_BUTTON
            }
            else -> {
                -1
            }
        }
    }

    fun returnList(input: Int, numbers: ArrayList<Int>?): ArrayList<Int>? {
        numbers?.let {
            if (input != -1 && input != -2) {
                numbers.add(input)
            } else if (input == -1 && numbers.size != 0) {
                numbers.remove(numbers.size - 1)
            } else {
                // Do nothing
            }
        }

        return numbers
    }
}