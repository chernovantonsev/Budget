package ru.antonc.budget.ui.date

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.antonc.budget.R
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var listener: DatePickerDialog.OnDateSetListener? = null

    private lateinit var dateDialog: DatePickerDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val date = arguments?.getLong(DATE)

        val c = Calendar.getInstance()

        if (date != null) {
            c.timeInMillis = date
        }

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        dateDialog = DatePickerDialog(
            requireContext(),
            R.style.ThemeOverlay_MaterialComponents_Dialog,
            this,
            year,
            month,
            day
        )

        return dateDialog
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val title = arguments?.getString(TITLE) ?: ""

        if (title.isNotEmpty()) {
            val titleView = inflater.inflate(R.layout.date_picker_dialog_title, null)
            titleView.findViewById<TextView>(R.id.title).text = title

            dateDialog.setCustomTitle(titleView)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener?.onDateSet(view, year, month, day)
    }


    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }

    fun setOnDateSetListener(dateSetListener: DatePickerDialog.OnDateSetListener) {
        listener = dateSetListener
    }

    companion object {
        const val DATE = "date_long"
        const val TITLE = "title"

        fun createDatePickerDialog(
            title: String = "",
            initDate: Long,
            onDateSelected: (res: Long) -> Unit
        ): DatePickerFragment {
            return DatePickerFragment().also { datePickerFragment ->
                Bundle().apply {
                    putLong(DATE, initDate)
                    putString(TITLE, title)
                }.let {
                    datePickerFragment.arguments = it
                }

                datePickerFragment.setOnDateSetListener(DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                        .let {
                            onDateSelected.invoke(it.timeInMillis)
                        }
                })
            }

        }
    }
}