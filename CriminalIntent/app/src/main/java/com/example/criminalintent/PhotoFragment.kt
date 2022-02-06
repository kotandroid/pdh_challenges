package com.example.criminalintent

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import java.io.File
import java.util.*

private const val ARG_PHOTO = "photo"

class PhotoFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val photo = arguments?.getSerializable(ARG_PHOTO) as File
        val imageDialog = AlertDialog.Builder(requireActivity())
        imageDialog.setView(ImageView(requireActivity()).apply {
            setImageBitmap(BitmapFactory.decodeFile(photo.path))
        })
        imageDialog.setNegativeButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->  })


        return imageDialog.show()
    }

    companion object {
        fun newInstance(photoFile: File):PhotoFragment {
            val args = Bundle().apply {
                putSerializable(ARG_PHOTO, photoFile)
            }

            return PhotoFragment().apply {
                arguments = args
            }
        }
    }
}