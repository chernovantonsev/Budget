package ru.antonc.budget.util.numpad

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import ru.antonc.budget.R


class NumPad(context: Context, attrs: AttributeSet?) :
    TableLayout(context, attrs) {
    private var num1: TextView? = null
    private var num2: TextView? = null
    private var num3: TextView? = null
    private var num4: TextView? = null
    private var num5: TextView? = null
    private var num6: TextView? = null
    private var num7: TextView? = null
    private var num8: TextView? = null
    private var num9: TextView? = null
    private var num0: TextView? = null
    private var backButton: ViewGroup? = null
    private var dotButton: TextView? = null
    private var listener: NumPadClick? = null
    private var container: ConstraintLayout? = null

    fun setOnNumPadClickListener(@NonNull listener: NumPadClick?) {
        this.listener = listener
        initialiseButtonListener()
    }

    private fun inflateNumPad(context: Context) {
        val view = View.inflate(context, R.layout.numpad_layout, this)
        initialiseWidgets(view)
    }

    private fun initialiseWidgets(view: View) {
        //Numpad widgets
        num1 = view.findViewById(R.id.btnNumpad1)
        num2 = view.findViewById(R.id.btnNumpad2)
        num3 = view.findViewById(R.id.btnNumpad3)
        num4 = view.findViewById(R.id.btnNumpad4)
        num5 = view.findViewById(R.id.btnNumpad5)
        num6 = view.findViewById(R.id.btnNumpad6)
        num7 = view.findViewById(R.id.btnNumpad7)
        num8 = view.findViewById(R.id.btnNumpad8)
        num9 = view.findViewById(R.id.btnNumpad9)
        num0 = view.findViewById(R.id.btnNumpad0)
        backButton = view.findViewById(R.id.btnNumpadBack)
        dotButton = view.findViewById(R.id.btnNumpadDot)
        // Background
        container = view.findViewById(R.id.numpad_container)
    }

    private fun initialiseButtonListener() {
        num1?.setOnClickListener(listener)
        num2?.setOnClickListener(listener)
        num3?.setOnClickListener(listener)
        num4?.setOnClickListener(listener)
        num5?.setOnClickListener(listener)
        num6?.setOnClickListener(listener)
        num7?.setOnClickListener(listener)
        num8?.setOnClickListener(listener)
        num9?.setOnClickListener(listener)
        num0?.setOnClickListener(listener)
        backButton?.setOnClickListener(listener)
        dotButton?.setOnClickListener(listener)
    }

    /**
     * Change color of button's text
     *
     * @param context
     * @param colorId
     */
    fun setButtonTextColor(context: Context, colorId: Int) {
        num1?.setTextColor(ContextCompat.getColor(context, colorId))
        num2?.setTextColor(ContextCompat.getColor(context, colorId))
        num3?.setTextColor(ContextCompat.getColor(context, colorId))
        num4?.setTextColor(ContextCompat.getColor(context, colorId))
        num5?.setTextColor(ContextCompat.getColor(context, colorId))
        num6?.setTextColor(ContextCompat.getColor(context, colorId))
        num7?.setTextColor(ContextCompat.getColor(context, colorId))
        num8?.setTextColor(ContextCompat.getColor(context, colorId))
        num9?.setTextColor(ContextCompat.getColor(context, colorId))
        num0?.setTextColor(ContextCompat.getColor(context, colorId))
    }

    init {
        inflateNumPad(context)
    }
}