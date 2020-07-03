package ru.antonc.budget.util.numpad

import android.view.View
import ru.antonc.budget.util.numpad.NumPadLogic.BACK_BUTTON
import ru.antonc.budget.util.numpad.NumPadLogic.DOT_BUTTON
import ru.antonc.budget.util.numpad.NumPadLogic.returnInteger

class NumPadClick(listener: NumPadClickListener) : View.OnClickListener {
    private val mListener: NumPadClickListener = listener
    private var result: StringBuilder = StringBuilder()

    override fun onClick(view: View?) {
        if (view != null) {

            when (val valueOfButton = returnInteger(view)) {
                in 0..9 -> {
                    if (result.length == 1 && result.first() == '0')
                        result.clear()
                    result.append(valueOfButton)
                }
                BACK_BUTTON -> {
                    when {
                        result.length == 1 -> {
                            result.clear()
                            result.append(0)
                        }
                        result.length > 1 -> {
                            result.deleteCharAt(result.length - 1)
                        }
                    }
                }
                DOT_BUTTON -> {
                    if (!result.contains('.')) {
                        result.append('.')
                    }
                }
                else -> {
                    //Nothing to do
                }
            }

            mListener.onNumPadClicked(result.toString())
        } else {
            throw NullPointerException("No listener attached to numpad")
        }
    }

}

interface NumPadClickListener {
    fun onNumPadClicked(numberString: String)
}