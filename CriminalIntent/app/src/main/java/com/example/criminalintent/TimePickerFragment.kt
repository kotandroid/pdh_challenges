package com.example.criminalintent

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_TIME = "time"

class TimePickerFragment:DialogFragment() {

    interface Callbacks {
        fun onTimeSelected(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val date = arguments?.getSerializable(ARG_TIME) as Date

        val calendar = Calendar.getInstance()

        calendar.time = date
        val initialHour = calendar.get(Calendar.HOUR_OF_DAY)
        val initialMin = calendar.get(Calendar.MINUTE)

        val timeListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            val temp_c = Calendar.getInstance()
            temp_c.time = calendar.time

            temp_c.set(Calendar.HOUR_OF_DAY, hour)
            temp_c.set(Calendar.MINUTE, minute)
            val resultTime:Date = temp_c.time

            targetFragment?.let{ fragment ->
                (fragment as Callbacks).onTimeSelected(resultTime)
            }
        }

        return TimePickerDialog(
            requireContext(),
            timeListener,
            initialHour,
            initialMin,
            true
        )
    }

    companion object {
        fun newInstance(date: Date):TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, date)
            }

            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}