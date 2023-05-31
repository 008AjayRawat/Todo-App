package com.android.todoapp.presentation.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.android.todoapp.R

class WarningDialogFragment(private val callback: ()->Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_task_delet_warning, null)

        view.findViewById<View>(R.id.btn_ok).setOnClickListener {
            // Perform action when "Ok" is clicked
            callback.invoke()
            dismiss()
        }

        view.findViewById<View>(R.id.btn_cancel).setOnClickListener {
            // Perform action when "Cancel" is clicked
            dismiss()
        }

        builder.setView(view)
        val dialog = builder.create()
        // Set the custom dialog's background
        dialog.window?.setBackgroundDrawableResource(R.drawable.shape_dialog_warning)

        return dialog
    }
}
